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
public class Cmd_0x0018 implements CpuCommand {

  public Cmd_0x0018() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Fx18: Set sound timer = Vx (LD ST, Vx)
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    cpu.setSoundTimer(cpu.getV(sourceRegister));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
