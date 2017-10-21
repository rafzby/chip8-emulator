package vm;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import vm.cpu_commands.CpuCommand;
import vm.cpu_commands._0xF000.Cmd_0x0000;
import vm.cpu_commands._0xF000.Cmd_0x1000;
import vm.cpu_commands._0xF000.Cmd_0x2000;
import vm.cpu_commands._0xF000.Cmd_0x3000;
import vm.cpu_commands._0xF000.Cmd_0x4000;
import vm.cpu_commands._0xF000.Cmd_0x5000;
import vm.cpu_commands._0xF000.Cmd_0x6000;
import vm.cpu_commands._0xF000.Cmd_0x7000;
import vm.cpu_commands._0xF000.Cmd_0x8000;
import vm.cpu_commands._0xF000.Cmd_0x9000;
import vm.cpu_commands._0xF000.Cmd_0xA000;
import vm.cpu_commands._0xF000.Cmd_0xB000;
import vm.cpu_commands._0xF000.Cmd_0xC000;
import vm.cpu_commands._0xF000.Cmd_0xD000;
import vm.cpu_commands._0xF000.Cmd_0xE000;
import vm.cpu_commands._0xF000.Cmd_0xF000;
import vm.exceptions.CpuException;
import vm.exceptions.MemoryReadException;
import vm.exceptions.MemoryWriteException;
import vm.exceptions.StackException;

public class CPU {
    private static final char PROGRAM_COUNTER_START = 0x200;
    private static final int STACK_SIZE = 16;
    private static final int REGISTERS_NUMBER = 16;
    private static final long TIMERS_INTERVAL = 17;

    private Memory memory;
    private Stack stack;
    private IODevice ioDevice;
    private char programCounter;
    private int delayTimer;
    private int soundTimer;
    private char[] V;
    private char I;

    private Map<Character, CpuCommand> cmdMap;

    public CPU(Memory memory, IODevice ioDevice) {
        this.memory = memory;
        this.ioDevice = ioDevice;

        stack = new Stack(STACK_SIZE);

        programCounter = PROGRAM_COUNTER_START;

        V = new char[REGISTERS_NUMBER];
        I = 0;

        delayTimer = 0;
        soundTimer = 0;

        initCommands();
        initTimers();
    }
    
    public void initCommands() {
      cmdMap = new HashMap<>();
      cmdMap.put((char) 0x0000, new Cmd_0x0000());
      cmdMap.put((char) 0x1000, new Cmd_0x1000());
      cmdMap.put((char) 0x2000, new Cmd_0x2000());
      cmdMap.put((char) 0x3000, new Cmd_0x3000());
      cmdMap.put((char) 0x4000, new Cmd_0x4000());
      cmdMap.put((char) 0x5000, new Cmd_0x5000());
      cmdMap.put((char) 0x6000, new Cmd_0x6000());
      cmdMap.put((char) 0x7000, new Cmd_0x7000());
      cmdMap.put((char) 0x8000, new Cmd_0x8000());
      cmdMap.put((char) 0x9000, new Cmd_0x9000());
      cmdMap.put((char) 0xA000, new Cmd_0xA000());
      cmdMap.put((char) 0xB000, new Cmd_0xB000());
      cmdMap.put((char) 0xC000, new Cmd_0xC000());
      cmdMap.put((char) 0xD000, new Cmd_0xD000());
      cmdMap.put((char) 0xE000, new Cmd_0xE000());
      cmdMap.put((char) 0xF000, new Cmd_0xF000());
    }

    public void execute() throws CpuException, InterruptedException, StackException, MemoryWriteException {
        try {
            final char opcode = memory.readOpcode(this.programCounter);
            final char c = (char) (opcode & 0xF000);
            final CpuCommand cmd = cmdMap.get(c);
            if (cmd != null) {
              cmd.execute(opcode, this);
            } else {
              throw new CpuException("Unable to read opcode from memory.");
            }
        } catch (MemoryReadException e) {
            throw new CpuException("Unable to read opcode from memory.");
        }
    }

    private void initTimers() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                decrementTimers();
            }
        }, TIMERS_INTERVAL, TIMERS_INTERVAL);
    }

    private void decrementTimers() {
        if (delayTimer != 0) {
            delayTimer--;
        }

        if (soundTimer != 0) {
            soundTimer--;
            ioDevice.playSound();
        }

        if (soundTimer == 0) {
            ioDevice.stopSound();
        }
    }

    public char getI() {
      return this.I;
    }
    
    public void setI(final char val) {
      this.I = val;
    }
    
    public void setV(final int register, final char val) {
      V[register] = val;
    }
    
    public char getV(final int register) {
      return V[register];
    }
    
    public void pushStack(final char c) throws StackException {
      stack.push(c);
    }
    
    public char popStack() throws StackException {
      return stack.pop();
    }
    
    public IODevice getIODevice() {
      return this.ioDevice;
    }
    
    public char getProgramCounter() {
      return this.programCounter;
    }
    
    public void setProgramCounter(final char cnt) {
      this.programCounter = cnt;
    }
    
    public Memory getMemory() {
      return this.memory;
    }
    
    public int getDelayTimer() {
      return this.delayTimer;
    }

    public void setDelayTimer(final int val) {
      this.delayTimer = val;
    }
    
    public int getSoundTimer() {
      return this.soundTimer;
    }

    public void setSoundTimer(final int val) {
      this.soundTimer = val;
    }
    
}
