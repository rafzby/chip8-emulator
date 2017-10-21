/**
 * 
 */
package vm.cpu_commands._0xF000._0xF000;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x0029 implements CpuCommand {

  public Cmd_0x0029() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Fx29: Set I = location of sprite for digit Vx (LD F, Vx)
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    cpu.setI((char) (0x050 + cpu.getV(sourceRegister) * 5));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
