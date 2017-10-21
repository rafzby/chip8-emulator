/**
 * 
 */
package vm.cpu_commands._0xF000;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x5000 implements CpuCommand {

  public Cmd_0x5000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 5xy0: Skip next instruction if Vx = Vy (SE Vx, Vy)
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    final int targetRegister = (opcode & 0x00F0) >> 4;
    cpu.setProgramCounter(
        (char) (cpu.getProgramCounter() + cpu.getV(sourceRegister) == cpu.getV(targetRegister) ? 4 : 2));
  }

}
