package vm;

import vm.exceptions.StackException;

public class Stack {
    private char[] stack;
    private int stackPointer;


    public Stack(int size) {
        stack = new char[size];
        stackPointer = -1;
    }

    public void push(char value) throws StackException {
        if (stackPointer >= stack.length - 1) {
            throw new StackException("Stack overflow.");
        }

        stack[++stackPointer] = value;
    }

    public char pop() throws StackException {
        if (stackPointer < 0) {
            throw new StackException("Empty stack.");
        }

        return stack[stackPointer--];
    }
}
