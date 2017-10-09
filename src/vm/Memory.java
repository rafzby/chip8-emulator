package vm;

import vm.exceptions.BadMemoryAccessException;

public class Memory {
    private byte[] memory;

    public Memory() {
        memory = new byte[4096];
    }

    public byte fetchByte(int address) throws BadMemoryAccessException {
        if(address < 0 || address >= memory.length) {
            throw new BadMemoryAccessException("Address (" + address + ") doesn't exists");
        }

        return memory[address];
    }

    public void writeByte(int address, byte value) throws BadMemoryAccessException {
        if(address < 0 || address >= memory.length) {
            throw new BadMemoryAccessException("Address (" + address + ") doesn't exists");
        }

        memory[address] = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < memory.length; i++) {
            builder.append(Integer.toHexString(i)).append(": ").append(Integer.toHexString(memory[i])).append("\n");
        }

        return builder.toString();
    }
}
