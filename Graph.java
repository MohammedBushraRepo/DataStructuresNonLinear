package Graph;

import java.util.*;

public class Graph {
    private class Node {
        private String label;


        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label ;
        }
    }

    private Map<String , Node> nodes = new HashMap<>();

    //Adjacency List
    private Map<Node , List<Node>> adjacencyList = new HashMap<>();

        public void addNode(String label){
            var node = new Node(label);//instantiate new node object
            nodes.putIfAbsent(label , node); // put the new label with its node in the hash tabel

            //once we add a node , it is good to add an entry in adjacencyList
            adjacencyList.putIfAbsent(node , new ArrayList<>());
            //so know every Node has an empty list , where we can insert the node it is connected to
        }

        public void addEdge(String from , String to){
            // first check that the arguments thats passed here are valid nodes in our graph
            var fromNode = nodes.get(from);
            if(fromNode == null)
                throw new IllegalArgumentException();
            // also the other part
            var toNode = nodes.get(to);
            if (toNode == null)
                throw new IllegalArgumentException();


            adjacencyList.get(fromNode).add(toNode);
        }

        //print method
     public void print(){
             // we are going to iterate over the adjacency list
         for (var source : adjacencyList.keySet()) {// this will return all the source nodes
            //here for each Node we should get the target node
             var target  = adjacencyList.get(source);
             if (!target.isEmpty())
                 System.out.println(source + "is connected to" + target);
         }
        }

        //remove operation
    public void removeNode(String label){
        // first check if the node is valid
        var node  = nodes.get(label);
        if (node == null)
            return;
        //now we have a valid node , to remove it we have to go to  adjacencyList
        //and for each node , if the that node is connected to this node we have to remove this connection
        for (var n : adjacencyList.keySet())//return all keys , so that will be every node in this garph
        {
            adjacencyList.get(n).remove(node);
            //return what n connected with and remove the target node
        }
        //once we remove all the links , we ready to remove this node
        adjacencyList.remove(node);
        nodes.remove(node);
    }
    //remove edge operation
    public void removeEdge(String from , String to){
            // check if the arguments that passed here represent valid nodes
        var fromNode = nodes.get(from);
        var toNode = nodes.get(to);

        if (fromNode == null || toNode == null)
            return;
        adjacencyList.get(fromNode).remove(toNode);
    }


    // Depth - traversing / using recurtion

    public void traversingDepthFirst(String root){
            var node = nodes.get(root);
            if (node == null)
                return;

            traversingDepthFirst(nodes.get(root) , new HashSet<>());
    }

    private void traversingDepthFirst(Node root , Set<Node> visited){ // using set to keep track of our node
     //first print the root node
        System.out.println(root);
        visited.add(root);//store its key

        //now we are gonna recursively visiting all neighbours of this root
        for (var node : adjacencyList.get(root))
                if (!visited.contains(node))
                    traversingDepthFirst(node , visited);


    }


    //depth - first using iteration
    public void traverseDepthFirstIter(String root) {
            //initially we validate our node
        var node = nodes.get(root);
        if (node == null)
            return;

        //create a Set with visited node

        Set<Node> visited = new HashSet<>();

        //also create stack and push the root node here
        Stack<Node> stack = new Stack<>();
        stack.push(node);



        //While stack is not empty , we pop ine item
        while (!stack.isEmpty()) {
            var current = stack.pop();



        //we make sure we did visit this node before
            if (visited.contains(current))
                continue;



        //then we gonna visited
            System.out.println(current);
            visited.add(current);

            //then we gonna look at un visited neighbors

            for (var neighbour : adjacencyList.get(current))
                if (!visited.contains(neighbour))
                    stack.push(neighbour);
        }
    }


    public void traverseBreadthFirst(String root) {
        var node = nodes.get(root);
        if (node == null)
            return;

        Set<Node> visited = new HashSet<>();

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            var current = queue.remove();

            if (visited.contains(current))
                continue;

            System.out.println(current);
            visited.add(current);

            for (var neighbour : adjacencyList.get(current))
                if (!visited.contains(neighbour))
                    queue.add(neighbour);
        }
    }


    //topological sorting algorithm

    public List<String> topologicalSort() {
       //to implement the private method we need stack , set , list
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for (var node : nodes.values()) //for each node we gonna call topologicalSort
            topologicalSort(node, visited, stack);

        //once we done here our stack should be populated with nodes in the reverse order
//so pop all items in the stack and put them on a list
        List<String> sorted = new ArrayList<>();
        while (!stack.empty())
            sorted.add(stack.pop().label);

        return sorted;
    }

    private void topologicalSort(
            Node node, Set<Node> visited, Stack<Node> stack) {
     //here make sure we are not visiting the same node twice
        if (visited.contains(node))
            return;

        //otherwise we gonna mark this node as visited
        visited.add(node);
//
        //next we gonna recursively visit all children of this node
        for (var neighbour : adjacencyList.get(node))
            topologicalSort(neighbour, visited, stack);


        //once we visited all children(neighbours) of the given node , we gonna push to the stack
        stack.push(node);
    }


    //note := topological sorting will not work cycle graph
    // so we should detect


    public boolean hasCycle() {
        Set<Node> all = new HashSet<>();
        all.addAll(nodes.values());

        Set<Node> visiting = new HashSet<>();
        Set<Node> visited = new HashSet<>();

        while (!all.isEmpty()) {
            var current = all.iterator().next();
            if (hasCycle(current, all, visiting, visited))
                return true;
        }

        return false;
    }

    private boolean hasCycle(Node node, Set<Node> all,
                             Set<Node> visiting, Set<Node> visited) {
        all.remove(node);
        visiting.add(node);

        for (var neighbour : adjacencyList.get(node)) {
            if (visited.contains(neighbour))
                continue;

            if (visiting.contains(neighbour))
                return true;

            if (hasCycle(neighbour, all, visiting, visited))
                return true;
        }

        visiting.remove(node);
        visited.add(node);

        return false;
    }





}
