/**
 1. Demonstrate class declaration.
 2. Name of class = name of file.java
 3. main routine structure
    a. not required in every class
    b. there are times when it might be private or protected
 4. No need for argc argv, can get the number of arguments from args,
    args.length
    a. Arrays like c++
 5. Classic output: System.out.print, or, System.out.println
    System is a pre-defined "package", packages to be described later.
 6. Class is like a combination of .h file and .cpp file
 7. Everything goes in a class
 */
public class HelloWorld {
    public static void main(String[] args) {
//        String s = " 444 \n";
  //      s = s.trim();
        System.out.println("Hi There");
    }
}
