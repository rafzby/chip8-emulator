/**
 * 
 */
package vm.cpu_commands._0xF000._0xF000;

import vm.CPU;
import vm.Memory;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.MemoryWriteException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x0055 implements CpuCommand {

  public Cmd_0x0055() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException, MemoryWriteException {
    // Fx55: Store registers V0 through Vx in memory starting at location I
    // (LD [I], Vx)
    final Memory memory = cpu.getMemory();
    final int numRegisters = (opcode & 0x0F00) >> 8;
    final char I = cpu.getI();
    for (int i = 0; i <= numRegisters; i++) {
      memory.writeByte(I + i, cpu.getV(i));
    }
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
