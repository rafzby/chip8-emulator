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
public class Cmd_0x2000 implements CpuCommand {

  public Cmd_0x2000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 2nnn: Call subroutine at nnn (CALL addr)
    cpu.pushStack(cpu.getProgramCounter());
    cpu.setProgramCounter((char) (opcode & 0x0FFF));
  }

}
