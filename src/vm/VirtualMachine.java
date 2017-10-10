package vm;


public class VirtualMachine {
    private CPU cpu;
    private FontLoader fontLoader;
    private ProgramLoader programLoader;


    public VirtualMachine() {
        Memory memory = new Memory();

        fontLoader = new FontLoader(memory);
        programLoader = new ProgramLoader(memory);

        cpu = new CPU(memory);
    }

    public void loadProgram(String fileName) {

    }

    public void run() {

    }
}
