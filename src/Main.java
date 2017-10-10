import vm.Memory;
import vm.ProgramLoader;
import vm.FontLoader;
import vm.exceptions.FontLoaderException;
import vm.exceptions.ProgramLoaderException;


public class Main {
    public static void main(String[] args) {
        Memory memory = new Memory();

        FontLoader fontLoader = new FontLoader(memory);
        ProgramLoader programLoader = new ProgramLoader(memory);

        try {
            fontLoader.loadFont();
        } catch (FontLoaderException e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            programLoader.loadProgram("/home/rafzby/Projects/chip8-emulator/resources/IBM Logo.c8");
        } catch (ProgramLoaderException e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println(memory);
    }
}
