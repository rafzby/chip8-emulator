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
public class Cmd_0x0006 implements CpuCommand {

  public Cmd_0x0006() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 8xy6: Set Vx = Vx SHR 1 (SHR Vx {, Vy})
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    cpu.setV(0xF, (char) ((cpu.getV(sourceRegister) & 0x000F) == 1 ? 1 : 0));
    cpu.setV(sourceRegister, (char) (cpu.getV(sourceRegister) / 2));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
