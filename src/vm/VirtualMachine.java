package vm;

import vm.exceptions.CpuException;
import vm.exceptions.FontLoaderException;
import vm.exceptions.ProgramLoaderException;

public class VirtualMachine extends Thread {
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

        cpu = new CPU(memory, display);

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

        while(true) {
            try {
                cpu.execute();

                // if needsRepaint
                mainWindow.repaint();

                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(0);
                }

            } catch (CpuException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
