package BinaryTrees;

import java.util.ArrayList;

/*create tree(root) class with embedded private Node class(value , left-child , right-child)
* insert(value)
* find(value): boolean*/
public class Tree {
    private class Node{
        private int value;
        private Node leftChild;
        private Node rightChild;

        public Node(int value){
            this.value = value;
        }

        @Override
        public String toString(){
            return "Node" + value;
        }
    }

//after we create Node class now we can declare root of our tree

    private Node root;

    //implement insert operation

    public void insert(int value){
        var node = new Node(value);
        //where the tree is empty, set the root to new node
        if (root == null) {
            root = node;
           return;
        }

        //other senario
        var current = root;
        // if tree has values we iterate over Nodes in this tree and find a parent for this node
        while(true){//we make it infinite while loop , until we find a parent
            //here we should compare the value that we want to insert with value on current node
            if (value < current.value) {
                if (current.leftChild == null){
                    //if null that mean is this node is parent for the new node that we want insert
                    current.leftChild = node;
                    break;
                }

                current = current.leftChild;
            }
            else {
                if (current.rightChild == null) {
                    //if null that mean is this node is parent for the new node that we want insert
                    current.rightChild = node;
                    break;
                }

                current = current.rightChild;
            }
        }
    }

    public boolean find(int value){
        //like insert method we need to start from the root Node traversed the tree until we find the target Node
        var current = root;
        while (current != null){//if the tree is empty current will be null loop will not work, it's not going to be an infinite loop because its possible we don't have the
        // now we need to compare the given value with value of current Node , if bigger it then right ,if smaller then left
            if (value < current.value)
                current = current.leftChild;
            else if (value > current.value)
                current = current.rightChild;
            else // if it match the input then we find the target node
             return true;
        }
        return false;
    }

    //Because the root field is private we need to overload the method
    public void traversPreOreder(){
        traversPreOreder(root);
    }
    private void traversPreOreder(Node root){
        //Base Condition
        if (root == null)
            return;

        // first we visit the root , print its value on the console
        System.out.println(root.value);
        //left
        traversPreOreder(root.leftChild);
        //right
        traversPreOreder(root.rightChild);


    }

  //InOreder
    //Because the root field is private we need to overload the method
    public void traversInOreder(){
        traversInOreder(root);
    }
    private void traversInOreder(Node root){
        //Base Condition
        if (root == null)
            return;

        //left
        traversInOreder(root.leftChild);

        //root
        System.out.println(root.value);

        //right
        traversInOreder(root.rightChild);

    }


    //PostOrder
    public void traversPostOreder(){
        traversPostOreder(root);
    }
    private void traversPostOreder(Node root){
        //Base Condition
        if (root == null)
            return;

        //left
        traversPostOreder(root.leftChild);
        //right
        traversPostOreder(root.rightChild);
        //root
        System.out.println(root.value);

    }


    //know the height of tree
    public int height(){
        return height(root);
    }
    private int height(Node root){
        //if the tree is empty
        if (root == null)
            return -1;

        // base condition : when both leaf are null , here using PostOrder
        if (isLeafBaseCondition(root))
            return root.value;
         // calculate the height using recursion
        return 1 + Math.max( // here we start recursion
                height(root.leftChild),
                height(root.rightChild)
        );
    }

//calculate the minimum value on the tree

    /*find the minimum value of the tree
    * first we should find minimum values in the left and right sub-trees
    * then we compare those minimum values with value in the root , to find the min in the main pinary tree
    * in this case we use post order traverse Starting from the leaf
    * */



    public int min(){
        // check if tree is empty
        if(root == null)
            throw  new IllegalStateException();

       /* return min(root);*/ //Binary Tree
        // assuming  we are dealing with Binary Search tree
        //we Should find the left most leaf == min value
        var current = root;
        var last = current;
        // time complexity is O(log n) because in each iteration be divide the values by half
        while (current != null){
            last = current;//reference last most child
            current = current.leftChild;
            // to keep going to left subtree , at some point current is gonna be null , so we need another reference to the left most child

        }
        return last.value;
    }


    //time complexity is 0(n)
    private int min(Node root){

        /*//if the tree is empty
        if (root == null)
            return -1;*/

        // base condition : when both leaf are null , here using PostOrder
        if (isLeafBaseCondition(root))
            return root.value;

        //find the minimum in the left subtree
        var left = min(root.leftChild);
        //find the minimum in the right subtree
        var right = min(root.rightChild);
        //then we compare those values with the value on the root node
        //we call Math.Min two times first compare right with left , second compare the return with root
        return Math.min(Math.min(right,left) , root.value);
    }

    //Base Condition for terminate recursion as A method
    private static boolean isLeafBaseCondition(Node root) {
        return root.leftChild == null && root.rightChild == null;
    }


    //Equality Check


    public boolean equal(Tree other){
       if (other == null)
           return false;
        return equal(root , other.root);
    }

    private boolean equal(Node first , Node second){
        // if the two nodes are empty return true
        if(first == null && second == null)
          return true;
        // if both are not empty
        if (first != null && second != null)
            return first.value == second.value
                        &&equal(first.leftChild , second.leftChild)
                        &&equal(first.rightChild , second.rightChild);

        return false;
    }

    /*another exercise they give you a binary tree and the wanted you to validate this binary tree
    * or binary search tree , the difference that in binary search all value in the left should be smaller than the root
    * and on the right should be bigger than root Node
    * we have to traverse the tree and for each node we check to see if the value of this node in the right range
    *
    * */


    public boolean isBinarySearchTree(){
        return isBinarySearchTree(root , Integer.MIN_VALUE  , Integer.MAX_VALUE);
        //because we don't have any constraints in the value of root node
    }

    private boolean isBinarySearchTree(Node root, int min , int max){
        // if root is null , so its tree and binary search tree
            if (root == null)
            return true;


        //validate the value on the root node to make sure that value in the right range
             if (root.value < min || root.value > max )//out of range
            return false;


        //if we get to this point that's means our value in the right range , so we can look at right and left subtrees



        return
                    isBinarySearchTree(root.leftChild , min, root.value - 1)
                            //if the value we had in root is 20 the left child should be a maximum of 19
                    && isBinarySearchTree(root.rightChild,root.value + 1 , max);
                            //if the value we had in root is 20 the right child on the right should be at least 21


    }

    //temp method to swap left and right nodes

    public void swapRoot(){
        // create temp var and set it to the left child
        var temp = root.leftChild;
        root.leftChild=root.rightChild;
        root.rightChild = temp;
        //mess up the nodes orders
    }



    /* print a k distance of certain node
    * using recursion
    * distance in root node = 0
    * if distance = 3 you should always go down and decrease distance */
    public ArrayList<Integer> getNodesAtDistance(int distance){
      var list = new ArrayList<Integer>();
        // give it the root of the tree to start , and the distance argument
        getNodesAtDistance(root , distance , list);
         return list;

    }
    private void getNodesAtDistance(Node root , int distance , ArrayList<Integer> list){
       if (root == null)
           return;
        // first the easiest case when distance =0,also considered a base condition to terminate the recursion
        if (distance == 0){
            list.add(root.value);
           // System.out.println(root.value);
            return;
        }
        //otherwise , if you get to this point that's mean distance is greater than zero
        //so here we gonna recursively traversed the tree , going to look on the left and right children
        //and as we go down we decrement the distance by one

        getNodesAtDistance(root.leftChild , distance - 1 , list);
        getNodesAtDistance(root.rightChild , distance - 1 , list);
    }

    /*
    * trees algorithms classified into two categories
    * 1-depth first => all above methods
    * 2- breath first also called level order traverse
    * int  level order traverse we go level by level
    * to implement first we need to know how many levels do we have === height of our tree
    * once we know the height we use foreach loop to print nodes at each level
    *
    *  */

    public void traversLevelOrder(){
        // here we need a for loop
        for (int i =0 ; i>height() ; ++i) {//use height method to determine the tree height
            // at each iteration we are going to call get Node at distance ,to find the Node at given distance
            var list = getNodesAtDistance(i);
            for (var value : list)
                System.out.println(value);
        }
    }



    /*recap of trees
    * A node can have up to 2 children
    * A Node without A children is called a leaf
    * the height of a leaf Node is zero
    * As we go up on the tree the height increases
    * the height of the tree is equal to the height of its root Node , which is long path to a leaf
    * the depth of root Node is Zero
    * As we go down on the tree the depth increases
    * the depth of Node is most accurately , the Number of edges from the root to that Node
    * Binary Search tree is Special type of binary trees , in which all the values in the left subtree of the node is smaller
    and the values of the right subtree are greater than the value if the Node itself
    *
    * we have two different categories algorithms for traversing the trees
    * 1- Breadth first:- Level Order Traversal
    *
    * 2- Depth first :- we have three algorithms in each one we visit the root in different levels
    * a- Pre-Order = Root , Left , Right
    * b- In-Order = Left , Root , Right
    * c- Post-Order = Left , Right , Root */



}
