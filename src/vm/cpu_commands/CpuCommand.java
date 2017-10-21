/**
 * 
 */
package vm.cpu_commands;

import vm.CPU;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.MemoryWriteException;
import vm.exceptions.StackException;

/**
 * @author Hans
 */
@FunctionalInterface
public interface CpuCommand {
  public void execute(final char opcode, final CPU cpu)
      throws MemoryReadException, MemoryWriteException, StackException, CpuException, InterruptedException;
}
