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
 *
 */
public class Cmd_0x0000 implements CpuCommand {

  private Map<Character, CpuCommand> cmdMap;

  public Cmd_0x0000() {
    cmdMap = new HashMap<>();
    cmdMap.put((char) 0x00E0, new vm.cpu_commands._0xF000._0x0000.Cmd_0x00E0());
    cmdMap.put((char) 0x00EE, new vm.cpu_commands._0xF000._0x0000.Cmd_0x00EE());
  }

  @Override
  public void execute(char opcode, final CPU cpu)
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
