package vm;

import vm.exceptions.DisplayException;
import vm.exceptions.FontLoaderException;
import vm.exceptions.ProgramLoaderException;

public class VirtualMachine {
    private static final int MEMORY_SIZE = 0x1000;
    private static final int DISPLAY_SIZE = 0x800;

    private DisplayPanel displayPanel;
    private MainWindow mainWindow;
    private Memory memory;
    private CPU cpu;
    private FontLoader fontLoader;
    private ProgramLoader programLoader;
    private Display display;


    public VirtualMachine() {
        memory = new Memory(MEMORY_SIZE);
        display = new Display(DISPLAY_SIZE);

        displayPanel = new DisplayPanel(display.getPixelArray());
        mainWindow = new MainWindow(displayPanel);

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
        try {
            display.setPixelValue(0, 1);
            display.setPixelValue(1, 1);
            display.setPixelValue(2, 1);
            display.setPixelValue(3, 1);
            display.setPixelValue(4, 1);
            display.setPixelValue(5, 1);
            display.setPixelValue(6, 1);
            display.setPixelValue(7, 1);
            display.setPixelValue(128, 1);
            display.setPixelValue(129, 1);
            display.setPixelValue(130, 1);
            display.setPixelValue(131, 1);
            display.setPixelValue(132, 1);
            display.setPixelValue(133, 1);
            display.setPixelValue(134, 1);
            display.setPixelValue(135, 1);
        } catch (DisplayException e) {
            e.printStackTrace();
        }

        System.out.println(memory);

        mainWindow.setVisible(true);
    }
}
