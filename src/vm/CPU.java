package vm;

import vm.exceptions.*;

import java.util.Random;

public class CPU {
    private static final char USER_PROGRAM_START_ADDRESS = 0x200;
    private static final int STACK_SIZE = 16;
    private static final int REGISTERS_NUMBER = 16;

    private char programCounter;
    private Memory memory;
    private Stack stack;
    private CPUCallbacks callbacks;
    private int delayTimer;
    private int soundTimer;
    private char[] V;
    private char I;


    public CPU(Memory memory, CPUCallbacks callbacks) {
        this.memory = memory;
        this.callbacks = callbacks;

        stack = new Stack(STACK_SIZE);
        programCounter = USER_PROGRAM_START_ADDRESS;
        delayTimer = 0;
        soundTimer = 0;

        V = new char[REGISTERS_NUMBER];
        I = 0x0;
    }

    public void execute() throws CpuException {
        try {
            char opcode = memory.readOpcode(programCounter);

            switch(opcode & 0xF000) { // First 4 bits are number of the operation

                case 0x0000: { // Multi-Case
                    switch(opcode & 0x00FF) {

                        // 00E00: Clear the display (CLS)
                        case 0x00E0: {
                            callbacks.onDisplayClear();
                            programCounter += 2;
                            break;
                        }

                        // 00EE: Return from a subroutine (RET)
                        case 0x00EE: {
                            try {
                                programCounter = stack.pop();
                            } catch (StackException e) {
                                e.printStackTrace();
                                System.exit(0);
                            }
                            break;
                        }

                        default:
                            throw new CpuException("Unsupported opcode.");
                    }

                    break;
                }

                // 1nnn: Jump to location nnn (JP addr)
                case 0x1000: {
                    programCounter = (char) (opcode & 0x0FFF);
                    break;
                }

                // 2nnn: Call subroutine at nnn (CALL addr)
                case 0x2000: {
                    try {
                        stack.push(programCounter);
                    } catch (StackException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }

                    programCounter = (char) (opcode & 0x0FFF);
                    break;
                }

                // 3xkk: Skip next instruction if Vx = kk (SE Vx, byte)
                case 0x3000: {
                    int x = (opcode & 0x0F00) >> 8;
                    int kk = (opcode & 0x00FF);


                    if(V[x] == kk) {
                        programCounter += 4;
                    } else {
                        programCounter += 2;
                    }
                    break;
                }

                // 4xkk: Skip next instruction if Vx != kk (SNE Vx, byte)
                case 0x4000: {
                    int x = (opcode & 0x0F00) >> 8;
                    int kk = (opcode & 0x00FF);

                    if(V[x] != kk) {
                        programCounter += 4;
                    } else {
                        programCounter += 2;
                    }
                    break;
                }

                // 5xy0: Skip next instruction if Vx = Vy (SE Vx, Vy)
                case 0x5000: {
                    int x = (opcode & 0x0F00) >> 8;
                    int y = (opcode & 0x00F0) >> 4;

                    if(V[x] == V[y]) {
                        programCounter += 4;
                    } else {
                        programCounter += 2;
                    }

                    break;
                }

                // 6xkk: Set Vx = kk (LD Vx, byte)
                case 0x6000: {
                    int x = (opcode & 0x0F00) >> 8;
                    V[x] = (char) (opcode & 0x00FF);
                    programCounter += 2;
                    break;
                }

                // 7xkk: Set Vx = Vx + kk (ADD Vx, byte)
                case 0x7000: {
                    int x = (opcode & 0x0F00) >> 8;
                    int kk = (opcode & 0x00FF);
                    V[x] = (char)((V[x] + kk) & 0xFF);
                    programCounter += 2;
                    break;
                }

                // Multi-Case
                case 0x8000: {
                    switch(opcode & 0x000F) {

                        // 8xy0: Set Vx = Vy (LD Vx, Vy)
                        case 0x0000: {
                            int x = (opcode & 0x0F00) >> 8;
                            int y = (opcode & 0x00F0) >> 4;

                            V[x] = V[y];

                            programCounter += 2;
                            break;
                        }

                        // 8xy1: Set Vx = Vx OR Vy (OR Vx, Vy)
                        case 0x0001: {
                            int x = (opcode & 0x0F00) >> 8;
                            int y = (opcode & 0x00F0) >> 4;

                            V[x] |= V[y];
                            programCounter += 2;

                            break;
                        }

                        // 8xy2: Set Vx = Vx AND Vy (AND Vx, Vy)
                        case 0x0002: {
                            int x = (opcode & 0x0F00) >> 8;
                            int y = (opcode & 0x00F0) >> 4;

                            V[x] &= V[y];
                            programCounter += 2;
                            break;
                        }

                        // 8xy3: Set Vx = Vx XOR Vy (XOR Vx, Vy)
                        case 0x0003: {
                            int x = (opcode & 0x0F00) >> 8;
                            int y = (opcode & 0x00F0) >> 4;

                            V[x] ^= V[y];

                            programCounter += 2;
                            break;
                        }

                        // 8xy4: Set Vx = Vx + Vy, set VF = carry
                        case 0x0004: {
                            int x = (opcode & 0x0F00) >> 8;
                            int y = (opcode & 0x00F0) >> 4;

                            int result = V[x] + V[y];

                            if(result > 0xFF) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }
                            result &= 0xFF;

                            V[x] = (char)result;

                            programCounter += 2;
                            break;
                        }

                        // 8xy5: Set Vx = Vx - Vy, set VF = NOT borrow (SUB Vx, Vy)
                        case 0x0005: {
                            int x = (opcode & 0x0F00) >> 8;
                            int y = (opcode & 0x00F0) >> 4;

                            if(V[x] > V[y]) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            V[x] = (char)((V[x] - V[y]) & 0xFF);

                            programCounter += 2;

                            break;
                        }

                        // 8xy6: Set Vx = Vx SHR 1 (SHR Vx {, Vy})
                        case 0x0006: {
                            int x = (opcode & 0x0F00) >> 8;

                            if((V[x] & 0x000F) == 1) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            V[x] /= 2;


                            programCounter += 2;
                            break;
                        }

                        // 8xy7: Set Vx = Vy - Vx, set VF = NOT borrow
                        case 0x0007: {
                            int x = (opcode & 0x0F00) >> 8;
                            int y = (opcode & 0x00F0) >> 4;

                            if(V[y] > V[x]) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            V[x] = (char)(V[y] - V[x]);

                            programCounter += 2;

                            break;
                        }

                        // 8xyE: Set Vx = Vx SHL 1 (SHL Vx {, Vy})
                        case 0x000E: {
                            int x = (opcode & 0x0F00) >> 8;

                            if((V[x] & 0x000F) == 1) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            V[x] *= 2;


                            programCounter += 2;
                            break;
                        }

                        default:
                            throw new CpuException("Unsupported opcode.");
                    }

                    break;
                }

                // 9xy0: Skip nest instruction if Vx != Vy (SNE Vx, Vy)
                case 0x9000: {
                    int x = (opcode & 0x0F00) >> 8;
                    int y = (opcode & 0x00F0) >> 4;

                    if(V[x] == V[y]) {
                        programCounter += 2;
                    } else {
                        programCounter += 4;
                    }

                    break;
                }

                // Annn: Set I = nnn (LD I, addr)
                case 0xA000: {
                    I = (char)(opcode & 0x0FFF);
                    programCounter += 2;

                    break;
                }

                // Bnnn: Jump to location nnn + V0 (JP V0, addr)
                case 0xB000: {
                    int nnn = opcode & 0x0FFF;
                    programCounter = (char)(nnn + V[0x0]);


                    break;
                }

                // Cxkk: Set Vx = random byte AND kk (RND Vx, byte)
                case 0xC000: {
                    int x = (opcode & 0x0F00) >> 8;
                    int kk = (opcode & 0x00FF);
                    int randomNumber = new Random().nextInt(256) & kk;

                    V[x] = (char)randomNumber;
                    programCounter += 2;
                    break;
                }

                // Dxyn: Display n-byte sprite starting at memory location I at (Vx, Vy), set VF = collision (DRW Vx, Vy, nibble)
                case 0xD000: {
                    int x = V[(opcode & 0x0F00) >> 8];
                    int y = V[(opcode & 0x00F0) >> 4];
                    int n = opcode & 0x000F;

                    V[0xF] = 0; // No collision

                    for(int _y = 0; _y < n; _y++) {
                        int line = memory.readByte(I + _y);

                        for(int _x = 0; _x < 8; _x++) {
                            int pixel = line & (0x80 >> _x); // Read bits from left to right

                            if(pixel != 0) {
                                int totalX = x + _x;
                                int totalY = y + _y;

                                totalX = totalX % 64;
                                totalY = totalY % 32;

                                int index = totalY * 64 + totalX;

                                int pixelValue = callbacks.onDisplayGetPixelValue(index);

                                if (pixelValue == 1) {
                                    V[0xF] = 1;
                                }

                                callbacks.onDisplaySetPixelValue(index, pixelValue ^ 1);
                            }
                        }
                    }

                    programCounter += 2;
                    callbacks.onDisplayRepaint();
                    break;
                }

                // Multi-Case
                case 0xE000: {
                    switch(opcode & 0x00FF) {

                        // ExA1: Skip next instruction if key with the value of Vx is not pressed (SKNP Vx)
                        case 0x00A1: {
                            int x = (opcode & 0x0F00) >> 8;

                           /* if(keys[V[x]] == 0) {
                                programCounter += 4;
                            } else {
                                programCounter += 2;
                            }*/

                            programCounter += 2; // TODO remove

                            break;
                        }

                        // Ex9E: Skip next instruction if key with the value of Vx is pressed (SKP Vx)
                        case 0x009E: {
                            int x = (opcode & 0x0F00) >> 8;

                           /* if(keys[V[x]] ==  1) {
                                programCounter += 4;
                            } else {
                                programCounter += 2;
                            }*/

                           programCounter += 2; // TODO remove
                            break;
                        }

                        default:
                            throw new CpuException("Unsupported opcode.");
                    }
                    break;
                }

                // Multi-Case
                case 0xF000: {
                    switch(opcode & 0x00FF) {

                        // Fx07: Set Vx = delay timer value (LD Vx, DT)
                        case 0x0007: {
                            int x = (opcode & 0x0F00) >> 8;
                            V[x] = (char)delayTimer;
                            programCounter += 2;

                            break;
                        }

                        // Fx0A: Wait for keypress, store the value of the key in Vx (LD Vx, K)
                        case 0x000A: {
                            /*for(int i = 0; i < keys.length; i++) {

                                if(keys[i] == 1) {
                                    programCounter += 2;
                                }
                            }*/

                            programCounter += 2; //TODO remove

                            break;
                        }

                        // Fx15: Set delay timer = Vx (LD DT, Vx)
                        case 0x0015: {
                            int x = (opcode & 0x0F00) >> 8;
                            delayTimer = V[x];
                            programCounter += 2;

                            break;
                        }

                        // Fx18: Set sound timer = Vx (LD ST, Vx)
                        case 0x0018: {
                            int x = (opcode & 0x0F00) >> 8;
                            soundTimer = V[x];
                            programCounter += 2;

                            break;
                        }

                        // Fx1E: Set I = I + Vx (ADD I, Vx)
                        case 0x001E: {
                            int x = (opcode & 0x0F00) >> 8;

                            I += V[x];
                            programCounter += 2;

                            break;
                        }

                        //Fx29: Set I = location of sprite for digit Vx (LD F, Vx)
                        case 0x0029: {
                            int x = (opcode & 0x0F00) >> 8;
                            int character = V[x];

                            I = (char)(0x050 + (character * 5));
                            programCounter += 2;

                            break;
                        }

                        //Fx33: Store BCD (Binary-Coded Decimal) representation of Vx in memory locations I, I+1, and I+2 (LD B, Vx)
                        case 0x0033: {
                            int x = (opcode & 0x0F00) >> 8;
                            int value = V[x];
                            int hundreds = (value - (value % 100)) / 100;
                            value -= hundreds * 100;
                            int tens = (value - (value % 10))/ 10;
                            value -= tens * 10;

                            memory.writeByte(I, (char) hundreds);
                            memory.writeByte(I + 1, (char) tens);
                            memory.writeByte(I + 2, (char) value);

                            programCounter += 2;
                            break;
                        }

                        //Fx55: Store registers V0 through Vx in memory starting at location I (LD [I], Vx)
                        case 0x0055: {
                            int x = (opcode & 0x0F00) >> 8;

                            for(int i = 0; i <= x; i ++) {
                                memory.writeByte(I + i, V[i]);
                            }

                            programCounter += 2;
                            break;
                        }

                        //Fx65: Read registers V0 through Vx from memory starting at location I (LD Vx, [I])
                        case 0x0065: {
                            int x = (opcode & 0x0F00) >> 8;

                            for(int i = 0; i <= x; i++) {
                                V[i] = memory.readByte(I + i);
                            }
                            I = (char)(I + x + 1);

                            programCounter += 2;

                            break;
                        }

                        default:
                            throw new CpuException("Unsupported opcode.");
                    }

                    break;
                }

                default:
                    throw new CpuException("Unsupported opcode.");
            }
        } catch (MemoryReadException e) {
            throw new CpuException("Unable to read opcode from memory.");
        }  catch (MemoryWriteException e) {
            e.printStackTrace();
        }
    }
}
