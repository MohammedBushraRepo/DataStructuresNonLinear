package UndirectedGraph;


import java.util.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class WeightedGraph {
    private class Node {
        private String label;
        private List<Edge> edges = new ArrayList<>();

        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public void addEdge(Node to, int weight) {
            edges.add(new Edge(this, to, weight));
        }

        public List<Edge> getEdges() {
            return edges;
        }
    }

    private class Edge {
        private Node from;
        private Node to;
        private int weight;

        //constructor to easily initialize an object
        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        //toString to easily debug
        @Override
        public String toString() {
            return from + "->" + to; // A->B
        }
    }

    private Map<String, Node> nodes = new HashMap<>();

    public void addNode(String label) {
        nodes.putIfAbsent(label, new Node(label));
    }

    public void addEdge(String from, String to, int weight) {
        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalArgumentException();

        var toNode = nodes.get(to);
        if (toNode == null)
            throw new IllegalArgumentException();

        fromNode.addEdge(toNode, weight);
        toNode.addEdge(fromNode, weight);
    }

    public void print() {
        for (var node : nodes.values()) {
            var edges = node.getEdges();
            if (!edges.isEmpty())
                System.out.println(node + " is connected to " + edges);
        }
    }

    //Dijkstra Algorithm **************************************************************************************

    private class NodeEntry{
        private Node node;
        private int priority;

        public NodeEntry(Node node, int priority) {
            this.node = node;
            this.priority = priority;
        }
    }
    public Path getShortestPath(String from , String to){

        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalArgumentException();

        var toNode = nodes.get(to);
        if (toNode == null)
            throw new IllegalArgumentException();
        //here calculate the shortest distance , first we create our distances Hash table
        //we have to the distance to all nodes  , except the starting node to the maximum int
        Map<Node , Integer> distances  = new HashMap<>();
        // now for every node on this graph , we need to add an entry in this table
        for (var node : nodes.values())
            distances.put(node , Integer.MAX_VALUE);
        //now we should set the distance between the node and itself to zero
        distances.replace(fromNode , 0);

        //here we should create a hash table for storing the previous Nodes
        Map<Node , Node > previousNodes = new HashMap<>();

        //after done with this we should create a set to keep track of the visited nodes
        Set<Node> visited = new HashSet<>();


        //here we use a priority queue instead of regular queue
        PriorityQueue <NodeEntry> queue = new PriorityQueue<>(
                //this type of queue use comparator to compare objects
                Comparator.comparingInt(ne -> ne.priority)//comparingInt is factory method to compare 2 integers
        );

        //now we start we should add our starting node to our queue
        queue.add(new NodeEntry(fromNode , 0) );

        // as long the queue is not empty we gonna repeat the algorithm
        while (!queue.isEmpty()){
            var current = queue.remove().node;
            visited.add(current);
            //now we should look at all un visited neighbours
            for (var edge : current.getEdges()) {
                if (visited.contains(to))  // if visited containes neighbour
                    continue;
                //otherwise we should calculate the new distance

                var newDistance = distances.get(current) +  edge.weight;
                //now we are in the middle in our graph and it get 5 miles to get to the current node from the starting node
                if (newDistance < distances.get(edge.to)) {
                    //we  should update our table we the new edge
                    distances.replace(edge.to , newDistance);
                    //also we should update previous table we the previous Node
                    previousNodes.put(edge.to , current);
                    //now we have new distance we should push this neighbour to our queue
                    queue.add (new NodeEntry(edge.to     , newDistance));
                }
            }
        }
        return buildPath(previousNodes , toNode) ;
    }

    //to organise the logic , extract it int helper method
    //this method take 2 parameteres the hash table and the target Node
    private Path buildPath(Map<Node , Node > previousNodes ,Node toNode){
        //at the end we can collect and calculate the shortest path in a stack
        Stack<Node> stack = new Stack<>();
        //intially we want to add the ending node to the stack
        stack.push(toNode);
        //now we gonna get all the previous nodes
        var previous = previousNodes.get(toNode);
        while (previous != null){
            //we gonna push this previous into our stack
            stack.push(previous);
            //then set prvious to previous of the current node , so will go to our hasj table one more time and get the previous node
            previous = previousNodes.get(previous);
        }
        //here our stack is ready , so now we gonna create Path object form this stack
        var path = new Path();
        while (!stack.isEmpty()){
            path.add(stack.pop().label);//access label vield because the return is String in the path
        }
        return path;
    }



    //**************************************Cycle Detection

    public boolean hasCycle(){
        //here we're going to create a set of node
        Set<Node> visited = new HashSet<>();

            //here we need to iterate over all node on this graph   , and start Depth-First From that Node
        for (var node : nodes.values()){
            //if we didn't visit this node we're going to start our depth-first search from this node
            if (!visited.contains(node) &&
                    hasCycle(node, null, visited));
            return true;


        }
        return false;

    }


    //recursive method for our depth first Searach
    private boolean hasCycle(Node node , Node parent , Set<Node> visited){
            //first we have to this node to the visited Set
        visited.add(node);
        //then we look at all the neighbours of this node
        for (var edge : node.getEdges()){
            //if this neighbour is where we come from we gonna ignore that , and get another neighbour

            if (edge.to == parent )    //so this is where we come from
                    continue;
                 //if this neighbour is existed in the visited set , that mean we have a cycle so we gonna return true
                //if we haven't visited this neighbour we should recursively visit that neighbour
                //call hasCycle method give it this neighbor , as Parent pass current Node , visited nodes
            if (visited.contains(edge.to) || hasCycle(edge.to , node , visited))
                    return true;
        }
        return false;


    }


    //*******************************************************************
    //Spanning Trees :- using Prims Algorithm
    public WeightedGraph getMinimumSpanningTree(){
        var tree = new WeightedGraph();

        //here we need a Priority queue to find the minimum weighted Edge
        PriorityQueue<Edge> edges = new PriorityQueue<>(//here we need to Comparator Object as a constructor, to allow priority queue to order the edges
                 Comparator.comparingInt(e -> e.weight));

        //now we need to pick a node from our graph and add all of it edges to this priority queue
        var startNode = nodes.values().iterator().next();   //iterator : used to read one item of collection ,next: this will return a node object

        //now we need to add this edges to our Priority Queue
        edges.addAll(startNode.getEdges());
        //so also we need this  startNode to our spanning tree , so every time we discover Node we give to our tree
        tree.addNode(startNode.label); //be aware nodes here are different from nodes in our graph , in memory we work in another object

        //now initialization now we have to repeat
        while (tree.nodes.size() < nodes.size()){
            // in every iteration we gonna remove the minimum edge weight from our queue
            var minEdge = edges.remove();
            var nextNode = minEdge.to;

            if (tree.containsNode(nextNode.label))
                continue;

            tree.addNode(nextNode.label);
            tree.addEdge(minEdge.from.label,
                    nextNode.label, minEdge.weight);

            for (var edge : nextNode.getEdges())
                if (!tree.containsNode(edge.to.label))
                    edges.add(edge);
        }

        return tree;
    }

    public boolean containsNode(String label) {
        return nodes.containsKey(label);
    }



}




















