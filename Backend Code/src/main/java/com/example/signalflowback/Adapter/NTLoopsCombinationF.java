package com.example.signalflowback.Adapter;

public class NTLoopsCombinationF {
    public String getLoops() {
        return loops;
    }

    public void setLoops(String loops) {
        this.loops = loops;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    String loops;
    double gain;

    public NTLoopsCombinationF(String loops, double gain) {
        this.loops = loops;
        this.gain = gain;
    }
}
