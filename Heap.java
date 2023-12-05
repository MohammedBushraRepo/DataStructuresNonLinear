package Heaps;

public class Heap {
    private int [] items = new int[10]; // to store values
    private int size ; // to keep track of items in our heap



    public void insert(int value){
        //as we insert items , our array may gets full
        if (isFull())
            throw  new IllegalStateException();

        // store this value in the next available solt
        items[size++] = value;
        // also increment the size filed
        /*size++;*/
        bubbleUp();//call it here

    }

    //remove method
    public  int  remove(){// we don't  need parameters here because we always remove the root node , which is the larg value

        //if the heap is empty
        if (isEmpty())
            throw new IllegalStateException();

        //set what we have on the root Node
        var root = items[0];

        // reset the root , move the value of last node to the root node
        items[0] = items[size - 1];
        size--;
        bubbleDown();

        return root;

    }
    //bubble down method
    private  void bubbleDown(){

        //once we remove this item we should apply the bubble down operation
        //so if item(root) < children we keep bubbling down until get the right position
        var index =  0;// we set it on variable because it keeps changing by each iteration

        // we need separate methods to calculate the left and right children
        while (index<= size &&!isValidParent(index))//if it is not valid
        {
            // now we have to swap it with one of its children , the Larger one , to determine
            var largerChildIndex  = largerChildIndex(index);

            //now after we had the larger child , we will swap it
            swap(index , largerChildIndex);
            // finally reset the index to the larger child index
            index = largerChildIndex;


        }


    }

    //heap is empty
    public boolean isEmpty(){
        return size == 0;
    }

    //get larger child index
    private int largerChildIndex(int index){
        //if it has not left child , means have not children
        if (!hasLeftChild(index))
            return index;
        //if it has not right child  , that means the left child is the grater one
        if (!hasRightChild(index))
            return leftChildIndex(index);
        return   (leftChild(index) > rightChild(index)) ? // if left > right //then
                leftChildIndex(index): //(:) == else
                rightChildIndex(index);//otherwise
    }
    // to determine if the node has a left child
    private boolean hasLeftChild(int index){ // node will have left or right child
        // , if the index of one of them if its value is less than or equal to the number of items on the heap
        return leftChildIndex(index)<= size;
    }

    private boolean hasRightChild(int index){ // node will have left or right child
        // , if the index of one of them if its value is less than or equal to the number of items on the heap
        return rightChildIndex(index)<= size;
    }

    // is valid parent
    // A node is valid if it is grater than or equal to both its children
    private boolean isValidParent(int index){
        //if it doesn't have a left child , this node is valid
        if (!hasLeftChild(index))
            return true;
        if (!hasRightChild(index))
            return items[index] >= leftChild(index);

        //if we get to this point thats mean , the node has 2 child

        return  items[index] >= leftChild(index) &&
                items[index] >= rightChild(index);
    }

    //left child value
    private int leftChild(int index){
        return items[leftChildIndex(index)];
    }

    //right child value
    private int rightChild(int index){
        return items[rightChildIndex(index)];
    }


    //calculate left child
      private int leftChildIndex(int index){
        return index * 2 + 1;
      }
    //calculate right child
    private int rightChildIndex(int index){
        return index * 2 + 2;
    }

    //as we insert items , our array may gets full
    public boolean isFull(){
        return size == items.length;
    }


    //bubble up algorithm
    private void bubbleUp(){
        //implement the bubble up operation , newItem > parent should bubble until right postition
        //to but the index of this item
        var index = size - 1 ;// because we increased the size after adding
        //var parentIndex = (index - 1 )/ 2 ;
        // as we bubble up a value in this tree , the index we be decreased may be in some point become negative , we need > 0 condition
        // and will go out of the array , and in parent method will zero divide , so index should always be a positive number
        while (index > 0 && items[index] > items[parent(index)] ) { // in the second argument we're calculating the index dynamically
            //as long as the items in this index is greater than its parent
            // we need to swap these items , so create swap method
            swap(index, parent(index));
            //before the next iteration we need to set the index , because we had some iteration
            // the index should point to its parent node
            index = parent(index); //we have to recalculate the index , because it will change after swapping
        }

    }


    //calculate item index
    private int parent(int index){
        return (index - 1 )/ 2;
    }

    // swap items
    private void swap(int first , int second){
        // how to swap 2 variable , use temp var
        var temp = items[first];//copy the item in this temp variable
        items[first] = items[second]; //copy the second item into the first
        items[second] = temp;//then what ever we have in this temp variable we copied the second item
    }

    //get the root node
    public int max (){

        if (isEmpty())
            throw new IllegalStateException();
        return items[0];
    }



}
