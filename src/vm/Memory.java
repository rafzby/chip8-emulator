package vm;

import vm.exceptions.MemoryReadException;
import vm.exceptions.MemoryWriteException;


public class Memory {
    private static final int MEMORY_SIZE = 0x1000;

    private char[] memory;


    public Memory() {
        memory = new char[MEMORY_SIZE];
    }

    public char readByte(int address) throws MemoryReadException {
        if(address < 0 || address >= memory.length) {
            throw new MemoryReadException("Attempt to read data from wrong memory address.");
        }

        return memory[address];
    }

    public void writeByte(int address, char value) throws MemoryWriteException {
        if(address < 0 || address >= memory.length) {
            throw new MemoryWriteException("Attempt to write date to the wrong memory address.");
        }

        memory[address] = (char) (value & 0xFF);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < memory.length; i++) {
            try {
                builder.append(Integer.toHexString(i)).append(": ").append(Integer.toHexString(readByte(i))).append("\n");
            } catch (MemoryReadException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }
}
