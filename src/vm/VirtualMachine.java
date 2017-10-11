package vm;

import vm.exceptions.DisplayException;
import vm.exceptions.FontLoaderException;
import vm.exceptions.ProgramLoaderException;

public class VirtualMachine extends Thread implements IODevice {
    private static final int MEMORY_SIZE = 0x1000;
    private static final int DISPLAY_SIZE = 0x800;

    private DisplayPanel displayPanel;
    private MainWindow mainWindow;
    private Memory memory;
    private CPU cpu;
    private FontLoader fontLoader;
    private ProgramLoader programLoader;
    private Display display;
    private Keyboard keyboard;


    public VirtualMachine() {
        memory = new Memory(MEMORY_SIZE);
        display = new Display(DISPLAY_SIZE);
        keyboard = new Keyboard();

        displayPanel = new DisplayPanel(display.getPixelArray());
        mainWindow = new MainWindow(displayPanel, keyboard);

        cpu = new CPU(memory, this);

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


    @Override
    public void run() {
        mainWindow.setVisible(true);

        while (true) {
            try {
                cpu.execute();

                try {
                    sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    @Override
    public void clearDisplay() {
        display.clear();
    }

    @Override
    public void repaintDisplay() {
        mainWindow.repaint();
    }

    @Override
    public void setPixelValue(int position, int value) {
        try {
            display.setPixelValue(position, value);
        } catch (DisplayException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public int getPixelValue(int position) {
        try {
            return display.getPixelValue(position);
        } catch (DisplayException e) {
            e.printStackTrace();
            System.exit(0);
            return 0;
        }
    }

    @Override
    public int getCurrentKeyPressed() {
        return keyboard.getCurrentKeyPressed();
    }
}
