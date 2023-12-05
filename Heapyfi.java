package Heaps;

public class Heapyfi {
    public static void heapify(int[] array) {
        // static method because we don't need to create a new Heap object
        // want to loop on each item of this array and for each item we want to make sure
        // that item on the right position , otherwise we have to bubble it down
        var lastParentIndex = array.length / 2 - 1; // get the index of the last parent
        for (var i = lastParentIndex; i >= 0; i--)
            // for (int i = 0 ; i > arr.length ; i++)// in each iteratoin we want to heapyfi the item i the given index
            heapify(array, i);
    }

    private static void heapify(int[] arr, int index) {// we want to make sure that the item of this index
        // in the right position otherwise we're going to move it down
        //here we gonna check if this item is smaller than his children , if it smaller we gonna swapp it the larger child
        var largeIndex = index; //  we're assuming that the item of this index is largest of the three values

        var leftIndex = index * 2 + 1; //left child of an array
        if (leftIndex < arr.length && // to make sure that value not falls outside the range of array
                arr[leftIndex] > arr[largeIndex])
            largeIndex = leftIndex;


        var rightIndex = index * 2 + 2; //right child of an array
        if (rightIndex < arr.length && // to make sure that value not falls outside the range of array
                arr[rightIndex] > arr[largeIndex])
            largeIndex = rightIndex;

        // if item is bigger than both its children

        if (index == largeIndex)//base condition for recursion , because at some point that item will be greater than its children
            return;

        // make the swap
        swap(arr, index, largeIndex);

        //we are going recursively repeat this step
        heapify(arr, largeIndex);


    }

    //swap method
    private static void swap(int[] arr, int first, int second) {
        var temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    //find the kth Largest value in the heap
    /*first you to use max heap , because in the max heap the root always hold the max value
    * so if you want to find the third max value , you should remove the first and second large values
    * and inspect the root node , what will have on the root is third max value */


    public static int getKthLargest(int[] array , int k){
        if (k < 1 || k > array.length)
            throw  new IllegalArgumentException();


        var heap = new Heap();//create heap object
        //insert all items in this array to the heap
        for (var number : array)
            heap.insert(number);

        //now we need to execute the delete operation k - 1 times , means if we need 3 largest , execute delete twice
        for (int i = 0 ; i < k - 1 ; i++)
            heap.remove();

        //finally we should return the root node , which is the maximum value (create max method)
        return heap.max();

    }


}







