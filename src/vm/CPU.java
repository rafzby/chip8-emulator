package vm;

import vm.exceptions.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CPU {
    private static final char PROGRAM_COUNTER_START = 0x200;
    private static final int STACK_SIZE = 16;
    private static final int REGISTERS_NUMBER = 16;

    private char programCounter;
    private Memory memory;
    private Stack stack;
    private IODevices ioDevices;
    private int delayTimer;
    private int soundTimer;
    private char[] V;
    private char I;


    public CPU(Memory memory, IODevices ioDevices) {
        this.memory = memory;
        this.ioDevices = ioDevices;

        stack = new Stack(STACK_SIZE);

        programCounter = PROGRAM_COUNTER_START;
        delayTimer = 0;
        soundTimer = 0;

        V = new char[REGISTERS_NUMBER];
        I = 0x0;

        initTimers();
    }

    public void execute() throws CpuException, InterruptedException, StackException {
        try {
            char opcode = memory.readOpcode(programCounter);

           // System.out.println(memory);
            //System.exit(0);
           // System.out.println(Integer.toHexString(opcode));

            switch(opcode & 0xF000) {
                case 0x0000: {
                    switch(opcode & 0x00FF) {

                        // 00E00: Clear the display (CLS)
                        case 0x00E0: {
                            ioDevices.clearDisplay();
                            programCounter += 2;
                            break;
                        }

                        // 00EE: Return from a subroutine (RET)
                        case 0x00EE: {
                            programCounter = (char) (stack.pop() + 2);
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
                    stack.push(programCounter);
                    programCounter = (char) (opcode & 0x0FFF);
                    break;
                }

                // 3xkk: Skip next instruction if Vx = kk (SE Vx, byte)
                case 0x3000: {
                    int sourceRegister = (opcode & 0x0F00) >> 8;
                    int value = (opcode & 0x00FF);

                    if (V[sourceRegister] == value) {
                        programCounter += 4;
                    } else {
                        programCounter += 2;
                    }
                    break;
                }

                // 4xkk: Skip next instruction if Vx != kk (SNE Vx, byte)
                case 0x4000: {
                    int sourceRegister = (opcode & 0x0F00) >> 8;
                    int value = (opcode & 0x00FF);

                    programCounter += V[sourceRegister] != value ? 4 : 2;
                    break;
                }

                // 5xy0: Skip next instruction if Vx = Vy (SE Vx, Vy)
                case 0x5000: {
                    int sourceRegister = (opcode & 0x0F00) >> 8;
                    int targetRegister = (opcode & 0x00F0) >> 4;

                    programCounter += V[sourceRegister] == V[targetRegister] ? 4 : 2;
                    break;
                }

                // 6xkk: Set Vx = kk (LD Vx, byte)
                case 0x6000: {
                    int targetRegister = (opcode & 0x0F00) >> 8;

                    V[targetRegister] = (char) (opcode & 0x00FF);

                    programCounter += 2;
                    break;
                }

                // 7xkk: Set Vx = Vx + kk (ADD Vx, byte)
                case 0x7000: {
                    int targetRegister = (opcode & 0x0F00) >> 8;
                    int value = (opcode & 0x00FF);

                    V[targetRegister] = (char) ((V[targetRegister] + value) & 0xFF);
                    programCounter += 2;
                    break;
                }

                // Multi-Case
                case 0x8000: {
                    switch(opcode & 0x000F) {

                        // 8xy0: Set Vx = Vy (LD Vx, Vy)
                        case 0x0000: {
                            int targetRegister  = (opcode & 0x0F00) >> 8;
                            int sourceRegister = (opcode & 0x00F0) >> 4;

                            V[targetRegister] = V[sourceRegister];

                            programCounter += 2;
                            break;
                        }

                        // 8xy1: Set Vx = Vx OR Vy (OR Vx, Vy)
                        case 0x0001: {
                            int targetRegister = (opcode & 0x0F00) >> 8;
                            int sourceRegister = (opcode & 0x00F0) >> 4;

                            V[targetRegister] |= V[sourceRegister];
                            programCounter += 2;
                            break;
                        }

                        // 8xy2: Set Vx = Vx AND Vy (AND Vx, Vy)
                        case 0x0002: {
                            int targetRegister = (opcode & 0x0F00) >> 8;
                            int sourceRegister = (opcode & 0x00F0) >> 4;

                            V[targetRegister] &= V[sourceRegister];
                            programCounter += 2;
                            break;
                        }

                        // 8xy3: Set Vx = Vx XOR Vy (XOR Vx, Vy)
                        case 0x0003: {
                            int targetRegister = (opcode & 0x0F00) >> 8;
                            int sourceRegister = (opcode & 0x00F0) >> 4;

                            V[targetRegister] ^= V[sourceRegister];
                            programCounter += 2;
                            break;
                        }

                        // 8xy4: Set Vx = Vx + Vy, set VF = carry
                        case 0x0004: {
                            int targetRegister = (opcode & 0x0F00) >> 8;
                            int sourceRegister = (opcode & 0x00F0) >> 4;

                            int result = V[targetRegister] + V[sourceRegister];

                            if(result > 0xFF) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            result &= 0xFF;

                            V[targetRegister] = (char) result;

                            programCounter += 2;
                            break;
                        }

                        // 8xy5: Set Vx = Vx - Vy, set VF = NOT borrow (SUB Vx, Vy)
                        case 0x0005: {
                            int targetRegister = (opcode & 0x0F00) >> 8;
                            int sourceRegister = (opcode & 0x00F0) >> 4;

                            if(V[targetRegister] > V[sourceRegister]) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            int result = (V[targetRegister] - V[sourceRegister]) & 0xFF;
                            V[targetRegister] = (char) result;

                            programCounter += 2;
                            break;
                        }

                        // 8xy6: Set Vx = Vx SHR 1 (SHR Vx {, Vy})
                        case 0x0006: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;

                            if((V[sourceRegister] & 0x000F) == 1) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            V[sourceRegister] /= 2;
                            programCounter += 2;
                            break;
                        }

                        // 8xy7: Set Vx = Vy - Vx, set VF = NOT borrow
                        case 0x0007: {
                            int targetRegister = (opcode & 0x0F00) >> 8;
                            int sourceRegister = (opcode & 0x00F0) >> 4;

                            if(V[sourceRegister] > V[targetRegister]) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            V[targetRegister] = (char) (V[sourceRegister] - V[targetRegister]);

                            programCounter += 2;
                            break;
                        }

                        // 8xyE: Set Vx = Vx SHL 1 (SHL Vx {, Vy})
                        case 0x000E: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;

                            if((V[sourceRegister] & 0x000F) == 1) {
                                V[0xF] = 1;
                            } else {
                                V[0xF] = 0;
                            }

                            V[sourceRegister] *= 2;
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
                    int sourceRegister = (opcode & 0x0F00) >> 8;
                    int targetRegister = (opcode & 0x00F0) >> 4;

                    programCounter += V[sourceRegister] != V[targetRegister] ? 4 : 2;
                    break;
                }

                // Annn: Set I = nnn (LD I, addr)
                case 0xA000: {
                    I = (char) (opcode & 0x0FFF);
                    programCounter += 2;
                    break;
                }

                // Bnnn: Jump to location nnn + V0 (JP V0, addr)
                case 0xB000: {
                    programCounter = (char) (I + (opcode & 0x0FFF));
                    break;
                }

                // Cxkk: Set Vx = random byte AND kk (RND Vx, byte)
                case 0xC000: {
                    int targetRegister = (opcode & 0x0F00) >> 8;
                    int value = opcode & 0x00FF;
                    int randomNumber = new Random().nextInt(256) & value;

                    V[targetRegister] = (char) randomNumber;
                    programCounter += 2;
                    break;
                }

                // Dxyn: Display n-byte sprite starting at memory location I at (Vx, Vy), set VF = collision (DRW Vx, Vy, nibble)
                case 0xD000: {
                    int x = V[(opcode & 0x0F00) >> 8];
                    int y = V[(opcode & 0x00F0) >> 4];
                    int n = opcode & 0x000F;

                    V[0xF] = 0; // No collision

                    for (int _y = 0; _y < n; _y++) {
                        int line = memory.readByte(I + _y);

                        for(int _x = 0; _x < 8; _x++) {
                            int pixel = line & (0x80 >> _x);

                            if (pixel != 0) {
                                int totalX = x + _x;
                                int totalY = y + _y;

                                totalX = totalX % 64;
                                totalY = totalY % 32;

                                int index = totalY * 64 + totalX;
                                int pixelValue = ioDevices.getPixelValue(index);

                                if (pixelValue == 1) {
                                    V[0xF] = 1;
                                }

                                ioDevices.setPixelValue(index, pixelValue ^ 1);
                            }
                        }
                    }

                    programCounter += 2;
                    ioDevices.repaintDisplay();
                    break;
                }

                case 0xE000: {
                    switch(opcode & 0x00FF) {

                        // ExA1: Skip next instruction if key with the value of Vx is not pressed (SKNP Vx)
                        case 0x00A1: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;

                            if (ioDevices.getCurrentKeyPressed() != V[sourceRegister]) {
                                programCounter += 4;
                            } else {
                                programCounter += 2;
                            }
                            break;
                        }

                        // Ex9E: Skip next instruction if key with the value of Vx is pressed (SKP Vx)
                        case 0x009E: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;

                            if (ioDevices.getCurrentKeyPressed() == V[sourceRegister]) {
                                programCounter += 4;
                            } else {
                                programCounter += 2;
                            }
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
                            int targetRegister = (opcode & 0x0F00) >> 8;
                            V[targetRegister] = (char) delayTimer;
                            programCounter += 2;
                            break;
                        }

                        // Fx0A: Wait for key press, store the value of the key in Vx (LD Vx, K)
                        case 0x000A: {
                            int targetRegister = (opcode & 0x0F00);
                            int currentKey = ioDevices.getCurrentKeyPressed();

                            while (currentKey == 0) {
                                Thread.sleep(300);
                                currentKey = ioDevices.getCurrentKeyPressed();
                            }

                            V[targetRegister] = (char) currentKey;
                            programCounter += 2;
                            break;
                        }

                        // Fx15: Set delay timer = Vx (LD DT, Vx)
                        case 0x0015: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;
                            delayTimer = V[sourceRegister];
                            programCounter += 2;
                            break;
                        }

                        // Fx18: Set sound timer = Vx (LD ST, Vx)
                        case 0x0018: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;
                            soundTimer = V[sourceRegister];
                            programCounter += 2;
                            break;
                        }

                        // Fx1E: Set I = I + Vx (ADD I, Vx)
                        case 0x001E: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;

                            I += V[sourceRegister];
                            programCounter += 2;
                            break;
                        }

                        //Fx29: Set I = location of sprite for digit Vx (LD F, Vx)
                        case 0x0029: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;
                            I = (char) (0x050 + V[sourceRegister] * 5);
                            programCounter += 2;
                            break;
                        }

                        //Fx33: Store BCD (Binary-Coded Decimal) representation of Vx in memory locations I, I+1, and I+2 (LD B, Vx)
                        case 0x0033: {
                            int sourceRegister = (opcode & 0x0F00) >> 8;
                            int value = V[sourceRegister];

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
                            int numRegisters = (opcode & 0x0F00) >> 8;
                            for(int i = 0; i <= numRegisters; i ++) {
                                memory.writeByte(I + i, V[i]);
                            }

                            programCounter += 2;
                            break;
                        }

                        //Fx65: Read registers V0 through Vx from memory starting at location I (LD Vx, [I])
                        case 0x0065: {
                            int numRegisters = (opcode & 0x0F00) >> 8;
                            for(int i = 0; i <= numRegisters; i++) {
                                V[i] = memory.readByte(I + i);
                            }
                            //I = (char)(I + numRegisters + 1);

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

    private void initTimers() {
        Timer timer = new Timer("Delay Timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                decrementTimers();
            }
        }, 17, 17); // TODO move times to consts
    }

    private void decrementTimers() {
        if (delayTimer != 0) {
            delayTimer--;
        }

        if (soundTimer != 0) {
            soundTimer--;
            // TODO implement midi player
        }
    }
}
