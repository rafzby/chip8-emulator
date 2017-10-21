/**
 * 
 */
package vm.cpu_commands._0xF000._0x8000;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x0002 implements CpuCommand {

  public Cmd_0x0002() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 8xy2: Set Vx = Vx AND Vy (AND Vx, Vy)
    final int targetRegister = (opcode & 0x0F00) >> 8;
    final int sourceRegister = (opcode & 0x00F0) >> 4;
    cpu.setV(targetRegister, (char) (cpu.getV(targetRegister) & cpu.getV(sourceRegister)));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
