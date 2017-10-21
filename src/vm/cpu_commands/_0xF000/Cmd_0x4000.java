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
public class Cmd_0x4000 implements CpuCommand {

  public Cmd_0x4000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 4xkk: Skip next instruction if Vx != kk (SNE Vx, byte)
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    final int value = (opcode & 0x00FF);
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + cpu.getV(sourceRegister) != value ? 4 : 2));
  }

}
