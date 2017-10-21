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
public class Cmd_0xB000 implements CpuCommand {

  public Cmd_0xB000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Bnnn: Jump to location nnn + V0 (JP V0, addr)
    cpu.setProgramCounter((char) (cpu.getI() + (opcode & 0x0FFF)));
  }

}
