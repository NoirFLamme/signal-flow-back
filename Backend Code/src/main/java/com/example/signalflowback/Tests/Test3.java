package com.example.signalflowback.Tests;

import com.example.signalflowback.MasonHelpingClasses.ForwardFinder;
import com.example.signalflowback.MasonHelpingClasses.LoopsFinder;
import com.example.signalflowback.createdDSs.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Test 3");
        ArrayList<Edge> empty = new ArrayList<>();
        Node n1 = new Node("1", empty);
        Node n2 = new Node("2", empty);
        Node n3 = new Node("3", empty);
        Node n4 = new Node("4", empty);
        Node n5 = new Node("5", empty);
        Node n6 = new Node("6", empty);
        Edge e12 = new Edge(2, n2);
        Edge e21 = new Edge(-2, n1);
        Edge e23 = new Edge(3, n3);
        Edge e34 = new Edge(4, n4);
        Edge e43 = new Edge(-4, n3);
        Edge e45 = new Edge(5, n5);
        Edge e56 = new Edge(6, n6);
        Edge e65 = new Edge(-6, n5);
        ArrayList<Edge> el1 = new ArrayList<>();
        el1.add(e12);
        n1.setEdgeArrayList(el1);
        ArrayList<Edge> el2 = new ArrayList<>();
        el2.add(e21);
        el2.add(e23);
        n2.setEdgeArrayList(el2);
        ArrayList<Edge> el3 = new ArrayList<>();
        el3.add(e34);
        n3.setEdgeArrayList(el3);
        ArrayList<Edge> el4 = new ArrayList<>();
        el4.add(e43);
        el4.add(e45);
        n4.setEdgeArrayList(el4);
        ArrayList<Edge> el5 = new ArrayList<>();
        el5.add(e56);
        n5.setEdgeArrayList(el5);
        ArrayList<Edge> el6 = new ArrayList<>();
        el6.add(e65);
        n6.setEdgeArrayList(el6);

        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n1, n2, n3, n4, n5, n6));
        LoopsFinder myclass = new LoopsFinder(nodes);
        ForwardFinder yes = new ForwardFinder();
        ArrayList<ForwardPaths> paths = yes.getAllPaths(nodes, "1", "6");
        ArrayList<Loop> loops = myclass.findAllLoops();
        System.out.println("Loops:");
        printLoopList(loops);
        System.out.println("Non Touching Loops:");
        ArrayList<LinkedList<NTLoopsCombination>> nt = myclass.findNTLs(loops);
        printNT(nt);
        System.out.println(myclass.getOverallDelta(loops, nt));
        myclass.getPathDelta(loops, nt, paths);
        System.out.println(myclass.getTF(myclass.getOverallDelta(loops, nt), paths));
    }

    private static void printLoopList(ArrayList<Loop> loops) {
        for (Loop l : loops) {
            System.out.println(l.getLoopNodes().toString() + " " + l.getGain());
        }
    }

    private static void printPaths(ArrayList<ForwardPaths> paths) {
        for (ForwardPaths p : paths) {
            System.out.println(p.getPath().toString() + " " + p.getGain());
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
