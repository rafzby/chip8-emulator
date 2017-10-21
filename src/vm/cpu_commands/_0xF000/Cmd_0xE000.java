/**
 * 
 */
package vm.cpu_commands._0xF000;

import java.util.HashMap;
import java.util.Map;

import vm.CPU;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.MemoryWriteException;
import vm.exceptions.StackException;

/**
 * @author Hans
 */
public class Cmd_0xE000 implements CpuCommand {

  private Map<Character, CpuCommand> cmdMap;

  public Cmd_0xE000() {
    cmdMap = new HashMap<>();
    cmdMap.put((char) 0x00A1, new vm.cpu_commands._0xF000._0xE000.Cmd_0x00A1());
    cmdMap.put((char) 0x009E, new vm.cpu_commands._0xF000._0xE000.Cmd_0x009E());
  }

  @Override
  public void execute(char opcode, CPU cpu)
      throws MemoryReadException, StackException, CpuException, MemoryWriteException, InterruptedException {
    final char c = (char) (opcode & 0x00FF);
    final CpuCommand cmd = cmdMap.get(c);
    if (cmd != null) {
      cmd.execute(opcode, cpu);
    } else {
      throw new CpuException("Unsupported opcode.");
    }
  }

}
