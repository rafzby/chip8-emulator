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
public class Cmd_0x0007 implements CpuCommand {

  public Cmd_0x0007() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Fx07: Set Vx = delay timer value (LD Vx, DT)
    final int targetRegister = (opcode & 0x0F00) >> 8;
    cpu.setV(targetRegister, (char) cpu.getDelayTimer());
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
