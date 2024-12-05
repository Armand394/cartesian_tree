import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author
 * Main class to test and save all performances of the Cartesian Tree
 */
public class Main {
    public static void main(String[] args) throws Exception {
        
        // Part 1: Insert the nodes in the order of their priority
        CartesianTree<Character, Integer> cartesianTree = new CartesianTree<>();

        cartesianTree.insertNode('H', 1);
        cartesianTree.insertNode('D', 2);
        cartesianTree.insertNode('B', 3);
        cartesianTree.insertNode('A', 5);
        cartesianTree.insertNode('E', 6);
        cartesianTree.insertNode('F', 7);
        cartesianTree.insertNode('C', 8);
        cartesianTree.insertNode('G', 9);
        cartesianTree.insertNode('I', 10);
        cartesianTree.insertNode('J', 12);

        System.out.println("Correct Tree (ordered insertion): ");
        cartesianTree.printTreePreOrderTraversal();
        System.out.println();

        // Part 2.1: Insert the nodes in a random order
        cartesianTree = new CartesianTree<>();

        cartesianTree.insertNode('A', 5);
        cartesianTree.insertNode('B', 3);
        cartesianTree.insertNode('C', 8);
        cartesianTree.insertNode('D', 2);
        cartesianTree.insertNode('E', 6);
        cartesianTree.insertNode('F', 7);
        cartesianTree.insertNode('G', 9);
        cartesianTree.insertNode('H', 1);
        cartesianTree.insertNode('I', 10);
        cartesianTree.insertNode('J', 12);

        System.out.println("Tree 1:");
        cartesianTree.printTreePreOrderTraversal();
        System.out.println();

        // Part 2.2: Insert the nodes in a random order
        cartesianTree = new CartesianTree<>();

        cartesianTree.insertNode('H', 1);
        cartesianTree.insertNode('G', 9);
        cartesianTree.insertNode('A', 5);
        cartesianTree.insertNode('B', 3);
        cartesianTree.insertNode('D', 2);
        cartesianTree.insertNode('F', 7);
        cartesianTree.insertNode('C', 8);
        cartesianTree.insertNode('J', 12);
        cartesianTree.insertNode('I', 10);
        cartesianTree.insertNode('E', 6);

        System.out.println("Tree 2:");
        cartesianTree.printTreePreOrderTraversal();
        System.out.println();
        
        // Part 2.3: Insert the nodes in a random order
        cartesianTree = new CartesianTree<>();

        cartesianTree.insertNode('E', 6);
        cartesianTree.insertNode('H', 1);
        cartesianTree.insertNode('B', 3);
        cartesianTree.insertNode('D', 2);
        cartesianTree.insertNode('C', 8);
        cartesianTree.insertNode('F', 7);
        cartesianTree.insertNode('G', 9);
        cartesianTree.insertNode('J', 12);
        cartesianTree.insertNode('A', 5);
        cartesianTree.insertNode('I', 10);

        System.out.println("Tree 3:");
        cartesianTree.printTreePreOrderTraversal();
        System.out.println();

        // Part 3.1: Delete a random node 
        cartesianTree.deleteNode('A', 5);
        System.out.println("Tree 3.1:");
        cartesianTree.printInOrder();
        System.out.println();

        // Part 3.2: Delete a random node 
        cartesianTree.deleteNode('J', 12);
        System.out.println("Tree 3.2:");
        cartesianTree.printInOrder();
        System.out.println();

        // Part 3.3: Delete a random node 
        cartesianTree.deleteNode('H', 1);
        System.out.println("Tree 3.3:");
        cartesianTree.printInOrder();
        System.out.println();
        cartesianTree.printTreePreOrderTraversal();


        // Part 4.1 random priority generator for tree with keys of characters (test before performance)
        List<Character> letterKeys = arrayRandomLetters();
        List<Integer> priorities = arrayRandomPriorities(letterKeys.size());

        CartesianTree<Character, Integer> randomizedCartesianTree = new CartesianTree<>();

        // Insertion
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < letterKeys.size(); i++){
            char currentChar = letterKeys.get(i);
            int currentPrio = priorities.get(i);
            randomizedCartesianTree.insertNode(currentChar, currentPrio);
        }
        long endTime = System.currentTimeMillis();

        // Elapsed time insertion
        double elapsedTimeInSeconds = (endTime - startTime) / 1000.0;
        String formattedElapsedTime = String.format("%.3f", elapsedTimeInSeconds);

        // Properties
        int height = randomizedCartesianTree.getHeight();
        boolean violatesProperty = randomizedCartesianTree.verifyCartiesanTree();
        
        // Suppresion
        startTime = System.currentTimeMillis();
        for(int i = 0; i < letterKeys.size(); i++){
            char currentChar = letterKeys.get(i);
            int currentPrio = priorities.get(i);
            randomizedCartesianTree.deleteNode(currentChar, currentPrio);
        }
        endTime = System.currentTimeMillis();

        // Elapsed time supression
        double elapsedTimeInSecondsSupression = (endTime - startTime) / 1000.0;
        String formattedElapsedTimeSupression = String.format("%.3f", elapsedTimeInSecondsSupression);

        // Print results
        System.out.println();
        System.out.println();
        System.out.println("End Solve Insertion: " + formattedElapsedTime);
        System.out.println("End Solve Supression: " + formattedElapsedTimeSupression);
        System.out.println("Cartesian tree does not violate properties: " + violatesProperty);
        System.out.println("Height of tree: " + height);


        // Part 4.2 random priority generator for tree with keys of integers (test before performance)
        List<Integer> integerKeys = integersKeyList(20);
        priorities = arrayRandomPriorities(integerKeys.size());

        CartesianTree<Integer, Integer> randomizedCartesianTree2 = new CartesianTree<>();

        // Insertion
        startTime = System.currentTimeMillis();
        for(int i = 0; i < integerKeys.size(); i++){
            int currentKey = integerKeys.get(i);
            int currentPrio = priorities.get(i);
            randomizedCartesianTree2.insertNode(currentKey, currentPrio);
        }
        endTime = System.currentTimeMillis();

        // Elapsed time insertion
        elapsedTimeInSeconds = (endTime - startTime) / 1000.0;
        formattedElapsedTime = String.format("%.3f", elapsedTimeInSeconds);

        // Properties tree
        height = randomizedCartesianTree2.getHeight();
        violatesProperty = randomizedCartesianTree2.verifyCartiesanTree();
        
        // Suppresion
        startTime = System.currentTimeMillis();
        for(int i = 0; i < integerKeys.size(); i++){
            int currentKey = integerKeys.get(i);
            int currentPrio = priorities.get(i);
            randomizedCartesianTree2.deleteNode(currentKey, currentPrio);
        }
        endTime = System.currentTimeMillis();

        // Elapsed time suppresion
        elapsedTimeInSecondsSupression = (endTime - startTime) / 1000.0;
        formattedElapsedTimeSupression = String.format("%.3f", elapsedTimeInSecondsSupression);

        // Print results
        System.out.println();
        System.out.println();
        System.out.println("End Solve Insertion: " + formattedElapsedTime);
        System.out.println("End Solve Supression: " + formattedElapsedTimeSupression);
        System.out.println("Cartesian tree does not violate properties: " + violatesProperty);
        System.out.println("Height of tree: " + height);

        // Part 5: Performance Analysis
        for(int nodes = 1000; nodes <= 10000; nodes+=1000){

            String outputFile = "cartesianTree_Performance_supression2_" + nodes + ".txt";

            Path outputPath = Paths.get("C:\\Users\\external\\Desktop\\M1S1\\COMPLEX\\Projet\\resultat\\resultat3\\" + outputFile);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
                
                for (int iteration = 0; iteration < 100; iteration++){
                    List<Integer> integerKeysPerformance = integersKeyList(nodes);
                    List<Long> prioritiesLong = arrayRandomPrioritiesLong(integerKeysPerformance.size());
                    
                    CartesianTree<Integer, Long> randomizedCartesianTreeTesting = new CartesianTree<>();
                    
                    // Insertion
                    startTime = System.currentTimeMillis();
                    for(int i = 0; i < integerKeysPerformance.size(); i++){
                        int currentKey = integerKeysPerformance.get(i);
                        long currentPrio = prioritiesLong.get(i);
                        randomizedCartesianTreeTesting.insertNode(currentKey, currentPrio);
                    }
                    endTime = System.currentTimeMillis();
                    
                    // Elapsed time insertion
                    double elapsedTimeInSecondsInsertion = (endTime - startTime) / 1000.0;
                    String formattedElapsedTimeInsertion = String.format("%.3f", elapsedTimeInSecondsInsertion);
                    
                    // Tree information
                    int heightTree = randomizedCartesianTreeTesting.getHeight();
                    int numberNodes = integerKeysPerformance.size();
                    
                    // Suppresion1
                    startTime = System.currentTimeMillis();
                    for(int i = 0; i < integerKeysPerformance.size(); i++){
                        int currentKey = integerKeysPerformance.get(i);
                        long currentPrio = prioritiesLong.get(i);
                        randomizedCartesianTreeTesting.deleteNode(currentKey, currentPrio);
                    }
                    endTime = System.currentTimeMillis();
                    
                    // Elapsed time Supression
                    elapsedTimeInSecondsSupression = (endTime - startTime) / 1000.0;
                    formattedElapsedTimeSupression = String.format("%.3f", elapsedTimeInSecondsSupression);

                    writeToFile(writer, numberNodes, heightTree, formattedElapsedTimeInsertion, formattedElapsedTimeSupression);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Generate list of random letters
     * @return list of characters
     */
    public static List<Character> arrayRandomLetters(){

        // Characters
        char[] alphabetL = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] alphabetU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        // Final list
        List<Character> resultList = new ArrayList<>(alphabetL.length + alphabetU.length);

        // Add all characters
        for (char lower: alphabetL){
            resultList.add(lower);
        }

        for (char upper: alphabetU){
            resultList.add(upper);
        }

        // Shuffle list
        Collections.shuffle(resultList, new Random(54));
        
        return resultList;

    }

    /**
     * Return list of randomly generated integer priorities with uniform distribution
     * @param size size of the list
     * @return List of generared priorities
     */
    public static List<Integer> arrayRandomPriorities(int size){
        // Initiate random number and list
        List<Integer> randomPriorities = new ArrayList<>(size);
        Random rd = new Random();  

        // Generate all random priorities
        for(int i = 0; i < size; i++){
            int priority = Math.round(100*size*size*rd.nextFloat());
            randomPriorities.add(priority);
        }

        return randomPriorities;
    }

    /**
     * Generate list of random priorities as long type (for large values)
     * @param size Size of the list
     * @return List of random Long priorities
     */
    public static List<Long> arrayRandomPrioritiesLong(int size){
        
        // Initiate random number and list
        List<Long> randomPriorities = new ArrayList<>(size);
        Random rd = new Random();  

        // Make size long number so it become a long multiplication
        long sizeLong = (long) size;

        // Generate all random priorities as long values
        for(int i = 0; i < size; i++){
            float valueUniform = rd.nextFloat();
            double priorityUnrounded = 100L*sizeLong*sizeLong*valueUniform;
            long priority = (long) priorityUnrounded;
            randomPriorities.add(priority);
        }

        return randomPriorities;
    }

    /**
     * Create list of keys as integers
     * @param size size of the list
     * @return Keys of the tree
     */
    public static List<Integer> integersKeyList(int size){
        List<Integer> numbersList = new ArrayList<>();

        for (int i = 0; i < size; i++){
            numbersList.add(i);
        }

        Collections.shuffle(numbersList, new Random(54));

        return numbersList;

    }

        /**
     * Write results to text file
     * @param writer File writer
     * @param numNodes Number of nodes used in instance
     * @param height height of the tree
     * @param formattedElapsedTimeInsert Total insertion time
     * @param formattedElapsedTimeDelete Total supression time
     * @throws IOException If output file cannot be found
     */
    public static void writeToFile(BufferedWriter writer, int numNodes, int height, String formattedElapsedTimeInsert, String formattedElapsedTimeDelete) throws IOException {
            writer.write(numNodes+ " , " + height + " , " + formattedElapsedTimeInsert + " , " + formattedElapsedTimeDelete);
            writer.newLine();
    }

}
