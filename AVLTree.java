package AVLTrees;

public class AVLTree {
    private class AVLNode{
        private int height; // introduced later
        private int value;
        private AVLNode leftChild;
        private AVLNode rightChild;


        // Constructor
        public AVLNode(int value){
            this.value = value;
        }

        //We're going to Override toString Method because we're going to use the debugger

        @Override
        public String toString(){
            return "Valur " + this.value;
        }


    }

    //every tree Needs reference to it's root Node
    private AVLNode root;

    public void insert(int value){
        /*with this recursive implementation we go down the tree until we find right place
        to insert the new node
        * once we're done we gonna recursively go back up
        *on the way backup we will update the height of every Node Until we get to the root Node */


        // set the root to the object that return from the insert method
         root = insert(root , value);
    }

    private AVLNode insert(AVLNode root , int value){
        //first scenario if the tree is empty
        if (root == null)
            return new AVLNode(value);

        //if the tree is not empty we should either go to the left or right , that depends on the inserted value
            if (value < root.value)
               root.leftChild =  insert(root.leftChild , value); // recursive call , set to root object to prevent returning null
            else
                root.rightChild = insert(root.rightChild , value);// recursive call , set to root object to prevent returning null
// what if the child is null , we made helper method to check and get the height below
        /*root.height = Math.max(height(root.leftChild) ,
                height(root.rightChild)) + 1;*/
        setHeight(root);

         //after we had the  height of each Node , we have to check if it unbalanced
        //so after we set the height we need to compare the height of the left and right subtrees
        //the difference should be less than or equal to 1
        // we use balance factor bf = h(L) - h(r)
        // > 1 => left heavy
        // < -1 => right heavy




      root =   balance(root);



        return root;


    }

    //Rotations
    private AVLNode rotateLeft(AVLNode node){
        //we need a refernce to the right child of root node and call new root
        var newRoot = root.rightChild;

        //Perform the rotations
        root.rightChild = newRoot.leftChild ;
        newRoot.leftChild = root;

        // now we need to reset the height of these 2 nodes , declare helper method to set height of Node
     setHeight(root);
     setHeight(newRoot);

     return newRoot;

    }

    private AVLNode rotateRight(AVLNode node){
        //we need a refernce to the left child of root node and call new root
        var newRoot = root.leftChild;

        //Perform the rotations
        root.leftChild = newRoot.rightChild ;
        newRoot.rightChild = root;


        // recalaculate the heights
        setHeight(root);
        setHeight(newRoot);


        return newRoot;



    }


//helper Method to set the height of a node
    private void setHeight(AVLNode node){
        node.height = Math.max(
                height(node.leftChild) ,
                height(node.rightChild)
        ) + 1 ;

    }

    private AVLNode balance(AVLNode root){
        if (isLeftHeavy(root) ) {
            if (balanceFactor(root.leftChild) < 0)
              root.leftChild =   rotateLeft(root.leftChild);
          return   rotateRight(root);
               /* System.out.println("left rotate " + root.leftChild.value);
            System.out.println("right rotate" + root.value);*/
        }
        else if (isRightHeavy(root)){
            if (balanceFactor(root.rightChild) > 0)
            root.rightChild =    rotateRight(root.rightChild);
          return   rotateLeft(root);
                /*System.out.println("right rotate " + root.rightChild.value);
            System.out.println("left rotate" + root.value);*/
        }

        return root;


    }

    //helper method to check if left is heavy
    private boolean isLeftHeavy(AVLNode node){
        return balanceFactor(node) > 1 ;
        //return height(node.leftChild) - height(node.rightChild) > 1;
    }

    //helper method to check if right is heavy
    private boolean isRightHeavy(AVLNode node){
       return balanceFactor(node) < -1;
        //return height(node.leftChild) - height(node.rightChild) < -1;
    }
    //refactor more and create a balance factor method
    private int balanceFactor(AVLNode node){
        //check if node null return 0 because empty tree is balanced
        return (node == null) ? 0 : height(node.leftChild) - height(node.rightChild);
    }






    // helper method for getting the height of  a node

    private int height(AVLNode node){
        if (node == null)
            return -1; // because the height of empty tree is -1
        return node.height;

    }
}
/*Notes:-
As we Know all operations in trees run in logarithmic time but this happens only if the tree is balanced
in balance tree the difference between the height of the left and right subtrees of every Node should be less than or
equal to 1 => height(left) - height(right) <= 1
*How Our trees become in balanced ? this happens when insert sorted values in ASC or DESC Order

* the Solution is : Self Balanced Trees like AVL Trees(Adelson-Velsky and Landis)
* they are special type Of BSTs, but they have self-balancing properties so Every time we insert or remove a value
the tree rebalanced itself


* Rotations
Avl trees use automatically rebalanced them self's  by insuring (height(left) - height(right) <= 1)
so the use Rotations to do that , we have 4 types of rotations :
1- Left (LL) , 2-Right(RR) ,3-Left-Right(LR) , 4-Right-Left(RL)


explain rotations
1- scenario 1
10
   20          leftRotate(10) =>        20
     30                              10    30


2- scenario 2
10                               10
   30        rightRotate(30) =>    20       leftRotate(10) =>           20
20                                   30                              10   30

* so in order to make the required rotation we need to Know the shape of the tree

how ? using the balance factor
* if the root is right heavy node like scenario 1 , we look at balance factor of its right child
in this case (20) on the left height is 0 on the right is 1 so 0 - 1 = -1
so if balanceFactor(root.right) < 0 you need to do leftRotate

*in scenario 2 in this case the node is (30) on the left height is 1 on the right is 0 so 1 - 0 = 1
so if balanceFactor(root.right) > 0 you need to do rightRotate with leftRotate

 var balanceFactor = balanceFactor(root);
        if (isLeftHeavy(root) )
            System.out.println(root.value + " is left heavy");
        else if (isRightHeavy(root))
            System.out.println(root.value + " is right heavy");


* implement rotations
1- scenario 1
10   => Root
   20 => newRoot
15     30

 here we have rightscew tree, and we want to perform a left rotate on the 10 , after rotation 20 is gonna be our
 new root
 so essentially newRoot = root.right after rotation  , but before that we going to set
 Root.right = newRoot.left
 newRoot.left  =  root
 after execution tree will look like
        20
    15      30
        10
 * Note : after changing the height of root and the new root will change , so we need
 to recalculate the height as we go
 2- as part of making the rotation the root is gonna be change so you have to change the return type
 to AVLNode.
* */
