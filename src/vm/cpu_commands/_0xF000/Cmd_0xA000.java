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
public class Cmd_0xA000 implements CpuCommand {

  public Cmd_0xA000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Annn: Set I = nnn (LD I, addr)
    cpu.setI((char) (opcode & 0x0FFF));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
