package com.example.signalflowback.MasonHelpingClasses;

import com.example.signalflowback.MasonHelpingClasses.loopFindingHelpingClasses.Tarjan;
import com.example.signalflowback.createdDSs.*;
import java.util.*;

public class LoopsFinder {

    final ArrayList<Node> graph;
    final int size;
    double gainAccumulator;
    Set<Node> blockedSet;
    Map<Node, Set<Node>> blockedMap;
    Deque<Node> stack;
    ArrayList<Loop> loops;

    public LoopsFinder(ArrayList<Node> graph) {
        this.graph = graph;
        this.size = graph.size();
    }

    public ArrayList<Loop> findAllLoops() {
        blockedSet = new HashSet<>();
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        loops = new ArrayList<>();
        int startIndex = 0;
        while (startIndex <= size) {
            ArrayList<Node> subGraph = createSubGraph(startIndex);
            Tarjan tarjan = new Tarjan(subGraph);
            ArrayList<ArrayList<Node>> sccs = tarjan.scc();
            Optional<Node> maybeLeastNode = leastIndexSCC(sccs, subGraph);
            if (maybeLeastNode.isPresent()) {
                Node leastVertex = maybeLeastNode.get();
                blockedSet.clear();
                blockedMap.clear();
                findLoopsInSCG(leastVertex, leastVertex);
                startIndex = leastVertex.getID() + 1;
            } else {
                break;
            }
        }
        for (Node n : graph){
            for (Edge e : n.getEdgeArrayList()){
                Node toNode = e.getToNode();
                if (n.equals(toNode)){
                    ArrayList<String > temp = new ArrayList<>();
                    temp.add(n.getName());
                    temp.add(n.getName());
                    Loop loop = new Loop(temp,e.getGain());
                    loops.add(loop);
                }
            }
        }
        return loops;
    }

    private ArrayList<Node> createSubGraph(int startVertex) {
        ArrayList<Node> subGraph = new ArrayList<>();
        for (int i = startVertex; i < size; i++) subGraph.add(graph.get(i));
        for (Node n : subGraph) {
            n.getEdgeArrayList().removeIf(e -> e.getToNode().getID() < startVertex);
        }
        return subGraph;
    }

    private Optional<Node> leastIndexSCC(ArrayList<ArrayList<Node>> sccs, ArrayList<Node> subGraph) {
        long min = Integer.MAX_VALUE;
        Node minVertex = null;
        ArrayList<Node> minScc = null;
        for (ArrayList<Node> scc : sccs) {
            if (scc.size() == 1) continue;
            for (Node n : scc) {
                if (n.getID() < min) {
                    min = n.getID();
                    minVertex = n;
                    minScc = scc;
                }
            }
        }
        if (minVertex == null) return Optional.empty();
        ArrayList<Node> graphScc = new ArrayList<>();
        for (Node n : subGraph) {
            if (minScc.contains(n)) graphScc.add(n);
        }
        for (Node n : graphScc) {
            ArrayList<Node> finalMinScc = minScc;
            n.getEdgeArrayList().removeIf(e -> !finalMinScc.contains(e.getToNode()));
        }
        return Optional.of(minVertex);
    }

    private boolean findLoopsInSCG(Node startNode, Node currentNode) {
        boolean foundCycle = false;
        stack.push(currentNode.getItWithoutEdges());
        blockedSet.add(currentNode);
        if (startNode == currentNode) gainAccumulator = 1;
        for (Edge e : currentNode.getEdgeArrayList()) {
            Node neighbor = e.getToNode();
            if (neighbor == startNode) {
                stack.push(startNode.getItWithoutEdges());
                gainAccumulator *= e.getGain();
                Loop loop = new Loop(stack, gainAccumulator);
                gainAccumulator /= e.getGain();
                stack.pop();
                loops.add(loop);
                foundCycle = true;
            } else if (!blockedSet.contains(neighbor)) {
                gainAccumulator *= e.getGain();
                boolean gotCycle = findLoopsInSCG(startNode, neighbor);
                gainAccumulator /= e.getGain();
                foundCycle = foundCycle || gotCycle;
            }
        }
        if (foundCycle) {
            unblock(currentNode);
        } else {
            for (Edge e : currentNode.getEdgeArrayList()) {
                Node w = e.getToNode();
                Set<Node> bSet = blockedMap.computeIfAbsent(w, (key) -> new HashSet<>());
                bSet.add(currentNode);
            }
        }
        stack.pop();
        return foundCycle;
    }


    private void unblock(Node u) {
        blockedSet.remove(u);
        if (blockedMap.get(u) != null) {
            blockedMap.get(u).forEach(v -> {
                if (blockedSet.contains(v)) {
                    unblock(v);
                }
            });
            blockedMap.remove(u);
        }
    }


    private boolean areNT(ArrayList<String> l1, ArrayList<String> l2) {
        for (int i = 0; i < l1.size(); i++) {
            for (int j = 0; j < l2.size(); j++) {
                if ((l1.get(i)).equals(l2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }


    public ArrayList<LinkedList<NTLoopsCombination>> findNTLs(ArrayList<Loop> loops) {
        ArrayList<LinkedList<NTLoopsCombination>> nonTouching = new ArrayList<>();
        boolean foundCombination;
        for (int i = 0; i < loops.size()-1; i++) nonTouching.add(new LinkedList<>());
        for ( int i=0;i< loops.size();i++){
            for (int j=i+1;j< loops.size();j++){
                if (this.areNT(loops.get(i).getLoopNodes(), loops.get(j).getLoopNodes())) {
                    Set<String> mergedLoops = new HashSet<>(loops.get(i).getLoopNodes());
                    mergedLoops.addAll(loops.get(j).getLoopNodes());
                    double mergedGains = loops.get(i).getGain() * loops.get(j).getGain();
                    Set<Loop> mergeSet = new HashSet<>();
                    mergeSet.add(loops.get(i));
                    mergeSet.add(loops.get(j));
                    nonTouching.get(0).add(new NTLoopsCombination(mergedGains,mergeSet,mergedLoops));
                }
            }
        }
        int i = 0;
        if (nonTouching.size() == 0) {return nonTouching;}
        while (true) {
            foundCombination = false;
            for (int iOLoop = 0; iOLoop < loops.size(); iOLoop++) {
                for (int j = 0; j < nonTouching.get(i).size(); j++) {
                    ArrayList<String> temp = new ArrayList<>( nonTouching.get(i).get(j).getNodesAfterJoining());
                    if (this.areNT(loops.get(iOLoop).getLoopNodes(), temp)) {
                        foundCombination = true;
                        Set<String> mergedLoops = new HashSet<>(loops.get(iOLoop).getLoopNodes());
                        mergedLoops.addAll(nonTouching.get(i).get(j).getNodesAfterJoining());
                        double mergedGains = loops.get(iOLoop).getGain() * nonTouching.get(i).get(j).getGain();
                        Set<Loop> mergeSet = new HashSet<>();
                        mergeSet.add(loops.get(iOLoop));
                        mergeSet.addAll(nonTouching.get(i).get(j).getNTLoops());
                        NTLoopsCombination currentComb = new NTLoopsCombination(mergedGains,mergeSet,mergedLoops);
                        boolean foundDuplicate = false;
                        for (NTLoopsCombination ntc : nonTouching.get(i+1))
                            if (ntc.getNodesAfterJoining().equals(currentComb.getNodesAfterJoining()))
                                foundDuplicate = true;
                        if (!foundDuplicate)
                            nonTouching.get(i+1).add(currentComb);
                    }
                }
            }
            if (!foundCombination) {
                break;
            }
            i++;
        }

        return nonTouching;
    }

    public double getOverallDelta(ArrayList<Loop> loops, ArrayList<LinkedList<NTLoopsCombination>> nonTouching)
    {
        double overallDelta = 1;

        for (int i = 0; i < loops.size(); i++)
        {
           overallDelta -= loops.get(i).getGain();
        }

        int sign = 1;
        for (int i = 0; i < nonTouching.size(); i++)
        {
            for (int j = 0; j < nonTouching.get(i).size(); j++)
            {
                overallDelta += (sign) * (nonTouching.get(i).get(j).getGain());
            }
            sign *= -1;
        }

        return overallDelta;
    }

    public void getPathDelta(ArrayList<Loop> loops, ArrayList<LinkedList<NTLoopsCombination>> nonTouching,
                                ArrayList<ForwardPaths> paths)
    {
        double overallDelta ;
        for (ForwardPaths fp: paths) {
            overallDelta = 1;
            for (int i = 0; i < loops.size(); i++) {
                if (areNT(fp.getPath(), loops.get(i).getLoopNodes()))
                    overallDelta -= loops.get(i).getGain();
            }

            int sign = 1;
            for (int i = 0; i < nonTouching.size(); i++) {
                for (int j = 0; j < nonTouching.get(i).size(); j++) {
                    if (areNT(fp.getPath(), loops.get(i).getLoopNodes()))
                        overallDelta += (sign) * (nonTouching.get(i).get(j).getGain());
                }
                sign *= -1;
            }
            fp.delta = overallDelta;
        }
    }

    public double getTF(double delta, ArrayList<ForwardPaths> paths)
    {
        double TF = 0;
        for (ForwardPaths i : paths)
        {
            TF += i.getGain() * i.delta;
        }
        TF /= delta;

        return TF;
    }


}