package com.example.signalflowback.MasonHelpingClasses;

import com.example.signalflowback.createdDSs.Edge;
import com.example.signalflowback.createdDSs.ForwardPaths;
import com.example.signalflowback.createdDSs.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class ForwardFinder {
    private int getNode(String a, ArrayList<Node> graph)
    {
        for (int i = 0; i < graph.size(); i++)
        {
            if (graph.get(i).getName().equals(a))
            {
                return i;
            }
        }
        return -1;
    }

    ArrayList<String> pathAcc = new ArrayList<>();
    double gainAcc = 1;
    ArrayList<ForwardPaths> forwardPaths = new ArrayList<>();
    public ArrayList<ForwardPaths> getAllPaths(ArrayList<Node> graph, String start, String end)
    {
        boolean[] isVisited = new boolean[graph.size()];
        swap(graph, start, end);
        pathAcc.add(graph.get(0).getName());

        printAllPathsUtil(0, graph.size() - 1, isVisited, graph);

        for (ForwardPaths i: forwardPaths
             ) {
            System.out.println(i.getPath());
            System.out.println(i.getGain());
        }



        return this.forwardPaths;
    }

    private void swap(ArrayList<Node> a, String start, String end)
    {
        int startIndex = 0;
        int endIndex = a.size() - 1;
        for (int i = 0; i < a.size(); i++)
        {
            if (a.get(i).getName().equals(start))
            {
                startIndex = i;
            }
            if (a.get(i).getName().equals(end))
            {
                endIndex = i;
            }
        }

        Node temp = a.get(0);
        a.set(0, a.get(startIndex));
        a.set(startIndex, temp);

        temp = a.get(a.size() - 1);
        a.set(a.size() - 1, a.get(endIndex));
        a.set(endIndex, temp);
    }


    private void printAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited, ArrayList<Node> graph)
    {

        if (u.equals(d)) {
//            ArrayList<String> temp = pathAcc.clone();
            forwardPaths.add(new ForwardPaths((ArrayList<String>) pathAcc.clone(), gainAcc));
            // if match found then no need to traverse more till depth
            return;
        }

        // Mark the current node
        isVisited[u]= true;

        // Recur for all the vertices
        // adjacent to current vertex
        for (Edge i : graph.get(u).getEdgeArrayList()) {
            if (!isVisited[getNode(i.getToNode().getName(), graph)]) {
                // store current node
                // in path[]
                pathAcc.add(i.getToNode().getName());
                gainAcc *= i.getGain();
                printAllPathsUtil(getNode(i.getToNode().getName(), graph), d, isVisited, graph);

                // remove current node
                // in path[]
                gainAcc /= i.getGain();
                pathAcc.remove(i.getToNode().getName());
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }

    public static void main(String[] args) {
        ArrayList<Edge> empty = new ArrayList<>();
        Node n0 = new Node ("0",empty);
        Node n1 = new Node ("1",empty);
        Node n2 = new Node ("2",empty);
        Node n3 = new Node ("3",empty);
        Node n4 = new Node ("4",empty);
        Edge e01 = new Edge(7,n1);
        Edge e12 = new Edge(8,n2);
        Edge e24 = new Edge(2,n4);
        Edge e42 = new Edge(-5,n2);
        Edge e23 = new Edge(-1,n3);
        Edge e34 = new Edge(-3,n4);
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
        el3.add(e34);
        n3.setEdgeArrayList(el3);
        ArrayList<Edge> el4 = new ArrayList<>();
        el4.add(e42);
        n4.setEdgeArrayList(el4);
        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n0,n1,n2,n3,n4));
        ForwardFinder yes = new ForwardFinder();

    }
}
