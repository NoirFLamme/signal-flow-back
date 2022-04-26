package com.example.signalflowback.MasonHelpingClasses.loopFindingHelpingClasses;

import com.example.signalflowback.createdDSs.Edge;
import com.example.signalflowback.createdDSs.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Tarjan {

    int index;
    ArrayList<Node> dg;
    Stack<Node> stack;
    Map<Node, Integer> indexMap;
    Map<Node, Integer> lowLinkMap;

    public ArrayList<ArrayList<Node>> scc() {
        this.index = 0;
        stack = new Stack<>();
        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        for (Node v : this.dg) {
            if (indexMap.get(v) == null) {
                result.addAll(strongConnect(v));
            }
        }
        return result;
    }

    public Tarjan(ArrayList<Node> dg) {
        this.index = 0;
        this.dg = dg;
        this.indexMap = new HashMap<>();
        this.lowLinkMap = new HashMap<>();
    }

    public ArrayList<ArrayList<Node>> strongConnect(Node v) {
        indexMap.put(v, index);
        lowLinkMap.put(v, index);
        index++;
        stack.push(v);
        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        for (Edge e : v.getEdgeArrayList()) {
            Node w = e.getToNode();
            if (lowLinkMap.size()==4 && w.getID()==3){
                System.out.println();
            }
            if (indexMap.get(w) == null) {
                ArrayList<ArrayList<Node>> temp = strongConnect(w);
                result.addAll(temp);
                lowLinkMap.put(v, Math.min(lowLinkMap.get(v), lowLinkMap.get(w)));
            } else {
                if (stack.contains(w)) {
                    lowLinkMap.put(v, Math.min(lowLinkMap.get(v), indexMap.get(w)));
                }
            }
        }

        if (lowLinkMap.get(v).equals(indexMap.get(v))) {
            ArrayList<Node> sccList = new ArrayList<>();
            while (true) {
                Node w = stack.pop();
                sccList.add(w);
                if (w.equals(v)) {
                    break;
                }
            }
            if (sccList.size() > 1) { result.add(sccList); }
        }
        return result;
    }
}
