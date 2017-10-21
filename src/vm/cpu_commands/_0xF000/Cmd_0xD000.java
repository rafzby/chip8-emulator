/**
 * 
 */
package vm.cpu_commands._0xF000;

import vm.CPU;
import vm.IODevice;
import vm.Memory;
import vm.cpu_commands.CpuCommand;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.StackException;

/**
 * @author Hans
 *
 */
public class Cmd_0xD000 implements CpuCommand {

  public Cmd_0xD000() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void execute(char opcode, CPU cpu) throws MemoryReadException, StackException, CpuException {
    // Dxyn: Display n-byte sprite starting at memory location I at (Vx, Vy),
    // set VF = collision (DRW Vx, Vy, nibble)
    final Memory memory = cpu.getMemory();
    final IODevice ioDevice = cpu.getIODevice();
    final int x = cpu.getV((opcode & 0x0F00) >> 8);
    final int y = cpu.getV((opcode & 0x00F0) >> 4);
    final int n = opcode & 0x000F;

    cpu.setV(0xF, (char) 0); // No collision

    for (int _y = 0; _y < n; _y++) {
      final int line = memory.readByte(cpu.getI() + _y);

      for (int _x = 0; _x < 8; _x++) {
        final int pixel = line & (0x80 >> _x);

        if (pixel != 0) {
          final int totalX = (x + _x) % 64;
          final int totalY = (y + _y) % 32;
          final int index = totalY * 64 + totalX;
          final int pixelValue = ioDevice.getPixelValue(index);

          if (pixelValue == 1) {
            cpu.setV(0xF, (char) 1);
          }

          ioDevice.setPixelValue(index, pixelValue ^ 1);
        }
      }
    }

    cpu.setProgramCounter((char) (cpu.getProgramCounter() + 2));
    ioDevice.repaintDisplay();
  }

}
