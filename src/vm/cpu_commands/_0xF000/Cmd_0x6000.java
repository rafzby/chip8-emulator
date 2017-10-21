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
public class Cmd_0x6000 implements CpuCommand {

  public Cmd_0x6000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 6xkk: Set Vx = kk (LD Vx, byte)
    final int targetRegister = (opcode & 0x0F00) >> 8;
    cpu.setV(targetRegister, (char) (opcode & 0x00FF));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
