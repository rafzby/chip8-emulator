package vm;

import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;

public class CPU {
    private static final int USER_PROGRAM_START_ADDRESS = 0x200;
    private static final int STACK_SIZE = 16;

    private int programCounter;
    private Memory memory;
    private Stack stack;


    public CPU(Memory memory) {
        this.programCounter = USER_PROGRAM_START_ADDRESS;
        this.memory = memory;

        this.stack = new Stack(STACK_SIZE);
    }

    public void execute() throws CpuException {
        try {
            char opcode = memory.readOpcode(programCounter);
        } catch (MemoryReadException e) {
            throw new CpuException("Unable to read opcode from memory");
        }
    }
}
