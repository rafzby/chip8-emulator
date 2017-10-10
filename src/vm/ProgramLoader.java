package vm;

import vm.exceptions.BadMemoryAccessException;
import vm.exceptions.ProgramLoaderException;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ProgramLoader {
    private static final int MEMORY_OFFSET = 0x200;

    private Memory memory;


    public ProgramLoader(Memory memory) {
        this.memory = memory;
    }

    public void loadProgram(String fileName) throws ProgramLoaderException {
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new ProgramLoaderException("ROM file not found.");
        }

        DataInputStream inputStream = new DataInputStream(fileInputStream);

        int address = MEMORY_OFFSET;

        try {
            while(inputStream.available() > 0) {
                char value = (char) inputStream.readByte();
                memory.write(address, value);
                address++;
            }
        } catch (BadMemoryAccessException e) {
            throw new ProgramLoaderException("Attempt to write data to the wrong memory address.");
        } catch (IOException e) {
            throw new ProgramLoaderException("Problem when reading data from ROM file.");
        }
    }
}
