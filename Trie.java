package Tries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    private static int ALPHABET_SIZE = 26;
    private class Node{
        private char value;
        private HashMap<Character , Node> children = new HashMap<>();
        private boolean isEndOfWord;


        public Node(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        //** Better Abstraction
        public boolean hasChild(char ch ){
            return children.containsKey(ch);
        }

        public void addChild(char ch) {
            children.put(ch , new Node(ch));
        }

        public Node getChild(char ch){
            return children.get(ch);
        }

        //service to get all children of a node
        public Node[] getChildren(){
            return children.values().toArray(new Node[0]);
            //first convert values to array , and then we need array of node not objects , so converted
            //to tell we need simply on a node array

        }

        public boolean hasChildren(){
            return !children.isEmpty();
        }

        public void removeChild(char ch){
            children.remove(ch);
        }


    }

    //Every trie will should have a root
    private Node root = new Node(' ');
/*********************************************************************************************/
    public void insert(String word){
        var current = root ; // start by current var pointing to the root
        //next we're going to iterate over the word that we're inserting
        for (char ch : word.toCharArray()){
            // in each iteration we have to ask the current node do you have a child for the current character,
            //so first we have to calculate the index of it's child
            var index = ch - 'a';
            if (!current.hasChild(ch)){//if we don't have this child we gonna create it
                current.addChild(ch) ;}
            //then we set current to point to tha node
            current = current.getChild(ch);
        }
        // after we visit all char in the word
        current.isEndOfWord = true;
        //** The problem of using arrays is every time we will allocate 26 , by large amount of word it will wast space

    }

    /****************************************************************************************/
    public boolean contains(String word){
        if (word  == null)
            return false;

        var current = root;
        for (var ch : word.toCharArray() ){
            // now we should ask the current node do you have child for this node
            if (!current.hasChild(ch)) //  if it doesn't have child return false immediately
                return false ;
            // otherwise we set the current to point to this child
            current = current.getChild(ch);

        }
        //so once we visit all characters in this node , the pointer point to the last node
        return current.isEndOfWord;
    }

    /******************************************************************************************************/

    //Pre-order traversing the trie using recursion

    public void traverse(){
        traverse(root); // call the implementation method and give it the root node

    }

    public void traverse(Node root){
        // Pre-Order traversal : visit the root node first
        System.out.println(root.value);//simply print its value
        //now we gonna visit each child of the root , unlike BTS a node could have many children not 2
        // create service to get all children of a node
        for (var child : root.getChildren())
            traverse(child);


    }


    //Post-order travere
    public void PostTraverse(){
        PostTraverse(root); // call the implementation method and give it the root node

    }

    public void PostTraverse(Node root){

        //now we gonna visit each child of the root , unlike BTS a node could have many children not 2
        // create service to get all children of a node
        for (var child : root.getChildren())
            PostTraverse(child);


        // Post-Order traversal : visit the root node first
        System.out.println(root.value);//simply print its value


    }


/*****************************************************************************************************************************/

    //remove method using recursion , using Post-Order traversal
    public void remove(String word){
        if (word == null)
            return;
       remove(root , word , 0);
    }

    private void remove(Node root , String word , int index){
         //base condition to terminate the recursion , in this case when we were visiting the last letter of this word
          if (index == word.length()){
            //here we should remove end of the word marker
            root.isEndOfWord = false;         //not physically yet
            return;
          }
          //to know which character we're looking at
         var ch  = word.charAt(index);

          //to know which character Node we looking at is the child object
          var child  =  root.getChild(ch);

          if (child == null)
                return;//stop here

          //recursive call
          remove(child , word , index + 1);
          // after visiting the child now we are in the parent  , we have to if this child have any children
          if (!child.hasChildren() && !child.isEndOfWord)
              //now we can physically remove it from our tree
               root.removeChild(ch);


    }

/********************************************************************************************/
    // Auto Completion
    public List<String>  findWords(String prefix){
        List<String> words = new ArrayList<>();
        //we have to find the last Node for this prefix , create Method
       var lastNode  = findLastNodeOf(prefix);
       //Now we can kick off the recursion , we need another method to handel the recursive work

        findWords(lastNode , prefix , words);
        return words;
    }
    //recursive method
    private void findWords(Node root ,String prefix , List<String>  words /*for collecting the words*/){

       //check if root is null
        if (root == null)
            return;

        //here we are going to do pre-order traversal before we visit its children
        //first we should check to see if
       if (root.isEndOfWord)           //here you want to add this prefix to the list of words
           words.add(prefix);

       for (var child : root.getChildren())
           findWords(child , prefix + child.value ,words );

    }

    private Node findLastNodeOf(String prefix){
        //first check if prefix is null
        if(prefix == null)
            return null;

        //the algorithm here is to start from the last Node then we visit each Node of each char in this prefix
        var current = root;
        // now we loop over all the characters in the string
        for (var ch : prefix.toCharArray()) {
            //then we ask the current node of this child
            var child = current.getChild(ch);
            //if the child is null that means we don't have this prefix in our trie
            if (child == null)
                return null;
            //otherwise we set the current to this child
            current = child;
        }
        //finally at the end of this loop current will represent the last node with this prefix
        return current;
        }

    }





