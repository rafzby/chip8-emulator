import vm.Memory;
import vm.ProgramLoader;


public class Main {
    public static void main(String[] args) {
        Memory memory = new Memory();

        ProgramLoader programLoader = new ProgramLoader(memory);
        programLoader.loadProgram("/home/rafzby/Projects/chip8-emulator/resources/IBM Logo.c8");

        System.out.println(memory);
    }
}
