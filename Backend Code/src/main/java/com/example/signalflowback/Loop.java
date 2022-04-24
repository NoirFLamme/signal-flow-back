package com.example.signalflowback;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Loop {
    String loopNodes;
    double gain;

    public Loop(Deque<String> nodeStack, double gain) {
        Deque<String> copyOfStack = new LinkedList<>(nodeStack);
        StringBuilder loopNodes = new StringBuilder();
        while (!copyOfStack.isEmpty()) loopNodes.append(copyOfStack.removeLast());
        this.loopNodes = loopNodes.toString();
        this.gain = gain;
    }
//loopNodes reverse
//gain case when true
}
