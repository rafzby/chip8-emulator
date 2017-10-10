package vm;

import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;

public class CPU {
    private static final int USER_PROGRAM_START_ADDRESS = 0x200;

    private int programCounter;
    private Memory memory;


    public CPU(Memory memory) {
        this.programCounter = USER_PROGRAM_START_ADDRESS;
        this.memory = memory;
    }

    public void execute() throws CpuException {
        try {
            char opcode = memory.readOpcode(programCounter);
        } catch (MemoryReadException e) {
            throw new CpuException("Unable to read opcode from memory");
        }
    }
}
