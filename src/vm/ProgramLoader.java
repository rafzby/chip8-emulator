package vm;

import vm.exceptions.BadMemoryAccessException;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ProgramLoader {
    private Memory memory;

    public ProgramLoader(Memory memory) {
        this.memory = memory;
    }

    public boolean loadProgram(String fileName) {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        DataInputStream inputStream = new DataInputStream(fileInputStream);

        int memoryOffset = 0;

        try {
            while(inputStream.available() > 0) {
                int address = 0x200 + memoryOffset;
                byte value = (byte) (inputStream.readByte() & 0xFF);

                memory.writeByte(address, value);
                memoryOffset++;
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
