import vm.VirtualMachine;

public class Main {
    public static void main(String[] args) {
        if(args.length != 0) {
            VirtualMachine virtualMachine = new VirtualMachine();
            virtualMachine.loadProgram(args[0]);
            virtualMachine.start();
        } else {
            System.err.println("Missing parameter with ROM path.");
        }
    }
}
