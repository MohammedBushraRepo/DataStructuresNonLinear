package BinaryTrees;

public class Recursion {
    //n!= n x (n-1)!
    //4! = 4 x 3!  called factorial
    //3!=3 x 2 x 1
    // Expressing a problem using smaller proplem

    public Recursion() {
    }


    // using iteration with loops
    public  int factorialCalc(int n){
        var factorial = 1;
        for (int i = n ; i > 1 ; i--)
            factorial *= i;
        return factorial;
    }
    //using recursion : recursion happens when a method call itself
    /*problem is we have cycle and it will continue  forever for example
    * f(3)
    *   3 x f(2)      => 3 x 2 = 6
    *          2 x f(1) => 2 x 1 = 2
    *           1 x (f0) => 1 x 1 = 1 #
    * Note : Java uses a Stack to manage Recursion */
    public int factorial(int n){
       //Base Condition : way to terminate  recursion
        if (n == 0)
            return 1;

        return n * factorial(n-1); // note that we are calling the method inside itself

    }
}
