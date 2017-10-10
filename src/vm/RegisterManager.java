package vm;

import java.util.ArrayList;

public class RegisterManager {
    private ArrayList<Register> registers;


    public RegisterManager(int size) {
        registers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            registers.add(i, new Register());
        }
    }

    public char getRegisterValue(int index) {
        return registers.get(index).getValue();
    }

    public void setRegisterValue(int index, char value) {
        registers.get(index).setValue(value);
    }
}
