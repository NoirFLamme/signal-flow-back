package com.example.signalflowback;


import org.springframework.stereotype.Service;
import java.util.*;

import static java.lang.Thread.sleep;

@Service
public class LoopFinder {

    final ArrayList<Node> graph;
    final int size;
    double gainAccumulator;
    Set<Node> blockedSet;
    Map<Node, Set<Node>> blockedMap;
    Deque<Node> stack;
    ArrayList<Loop> loops;

    public LoopFinder(ArrayList<Node> graph) {
        this.graph = graph;
        this.size = graph.size();
    }
//0 -> 1
//1 -> 2
//2 -> 3,4
//3 -> 0
//4 -> 2
//link: https://github.com/mission-peace/interview/blob/master/src/com/interview/graph/AllCyclesInDirectedGraphJohnson.java
    public ArrayList<Loop> findAllLoops() {
        blockedSet = new HashSet<>();
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        loops = new ArrayList<>();
        int startIndex = 0;
        while(startIndex <= size) {
            ArrayList<Node> subGraph = createSubGraph(startIndex);
            Tarjan tarjan = new Tarjan(subGraph);
            ArrayList<ArrayList<Node>> sccs = tarjan.scc();
            Optional<Node> maybeLeastNode = leastIndexSCC(sccs,subGraph);
            if(maybeLeastNode.isPresent()) {
                Node leastVertex = maybeLeastNode.get();
                blockedSet.clear();
                blockedMap.clear();
                findLoopsInSCG(leastVertex, leastVertex);
                startIndex = leastVertex.getID() + 1;
            } else {
                break;
            }
        }
        return loops;
    }

    private ArrayList<Node> createSubGraph(int startVertex) {
        ArrayList<Node> subGraph = new ArrayList<>();
        for (int i=startVertex; i<size ; i++) subGraph.add(graph.get(i));
        for (Node n : subGraph){
            n.edgeArrayList.removeIf(e -> e.toNode.getID() < startVertex);
        }
        return subGraph;
    }

    private Optional<Node> leastIndexSCC(ArrayList<ArrayList<Node>> sccs, ArrayList<Node> subGraph) {
        long min = Integer.MAX_VALUE;
        Node minVertex = null;
        ArrayList<Node> minScc = null;
        for(ArrayList<Node> scc : sccs) {
            if(scc.size() == 1) continue;
            for(Node n : scc) {
                if(n.getID() < min) {
                    min = n.getID();
                    minVertex = n;
                    minScc = scc;
                }
            }
        }
        if(minVertex == null) return Optional.empty();
        ArrayList<Node> graphScc = new ArrayList<>();
        for(Node n : subGraph) {
            if(minScc.contains(n)) graphScc.add(n);
        }
        for (Node n : graphScc){
            ArrayList<Node> finalMinScc = minScc;
            n.edgeArrayList.removeIf(e -> !finalMinScc.contains(e.toNode));
        }
        return Optional.of(minVertex);
    }

    private boolean findLoopsInSCG(Node startNode, Node currentNode) {
        boolean foundCycle = false;
        stack.push(currentNode.getItWithoutEdges());
        blockedSet.add(currentNode);
        if (startNode==currentNode) gainAccumulator = 1;
        for (Edge e : currentNode.edgeArrayList) {
            Node neighbor = e.toNode;
            if (neighbor == startNode) {
                stack.push(startNode.getItWithoutEdges());
                gainAccumulator *= e.gain;
                Loop loop = new Loop(stack,gainAccumulator);
                gainAccumulator /= e.gain;
                stack.pop();
                loops.add(loop);
                foundCycle = true;
            }else if (!blockedSet.contains(neighbor)) {
                gainAccumulator *= e.gain;
                boolean gotCycle = findLoopsInSCG(startNode, neighbor);
                gainAccumulator /= e.gain;
                foundCycle = foundCycle || gotCycle;
            }
        }
        if (foundCycle) {
            unblock(currentNode);
        } else {
            for (Edge e : currentNode.edgeArrayList) {
                Node w = e.toNode;
                Set<Node> bSet = blockedMap.computeIfAbsent(w, (key) ->  new HashSet<>() );
                bSet.add(currentNode);
            }
        }
        stack.pop();
        return foundCycle;
    }




    private void unblock(Node u) {
        blockedSet.remove(u);
        if(blockedMap.get(u) != null) {
            blockedMap.get(u).forEach( v -> {
                if(blockedSet.contains(v)) {
                    unblock(v);
                }
            });
            blockedMap.remove(u);
        }
    }


    private boolean areNT (Loop l1,Loop l2){
        for (int i=0;i<l1.loopNodes.size();i++ ){
            for (int j=0;j<l2.loopNodes.size();j++){
                if ((l1.loopNodes.get(i)).equals(l2.loopNodes.get(j))){
                    return false;
                }
            }
        }
        return true;
    }



    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Test 1");
//        ArrayList<Edge> empty = new ArrayList<>();
//        Node n0 = new Node ("1",empty);
//        Node n1 = new Node ("2",empty);
//        Node n2 = new Node ("3",empty);
//        Node n3 = new Node ("4",empty);
//        Node n4 = new Node ("5",empty);
//        Edge e01 = new Edge(7,n1);
//        Edge e12 = new Edge(8,n2);
//        Edge e24 = new Edge(2,n4);
//        Edge e42 = new Edge(-5,n2);
//        Edge e23 = new Edge(-1,n3);
//        Edge e30 = new Edge(-3,n0);
//        ArrayList<Edge> el0 = new ArrayList<>();
//        el0.add(e01);
//        n0.setEdgeArrayList(el0);
//        ArrayList<Edge> el1 = new ArrayList<>();
//        el1.add(e12);
//        n1.setEdgeArrayList(el1);
//        ArrayList<Edge> el2 = new ArrayList<>();
//        el2.add(e24);
//        el2.add(e23);
//        n2.setEdgeArrayList(el2);
//        ArrayList<Edge> el3 = new ArrayList<>();
//        el3.add(e30);
//        n3.setEdgeArrayList(el3);
//        ArrayList<Edge> el4 = new ArrayList<>();
//        el4.add(e42);
//        n4.setEdgeArrayList(el4);
//        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n0,n1,n2,n3,n4));
//        LoopFinder myclass = new LoopFinder(nodes);
//        ArrayList<Loop> loops = myclass.findLoops();
//        printLoopList(loops);

        System.out.println("Test 2");
        ArrayList<Edge> empty = new ArrayList<>();
        Node n1 = new Node ("1",empty);
        Node n2 = new Node ("2",empty);
        Node n3 = new Node ("3",empty);
        Node n4 = new Node ("4",empty);
        Node n5 = new Node ("5",empty);
        Node n6 = new Node ("6",empty);
        Node n7 = new Node ("7",empty);
        Node n8 = new Node ("8",empty);
        Node n9 = new Node ("9",empty);
        Edge e12 = new Edge(15,n2);
        Edge e18 = new Edge( 3,n8);
        Edge e23 = new Edge( 8,n3);
        Edge e27 = new Edge( 5,n7);
        Edge e29 = new Edge(12,n9);
        Edge e31 = new Edge( 5,n1);
        Edge e32 = new Edge(-7,n2);
        Edge e34 = new Edge( 7,n4);
        Edge e36 = new Edge( 8,n6);
        Edge e45 = new Edge(-2,n5);
        Edge e52 = new Edge(11,n2);
        Edge e64 = new Edge(-6,n4);
        Edge e89 = new Edge( 1,n9);
        Edge e98 = new Edge(-2,n8);
        ArrayList<Edge> el1 = new ArrayList<>();
        el1.add(e12);
        el1.add(e18);
        n1.setEdgeArrayList(el1);
        ArrayList<Edge> el2 = new ArrayList<>();
        el2.add(e23);
        el2.add(e27);
        el2.add(e29);
        n2.setEdgeArrayList(el2);
        ArrayList<Edge> el3 = new ArrayList<>();
        el3.add(e31);
        el3.add(e32);
        el3.add(e34);
        el3.add(e36);
        n3.setEdgeArrayList(el3);
        ArrayList<Edge> el4 = new ArrayList<>();
        el4.add(e45);
        n4.setEdgeArrayList(el4);
        ArrayList<Edge> el5 = new ArrayList<>();
        el5.add(e52);
        n5.setEdgeArrayList(el5);
        ArrayList<Edge> el6 = new ArrayList<>();
        el6.add(e64);
        n6.setEdgeArrayList(el6);
        ArrayList<Edge> el8 = new ArrayList<>();
        el8.add(e89);
        n8.setEdgeArrayList(el8);
        ArrayList<Edge> el9 = new ArrayList<>();
        el9.add(e98);
        n9.setEdgeArrayList(el9);

        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(n1,n2,n3,n4,n5,n6,n7,n8,n9));
        LoopFinder myclass = new LoopFinder(nodes);
        ArrayList<Loop> loops = myclass.findAllLoops();
        printLoopList(loops);
    }

    private static void printLoopList(ArrayList<Loop> loops){
        for (Loop l : loops){
            System.out.println(l.loopNodes.toString() + " " + l.gain);
        }
    }

    private ArrayList<LinkedList<Loop>> findNTLs(ArrayList<Loop> loops)
    {
        ArrayList<LinkedList<Loop>> nonTouching = new ArrayList<>();
        ArrayList<Boolean> nonTouchingbool = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < loops.size(); i++)
        {
            nonTouching.add(new LinkedList<>());
            nonTouchingbool.add(false);
        }

        for (int i = 0; i < loops.size(); i++)
        {
            nonTouching.get(0).add(loops.get(i));
        }

        int j = 0;

        while (true) {
            flag = false;
            for (int i = 0; i < nonTouching.get(0).size(); i++) {
                int l = 0;
                if(j == 0)
                    l = i + 1;
                for (l = l; l < nonTouching.get(j).size(); l++)
                {
                    if (this.areNT(nonTouching.get(0).get(i), nonTouching.get(j).get(l)))
                    {
                        flag = true;
                        ArrayList<String> mergedLoops = new ArrayList<>(nonTouching.get(0).get(i).loopNodes);
                        mergedLoops.addAll(nonTouching.get(j).get(l).loopNodes);
                        double mergedGains = nonTouching.get(0).get(i).gain * nonTouching.get(j).get(l).gain;
                        nonTouching.get(j + 1).add(new Loop(mergedLoops, mergedGains));
                    }
                }
            }
            if (!flag)
            {
                break;
            }
            j++;
        }

        return nonTouching;
    }

}
