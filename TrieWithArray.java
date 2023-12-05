package Tries;

public class TrieWithArray {
    private static int ALPHABET_SIZE = 26;
    private class Node{
        private char value;
        private TrieWithArray.Node[] children = new TrieWithArray.Node[ALPHABET_SIZE];
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
    }

    //Every trie will should have a root
    private TrieWithArray.Node root = new TrieWithArray.Node(' ');

    public void insert(String word){
        var current = root ; // start by current var pointing to the root
        //next we're going to iterate over the word that we're inserting
        for (char ch : word.toCharArray()){
            // in each iteration we have to ask the current node do you have a child for the current character,
            //so first we have to calculate the index of it's child
            var index = ch - 'a';
            if (current.children[index] == null){//if we don't have this child we gonna create it
                current.children[index] = new TrieWithArray.Node(ch);}
            //then we set current to point to tha node
            current = current.children[index];
        }
        // after we visit all char in the word
        current.isEndOfWord = true;
        //** The problem of using arrays is every time we will allocate 26 , by large amount of word it will wast space

    }

}
