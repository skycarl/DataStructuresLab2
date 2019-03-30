/*
  This program solves the Towers of Hanoi problem recursively and iteratively for a given n
  that is specified in the input file. It then outputs the results and statistics to an
  output file.

  @author Skyler Carlson
 * @version 1.1
 * @since 2019-03-13
 */

import java.io.*;
import java.util.Scanner;
//import java.util.Stack;

public class Lab2 {

    /**
     * This is the main driver method that runs the program.
     * @param args Expects two arguments: input filename (containing the value of n) and an output filename
     */

    public static void main(String[] args) throws StackOverflow, StackUnderflow {

        // Verify that input arguments are specified
        if (args.length != 2) {
            System.out.println("Error: insufficient input arguments. Enter input and output filenames.");
            System.exit(1);
        }

        // TODO add verification that input value is >0
        // TODO test weird input files

        // Define output filename for easier use throughout the program
        String outfile = args[1];

        // Import the file
        int maxProblemSize = importProblemSize(args[0]);

        // Define the towers
        char towerA = 'A';
        char towerB = 'B';
        char towerC = 'C';

        // Initialize and start the timer
        long startTime;
        long endTime;
        long runTime;

        /*
        // ******** Recursive solution ********
        // Loop through problems from 1 to size specified in input file
        for (int runProblemSize = 1; runProblemSize <= maxProblemSize; runProblemSize++) {

            // Start the timer
            startTime = System.currentTimeMillis();

            // Recursive solution
            moveRingsRecursive(runProblemSize, towerA, towerB, towerC, outfile);

            // Stop the timer and send to results
            endTime = System.currentTimeMillis();
            runTime = endTime - startTime;
            printStatsToFile(runProblemSize, runTime, "Recursive", outfile);
        }
        */
        moveRingsRecursive(7, towerA, towerB, towerC, outfile);
        System.out.println("\n\n*********");
        moveRingsIterative(7, outfile);
        /*
        // ******** Iterative solution ********
        for (int runProblemSize = 1; runProblemSize <= maxProblemSize; runProblemSize++) {

            // Start the timer
            startTime = System.currentTimeMillis();

            // Recursive solution
            moveRingsIterative(runProblemSize, outfile);

            // Stop the timer and send to results
            endTime = System.currentTimeMillis();
            runTime = endTime - startTime;
            printStatsToFile(runProblemSize, runTime, "Iterative", outfile);
        } */
    }


    /**
     * This method imports the problem size from the input file and returns it to main()
     * @param filename              The arguments of the program, passed from main().
     * @return problemSize      An integer denoting the size of the problem.
     */
    private static int importProblemSize(String filename) {



        // Import input file
        int problemSize = -1;

        // File name
        File file = new File(filename);

        try (Scanner inputScanner = new Scanner(file)) {
            problemSize = inputScanner.nextInt();

        } catch (IOException ioExc) {
            ioExc.toString();
        }

        return problemSize;

    }

    /**
     * This method accepts a value and an output file name and writes the value to the file.
     * @param problemSize       Integer representing the number of rings involved in the problem.
     * @param runtime           Double that is intended to denote the runtime of the function.
     * @param runType           String description of the function.
     * @param outputFile        String for the output file name.
     */

    private static void printStatsToFile(int problemSize, long runtime, String runType,
        String outputFile) {


        // Create FileWriter object
        FileWriter output;

        try {
            // Create FileWriter object in append mode
            output = new FileWriter(outputFile, true);
            output.write("\nNumber of rings: " + problemSize);
            output.write("\nSolution type: " + runType);
            output.write("\nRuntime: " + runtime + "ms");
            output.write("\n\n-----------------------------\n");

            output.close();
        }
        catch (IOException ioExc) {
            ioExc.toString();
        }
        catch (RuntimeException rtExc) {
            rtExc.toString();
        }

        // TODO remove this temp print statement
        System.out.print("\nNumber of rings: " + problemSize);
        System.out.print("\nSolution type: " + runType);
        System.out.print("\nRuntime: " + runtime + "ms");
        System.out.print("\n\n-----------------------------\n");
    }

    /**
     * This method prints the current move.
     * @param disk                  The disk being moved.
     * @param originTower           The tower from which the disk is being moved.
     * @param destinationTower      The tower to which the disk is being moved.
     * @param outputFile            The name of the output file.
     */
    private static void printMoveToFile(int disk, char originTower, char destinationTower,
        String outputFile) {

        // Create FileWriter object
        FileWriter output;

        try {
            // Create FileWriter object in append mode
            output = new FileWriter(outputFile, true);
            output.write("\nMove disk " + disk);
            output.write(" from tower " + originTower + " to tower " + destinationTower);

            // TODO remove this temp print statement
            System.out.print("\nMove disk " + disk);
            System.out.print(" from tower " + originTower + " to tower " + destinationTower);


            output.close();
        }
        catch (IOException ioExc) {
            ioExc.toString();
        }

    }

    /**
     * This recursive method performs the operations to move the rings.
     * @param remainingDisks        The number of disks remaining on the original tower.
     * @param originTower           The identifier of the origin tower.
     * @param auxiliaryTower        The identifier of the auxiliary tower.
     * @param destTower             The identifier or the destination tower.
     * @param outputFile            The file name of the output file.
     */
    private static void moveRingsRecursive(int remainingDisks, char originTower,
        char auxiliaryTower, char destTower, String outputFile) {

        // Base case if n = 1
        if (remainingDisks == 1) {
            printMoveToFile(remainingDisks, originTower, destTower, outputFile);
            return;
        }

        // Move n-1 disks to the auxiliary tower, using the destination tower as the auxiliary
        moveRingsRecursive(remainingDisks - 1, originTower, destTower, auxiliaryTower, outputFile);
        printMoveToFile(remainingDisks, originTower, destTower, outputFile);

        // Move the n-1 disks from the auxiliary tower to the destination tower, using the origin tower as the auxiliary
        moveRingsRecursive(remainingDisks - 1, auxiliaryTower, originTower, destTower, outputFile);
    }

    /**
     * This method performs the operations iteratively.
     * @param problemSize
     * @param outputFile
     */
    private static void moveRingsIterative(int problemSize, String outputFile) {

        // Create and initialize the towers
        Stack originTower = new Stack(problemSize);
        Stack auxiliaryTower = new Stack(problemSize);
        Stack destinationTower = new Stack(problemSize);
        int tempDisk;

        // Create and initialize character representations of the towers
        char originTowerChar = 'A';
        char auxTowerChar = 'B';
        char destinationTowerChar = 'C';

        // Load the originTower with values
        for (int i = problemSize; i > 0; i--) {
            try {
                originTower.push(i);
            }
            catch (StackOverflow so) {
                System.out.println(so.toString());
            }
        }

        // Calculate the number of moves in the problem
        int numMoves = (int) (Math.pow(2, problemSize) - 1);

        // Swap the towers, if the problem size is even
        if (problemSize % 2 == 0) {
            auxTowerChar = 'C';
            destinationTowerChar = 'B';
        }

        // Perform the iterative moves
        for (int j = 1; j <= numMoves; j++) {
            if (j % 3 == 0) {

                // Move from B to C
                try { // TODO remove this trc block

                    checkMoveIterative(auxiliaryTower, destinationTower, auxTowerChar, destinationTowerChar, outputFile);

                    //tempDisk = auxiliaryTower.pop();
                    //printMoveToFile(tempDisk, auxTowerChar, destinationTowerChar, outputFile );
                    //destinationTower.push(tempDisk);
                }
                catch (StackUnderflow su) {
                    System.out.println(su.toString());
                }
                catch (StackOverflow so) {
                    System.out.println(so.toString());
                }
            }
            else if (j % 3 == 1) {

                // Move from A to C
                try { // TODO remove this trc block

                    checkMoveIterative(originTower, destinationTower, originTowerChar, destinationTowerChar, outputFile);
                    
                    //tempDisk = originTower.pop();
                    //printMoveToFile(tempDisk, originTowerChar, destinationTowerChar, outputFile );
                    //destinationTower.push(tempDisk);
                }
                catch (StackUnderflow su) {
                    System.out.println(su.toString());
                }
                catch (StackOverflow so) {
                    System.out.println(so.toString());
                }
            }
            else if (j % 3 == 2) {

                // Move from A to B
                try { // TODO remove this trc block

                    checkMoveIterative(originTower, auxiliaryTower, originTowerChar, auxTowerChar, outputFile);



                    //tempDisk = originTower.pop();
                    //printMoveToFile(tempDisk, originTowerChar, auxTowerChar, outputFile );
                    //auxiliaryTower.push(tempDisk);
                }
                catch (StackUnderflow su) {
                    System.out.println(su.toString());
                }
                catch (StackOverflow so) {
                    System.out.println(so.toString());
                }
            }
        }
    }

    /**
     * This class finds which move between the two towers passed is valid. It then makes the valid move.
     * @param tower1        The tower from which the ring is being moved.
     * @param tower2        The tower to which the ring is being moved.
     * @param tower1char    The char representation of the source tower.
     * @param tower2char    The char representation of the destination tower.
     * @param outputFile    A string representing the name of the output file.
     */
    private static void checkMoveIterative(Stack tower1, Stack tower2, char tower1char, char tower2char, String outputFile) throws StackOverflow, StackUnderflow {
        int tempDisk;

        // If one of the towers is empty, move the ring to the empty tower
        if (tower1.isEmpty()) {
            tempDisk = tower2.pop();
            printMoveToFile(tempDisk, tower2char, tower1char, outputFile);
            tower1.push(tempDisk);
        }
        else if (tower2.isEmpty()){
            tempDisk = tower1.pop();
            printMoveToFile(tempDisk, tower1char, tower2char, outputFile);
            tower2.push(tempDisk);
        }

        // A larger disk cannot go on top of a smaller disk
        else if (tower1.peek() < tower2.peek()) {
            tempDisk = tower1.pop();
            printMoveToFile(tempDisk, tower1char, tower2char, outputFile);
            tower2.push(tempDisk);
        }
        else if (tower2.peek() < tower1.peek()) {
            tempDisk = tower2.pop();
            printMoveToFile(tempDisk, tower2char, tower1char, outputFile);
            tower1.push(tempDisk);
        }
    }
}