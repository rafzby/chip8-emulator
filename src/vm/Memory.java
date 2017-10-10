package vm;

import vm.exceptions.MemoryReadException;
import vm.exceptions.MemoryWriteException;


public class Memory {
    private char[] memory;


    public Memory(int size) {
        memory = new char[size];
    }

    public char readByte(int address) throws MemoryReadException {
        if(address < 0 || address >= memory.length) {
            throw new MemoryReadException("Attempt to read data from wrong memory address.");
        }

        return memory[address];
    }

    public char readOpcode(int address) throws MemoryReadException{
        return (char)((readByte(address) << 8) | readByte(address + 1));
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
