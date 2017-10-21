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
public class Cmd_0x0033 implements CpuCommand {

  public Cmd_0x0033() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException, MemoryWriteException {
    // Fx33: Store BCD (Binary-Coded Decimal) representation of Vx in memory
    // locations I, I+1, and I+2 (LD B, Vx)
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    int value = cpu.getV(sourceRegister);

    int hundreds = (value - (value % 100)) / 100;
    value -= hundreds * 100;
    int tens = (value - (value % 10)) / 10;
    value -= tens * 10;

    final char I = cpu.getI();

    final Memory memory = cpu.getMemory();
    memory.writeByte(I, (char) hundreds);
    memory.writeByte(I + 1, (char) tens);
    memory.writeByte(I + 2, (char) value);

    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
  }

}
