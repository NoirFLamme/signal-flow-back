package com.example.signalflowback;


import java.util.ArrayList;

public class ForwardPaths {
    ArrayList<String> path;
    double gain;
    double delta;

    public ForwardPaths(ArrayList<String> path, double gain) {
        this.path = path;
        this.gain = gain;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }
}
