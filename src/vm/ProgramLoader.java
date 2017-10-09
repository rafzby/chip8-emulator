package vm;

import vm.exceptions.BadMemoryAccessException;

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

    public boolean loadProgram(String fileName) {
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        DataInputStream inputStream = new DataInputStream(fileInputStream);

        int address = MEMORY_OFFSET;

        try {
            while(inputStream.available() > 0) {
                char value = (char) inputStream.readByte();
                memory.write(address, value);
                address++;
            }
        } catch (IOException | BadMemoryAccessException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
