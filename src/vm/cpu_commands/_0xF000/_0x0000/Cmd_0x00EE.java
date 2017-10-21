/**
 * 
 */
package vm.cpu_commands._0xF000._0x0000;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x00EE implements CpuCommand {

  /**
   * 
   */
  public Cmd_0x00EE() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 00EE: Return from a subroutine (RET)
    cpu.setProgramCounter((char) (cpu.popStack() + 2));
  }

}
