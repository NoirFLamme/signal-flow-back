package com.example.signalflowback.Tests;

import com.example.signalflowback.MasonHelpingClasses.ForwardFinder;
import com.example.signalflowback.MasonHelpingClasses.LoopsFinder;
import com.example.signalflowback.createdDSs.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Test1 {
    public static <LoopFinder> void main(String[] args) throws InterruptedException {

        System.out.println("Test 1");
        ArrayList<Edge> empty = new ArrayList<>();
        Node n0 = new Node ("1",empty);
        Node n1 = new Node ("2",empty);
        Node n2 = new Node ("3",empty);
        Node n3 = new Node ("4",empty);
        Node n4 = new Node ("5",empty);
        Edge e01 = new Edge(7,n1);
        Edge e12 = new Edge(8,n2);
        Edge e24 = new Edge(2,n4);
        Edge e42 = new Edge(-5,n2);
        Edge e23 = new Edge(-1,n3);
        Edge e30 = new Edge(-3,n0);
        ArrayList<Edge> el0 = new ArrayList<>();
        el0.add(e01);
        n0.setEdgeArrayList(el0);
        ArrayList<Edge> el1 = new ArrayList<>();
        el1.add(e12);
        n1.setEdgeArrayList(el1);
        ArrayList<Edge> el2 = new ArrayList<>();
        el2.add(e24);
        el2.add(e23);
        n2.setEdgeArrayList(el2);
        ArrayList<Edge> el3 = new ArrayList<>();
        el3.add(e30);
        n3.setEdgeArrayList(el3);
        ArrayList<Edge> el4 = new ArrayList<>();
        el4.add(e42);
        n4.setEdgeArrayList(el4);
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n0,n1,n2,n3,n4));
        LoopsFinder myclass = new LoopsFinder(nodes);
        ArrayList<Loop> loops = myclass.findAllLoops();
        printLoopList(loops);




    }

    private static void printLoopList(ArrayList<Loop> loops) {
        for (Loop l : loops) {
            System.out.println(l.getLoopNodes().toString() + " " + l.getGain());
        }
    }

    private static void printNT(ArrayList<LinkedList<NTLoopsCombination>> nts) {
        for (LinkedList<NTLoopsCombination> ll : nts) {
            for (NTLoopsCombination l : ll){
                System.out.println(l.getNodesAfterJoining().toString() + " " + l.getGain());
            }
        }
    }

}
