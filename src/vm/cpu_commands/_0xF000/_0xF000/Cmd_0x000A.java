/**
 * 
 */
package vm.cpu_commands._0xF000._0xF000;

import vm.CPU;
import vm.IODevice;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x000A implements CpuCommand {

  public Cmd_0x000A() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException, InterruptedException {
    // Fx0A: Wait for key press, store the value of the key in Vx (LD Vx, K)
    final IODevice ioDevice = cpu.getIODevice();
    final int targetRegister = (opcode & 0x0F00);

    int currentKey = ioDevice.getCurrentKeyPressed();
    while (currentKey == 0) {
      Thread.sleep(300);
      currentKey = ioDevice.getCurrentKeyPressed();
    }

    cpu.setV(targetRegister, (char) currentKey);
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
