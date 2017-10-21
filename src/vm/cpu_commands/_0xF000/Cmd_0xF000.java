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
public class Cmd_0xF000 implements CpuCommand {

  private Map<Character, CpuCommand> cmdMap;

  public Cmd_0xF000() {
    cmdMap = new HashMap<>();
    cmdMap.put((char) 0x0007, new vm.cpu_commands._0xF000._0xF000.Cmd_0x0007());
    cmdMap.put((char) 0x000A, new vm.cpu_commands._0xF000._0xF000.Cmd_0x000A());
    cmdMap.put((char) 0x0015, new vm.cpu_commands._0xF000._0xF000.Cmd_0x0015());
    cmdMap.put((char) 0x0018, new vm.cpu_commands._0xF000._0xF000.Cmd_0x0018());
    cmdMap.put((char) 0x001E, new vm.cpu_commands._0xF000._0xF000.Cmd_0x001E());
    cmdMap.put((char) 0x0029, new vm.cpu_commands._0xF000._0xF000.Cmd_0x0029());
    cmdMap.put((char) 0x0033, new vm.cpu_commands._0xF000._0xF000.Cmd_0x0033());
    cmdMap.put((char) 0x0055, new vm.cpu_commands._0xF000._0xF000.Cmd_0x0055());
    cmdMap.put((char) 0x0065, new vm.cpu_commands._0xF000._0xF000.Cmd_0x0065());
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
