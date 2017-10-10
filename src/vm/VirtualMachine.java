package vm;


import vm.exceptions.FontLoaderException;
import vm.exceptions.ProgramLoaderException;

public class VirtualMachine {
    private static final int MEMORY_SIZE = 0x1000;
    private static final int DISPLAY_SIZE = 0x800;

    private Memory memory;
    private CPU cpu;
    private FontLoader fontLoader;
    private ProgramLoader programLoader;
    private Display display;


    public VirtualMachine() {
        memory = new Memory(MEMORY_SIZE);
        display = new Display(DISPLAY_SIZE);

        cpu = new CPU(memory);

        fontLoader = new FontLoader(memory);
        programLoader = new ProgramLoader(memory);

        loadFont();
    }

    public void loadProgram(String fileName) {
        try {
            programLoader.loadProgram(fileName);
        } catch (ProgramLoaderException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void loadFont() {
        try {
            fontLoader.loadFont();
        } catch (FontLoaderException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run() {
        System.out.println(memory);
    }
}
