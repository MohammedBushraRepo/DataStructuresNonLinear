import Graph.Graph;
import UndirectedGraph.WeightedGraph;

public class Main {
    public static void main(String[] args) {
        var graph = new WeightedGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        // graph.addNode("D");
        graph.addEdge("A","B" , 1);
        graph.addEdge("B" , "C" , 2);
        // graph.addEdge("D" , "C");
        graph.addEdge("A" , "C" , 10);


        var path = graph.getShortestPath("A" , "C");
        System.out.println(path);
        //graph.traverseBreadthFirst("A");
    }
 }





/*
        var trie = new Trie();
        trie.insert("car");
        trie.insert("care");
        trie.insert("card");
        trie.insert("careful");
        trie.insert("egg");
        var words = trie.findWords("car");
        System.out.println(words);*/



//System.out.println(trie.contains("can"));
        /*//Heap Sort
        int[] numbers = {5,3,8,4,1,2};
        System.out.println(Heapyfi.getKthLargest(numbers , 3));
*/


       /* Heapyfi.heapify(numbers);
        System.out.println(Arrays.toString(numbers));*/



       /* //this heap is max heap , the root will hold the max value
        for (var number : numbers)
            heap.insert(number);//loop over the array and insert each number to the heap
        //now we have sorted heap from max value when we remove large(root) , the second largest value will move to the root
        // when we keep removing this values from the heap they will come in descending order
        //to print the out put*/
       /* while (!heap.isEmpty())
            System.out.println(heap.remove());*/

// arrange it in array descending order
       /* for (var i = 0 ; i < numbers.length ; i++)
            numbers[i] = heap.remove();
        System.out.println(Arrays.toString(numbers));*/

//to start from ascending order , change the direction of this loop , start from the last element
       /* for (var i = numbers.length -1 ; i >= 0 ; i--)
            numbers[i] = heap.remove();
        System.out.println(Arrays.toString(numbers));*/







       /* Heap heap = new Heap();
        heap.insert(10);
        heap.insert(5);
        heap.insert(17);
        heap.insert(4);
        heap.insert(22);
heap.remove();
        System.out.println("Done");*/







































/*AVLTress testing*/




  /*      var tree = new AVLTree();*/
  /*      tree.insert(10);*/
  /*      tree.insert(20);*/
  /*      tree.insert(30);*/
/**/

       /* Recursion recursion = new Recursion();
        System.out.println(recursion.factorialCa
       /* Tree tree = new Tree();*/
       /* tree.insert(7);*/
       /* tree.insert(4);*/
       /* tree.insert(9);*/
       /* tree.insert(1);*/
       /* tree.insert(6);*/
       /* tree.insert(8);*/
       /* tree.insert(10);*/
       /* var list = tree.getNodesAtDistance(2);
       /* for (var item: list)*/
       /*     System.out.println(item);*/
        //System.out.println(tree.isBinarySearch
     //   System.out.println(tree.isBinarySearch

       /* Tree tree2 = new Tree();*/
       /* tree2.insert(7);*/
       /* tree2.insert(4);*/
       /* tree2.insert(9);*/
       /* tree2.insert(1);*/
       /* tree2.insert(6);*/
       /* tree2.insert(8);*/
      //  tree2.insert(10);
        /*ystem.out.println(tree.equal(tree2));;
        //tree.traversPreOreder();
      // tree.traversInOreder();
      //  tree.traversPostOreder();
       // System.out.println(tree.find(11))


         */