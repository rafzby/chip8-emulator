package vm;

public class Stack {
    private char[] stack;
    private int stackPointer;


    public Stack(int size) {
        stack = new char[size];
        stackPointer = -1;
    }

    public void push(char value) {
        stack[++stackPointer] = value;
    }

    public char pop() {
        return stack[--stackPointer];
    }
}
