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
public class Cmd_0x8000 implements CpuCommand {

  private Map<Character, CpuCommand> cmdMap;

  public Cmd_0x8000() {
    cmdMap = new HashMap<>();
    cmdMap.put((char) 0x0000, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0000());
    cmdMap.put((char) 0x0001, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0001());
    cmdMap.put((char) 0x0002, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0002());
    cmdMap.put((char) 0x0003, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0003());
    cmdMap.put((char) 0x0004, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0004());
    cmdMap.put((char) 0x0005, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0005());
    cmdMap.put((char) 0x0006, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0006());
    cmdMap.put((char) 0x0007, new vm.cpu_commands._0xF000._0x8000.Cmd_0x0007());
    cmdMap.put((char) 0x000E, new vm.cpu_commands._0xF000._0x8000.Cmd_0x000E());
  }

  @Override
  public void execute(char opcode, CPU cpu)
      throws MemoryReadException, StackException, CpuException, MemoryWriteException, InterruptedException {
    final char c = (char) (opcode & 0x000F);
    final CpuCommand cmd = cmdMap.get(c);
    if (cmd != null) {
      cmd.execute(opcode, cpu);
    } else {
      throw new CpuException("Unsupported opcode.");
    }
  }

}
