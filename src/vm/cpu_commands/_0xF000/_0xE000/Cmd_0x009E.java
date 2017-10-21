/**
 * 
 */
package vm.cpu_commands._0xF000._0xE000;

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
public class Cmd_0x009E implements CpuCommand {

  public Cmd_0x009E() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Ex9E: Skip next instruction if key with the value of Vx is pressed (SKP
    // Vx)
    final IODevice ioDevice = cpu.getIODevice();
    int sourceRegister = (opcode & 0x0F00) >> 8;

    if (ioDevice.getCurrentKeyPressed() == cpu.getV(sourceRegister)) {
      cpu.setProgramCounter((char) (cpu.getProgramCounter() + 4));
    } else {
      cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
    }
  }

}
