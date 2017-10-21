/**
 * 
 */
package vm.cpu_commands._0xF000;

import java.util.Random;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0xC000 implements CpuCommand {

  public Cmd_0xC000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Cxkk: Set Vx = random byte AND kk (RND Vx, byte)
    final int targetRegister = (opcode & 0x0F00) >> 8;
    final int value = opcode & 0x00FF;
    final int randomNumber = new Random().nextInt(256) & value;
    cpu.setV(targetRegister, (char) randomNumber);
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
