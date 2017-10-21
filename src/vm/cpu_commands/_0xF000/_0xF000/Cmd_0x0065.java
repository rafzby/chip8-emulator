/**
 * 
 */
package vm.cpu_commands._0xF000._0xF000;

import vm.CPU;
import vm.Memory;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x0065 implements CpuCommand {

  public Cmd_0x0065() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Fx65: Read registers V0 through Vx from memory starting at location I
    // (LD Vx, [I])
    final Memory memory = cpu.getMemory();
    final int numRegisters = (opcode & 0x0F00) >> 8;
    final char I = cpu.getI();
    for (int i = 0; i <= numRegisters; i++) {
      cpu.setV(i, memory.readByte(I + i));
    }

    cpu.setI((char) (I + numRegisters + 1));
    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
