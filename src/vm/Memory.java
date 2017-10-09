package vm;

import vm.exceptions.BadMemoryAccessException;


public class Memory {
    private static final int MEMORY_SIZE = 0x1000;
    private char[] memory;

    public Memory() {
        memory = new char[MEMORY_SIZE];
    }

    public char read(int address) throws BadMemoryAccessException {
        if(address < 0 || address >= memory.length) {
            throw new BadMemoryAccessException("Address (" + address + ") doesn't exists");
        }

        return memory[address];
    }

    public void write(int address, char value) throws BadMemoryAccessException {
        if(address < 0 || address >= memory.length) {
            throw new BadMemoryAccessException("Address (" + address + ") doesn't exists");
        }

        memory[address] = (char) (value & 0xFF);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < memory.length; i++) {
            try {
                builder.append(Integer.toHexString(i)).append(": ").append(Integer.toHexString(read(i))).append("\n");
            } catch (BadMemoryAccessException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }
}
