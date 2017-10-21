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
 */
public class Cmd_0x00E0 implements CpuCommand {

  public Cmd_0x00E0() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // 00E00: Clear the display (CLS)
    cpu.getIODevice().clearDisplay();
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
