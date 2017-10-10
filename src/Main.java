import vm.VirtualMachine;


public class Main {
    public static void main(String[] args) {
        VirtualMachine virtualMachine = new VirtualMachine();

        virtualMachine.initialize();
        virtualMachine.loadProgram("/home/rafzby/Projects/chip8-emulator/resources/IBM Logo.c8");
        virtualMachine.run();
    }
}
