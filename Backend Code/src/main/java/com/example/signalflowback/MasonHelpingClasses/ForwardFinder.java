package com.example.signalflowback.MasonHelpingClasses;

import com.example.signalflowback.createdDSs.*;
import java.util.ArrayList;


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


}
