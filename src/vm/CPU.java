package vm;

import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;


public class CPU {
    private static final int USER_PROGRAM_START_ADDRESS = 0x200;
    private static final int STACK_SIZE = 16;
    private static final int REGISTERS_NUMBER = 16;

    private char programCounter;
    private Memory memory;
    private Stack stack;
    private RegisterManager registerManager;
    private int delayTimer;
    private int soundTimer;


    public CPU(Memory memory) {
        this.memory = memory;

        stack = new Stack(STACK_SIZE);
        registerManager = new RegisterManager(REGISTERS_NUMBER);
        programCounter = USER_PROGRAM_START_ADDRESS;
        delayTimer = 0;
        soundTimer = 0;
    }

    public void execute() throws CpuException {
        try {
            char opcode = memory.readOpcode(programCounter);
        } catch (MemoryReadException e) {
            throw new CpuException("Unable to read opcode from memory.");
        }
    }
}
