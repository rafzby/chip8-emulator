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
public class Cmd_0x7000 implements CpuCommand {

  public Cmd_0x7000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 7xkk: Set Vx = Vx + kk (ADD Vx, byte)
    final int targetRegister = (opcode & 0x0F00) >> 8;
    final int value = (opcode & 0x00FF);
    cpu.setV(targetRegister, (char) ((cpu.getV(targetRegister) + value) & 0xFF));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
