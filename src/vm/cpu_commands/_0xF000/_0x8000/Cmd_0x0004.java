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
public class Cmd_0x0004 implements CpuCommand {

  public Cmd_0x0004() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 8xy4: Set Vx = Vx + Vy, set VF = carry
    final int targetRegister = (opcode & 0x0F00) >> 8;
    final int sourceRegister = (opcode & 0x00F0) >> 4;
    final int result = cpu.getV(targetRegister) + cpu.getV(sourceRegister);
    cpu.setV(0xF, (char) (result > 0xFF ? 1 : 0));
    cpu.setV(targetRegister, (char) (result & 0xFF));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
