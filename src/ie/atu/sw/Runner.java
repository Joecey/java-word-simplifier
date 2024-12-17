package ie.atu.sw;

/**
 * This program allows you to simplify text files using your own 50d word embeddings model
 * It has various features which are described in the provided README.md
 *
 * @author Joe Linogao
 * @version 1.0
 */
public class Runner {
    public static void main(String[] args) {
        System.out.println("***** The Blazingly Fast and Cutting Edge Word Simplifier *****");
        System.out.println("Created by Joe Linogao");

        CLIMenu textSimplifier = new CLIMenu();
        textSimplifier.start();
    }
}
