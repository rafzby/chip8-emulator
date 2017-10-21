/**
 * 
 */
package vm.cpu_commands._0xF000._0xE000;

import vm.CPU;
import vm.IODevice;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0x00A1 implements CpuCommand {

  public Cmd_0x00A1() {
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // ExA1: Skip next instruction if key with the value of Vx is not pressed
    // (SKNP Vx)
    final IODevice ioDevice = cpu.getIODevice();
    final int sourceRegister = (opcode & 0x0F00) >> 8;
    if (ioDevice.getCurrentKeyPressed() != cpu.getV(sourceRegister)) {
      cpu.setProgramCounter((char) (cpu.getProgramCounter() + 4));
    } else {
      cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
    }
  }

}
