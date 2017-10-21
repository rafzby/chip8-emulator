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
public class Cmd_0x0015 implements CpuCommand {

  public Cmd_0x0015() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Fx15: Set delay timer = Vx (LD DT, Vx)
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    cpu.setDelayTimer(cpu.getV(sourceRegister));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
