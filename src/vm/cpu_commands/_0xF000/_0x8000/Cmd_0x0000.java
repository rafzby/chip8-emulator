/**
 * 
 */
package vm.cpu_commands._0xF000._0x8000;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x0000 implements CpuCommand {

  public Cmd_0x0000() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 8xy0: Set Vx = Vy (LD Vx, Vy)
    final int targetRegister = (opcode & 0x0F00) >> 8;
    final int sourceRegister = (opcode & 0x00F0) >> 4;
    cpu.setV(targetRegister, cpu.getV(sourceRegister));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
