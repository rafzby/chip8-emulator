/**
 * 
 */
package vm.cpu_commands._0xF000._0xF000;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x001E implements CpuCommand {

  public Cmd_0x001E() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Fx1E: Set I = I + Vx (ADD I, Vx)
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    cpu.setI((char) (cpu.getI() + cpu.getV(sourceRegister)));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
