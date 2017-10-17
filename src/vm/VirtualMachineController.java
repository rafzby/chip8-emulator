package vm;

/**
 * Created by pmarc on 16/10/2017.
 */
public class VirtualMachineController {
    private VirtualMachine vm;
    private static VirtualMachineController instance;

    private VirtualMachineController(){}

    public static VirtualMachineController getInstance() {
        if(instance == null) {
            instance = new VirtualMachineController();
            MainWindow main = new MainWindow(instance);
            main.setup(null,null);
            main.setVisible(true);
        }
        return instance;
    }

    /**
     * Creates a new Virtual Machine and stops the current one if present.
     *
     * @param main
     * @param filePath
     */
    public void setRunner(MainWindow main,String filePath){
        if(vm != null && vm instanceof VirtualMachine) stopRunner();
        main.cleanDisplayPanel();
        vm = new VirtualMachine(main);
        vm.loadProgram(filePath);
        vm.start();
    }

    private void stopRunner(){
        vm.stopThread();
        vm = null;
    }

}
