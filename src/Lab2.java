/*
  This program solves the Towers of Hanoi problem recursively and iteratively for a given n
  that is specified in the input file. It then outputs the results and statistics to an
  output file.

  @author Skyler Carlson
 * @version 1.3
 * @since 2019-03-13
 */

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

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

        // Define output filename for easier use throughout the program
        String outfile = args[1];

        // Delete the previous file
        deletePreviousFile(outfile);

        int maxProblemSize = 0;

        // Import the input size file
        try {
            maxProblemSize = importProblemSize(args[0]);
        }
        catch (InputMismatchException mismatch) {
            System.out.println("Invalid input for problem size. Input must be an integer. Try again.");
            System.exit(1);
        }

        // Ensure user has entered positive integer
        if (maxProblemSize < 1) {
            System.out.println("Invalid input for problem size. Input must be greater than 0. Try again.");
            System.exit(1);
        }

        // Define the towers
        char towerA = 'A';
        char towerB = 'B';
        char towerC = 'C';

        // Initialize and start the timer
        long startTime;
        long endTime;
        long runTime;

        // Declare arrays to store run times for easier printing at the end
        long[] recursiveRunTimes = new long[maxProblemSize];
        long[] iterativeRunTimes = new long[maxProblemSize];


        // ******** Recursive solution ********
        // Loop through problems from 1 to size specified in input file
        for (int runProblemSize = 1; runProblemSize <= maxProblemSize; runProblemSize++) {

            // Start the timer
            startTime = System.nanoTime();

            // Recursive solution
            moveRingsRecursive(runProblemSize, towerA, towerB, towerC, outfile);

            // Stop the timer and send to results
            endTime = System.nanoTime();
            runTime = endTime - startTime;
            printStatsToFile(runProblemSize, runTime, "Recursive", outfile);
            recursiveRunTimes[runProblemSize-1] = runTime;
        }

        // ******** Iterative solution ********
        for (int runProblemSize = 1; runProblemSize <= maxProblemSize; runProblemSize++) {

            // Start the timer
            startTime = System.nanoTime();

            // Recursive solution
            moveRingsIterative(runProblemSize, outfile);

            // Stop the timer and send to results
            endTime = System.nanoTime();
            runTime = endTime - startTime;
            printStatsToFile(runProblemSize, runTime, "Iterative", outfile);
            iterativeRunTimes[runProblemSize-1] = runTime;
        }

        // Print the summary stats to file
        printSummaryStats(outfile, maxProblemSize, recursiveRunTimes, iterativeRunTimes);

        // Print message that program has completed
        System.out.println("Program completed for n = " + maxProblemSize);
    }


    /**
     * This method prints summary statistics to the output file.
     * @param outfile               The name of the output file.
     * @param maxProblemSize        The maximum number of rings tested.
     * @param recursiveRunTimes     An array of the recursive run times for all n values, in nanoseconds.
     * @param iterativeRunTimes     An array of the iterative run times for all n values, in nanoseconds.
     */
    private static void printSummaryStats(String outfile, int maxProblemSize,
        long[] recursiveRunTimes, long[] iterativeRunTimes) {

        // Create FileWriter object to print summary statistics to file
        FileWriter output;
        String tempOutputString;
        String headerString;

        try {
            // Create FileWriter object in append mode
            output = new FileWriter(outfile, true);

            // Print the headers
            output.write("\n\n-------------Summary statistics-------------\n");
            headerString = String.format("\n%7s%19s%19s", "# rings", "Recursive (ns)", "Iterative (ns)");
            output.write(headerString);

            // Loop through the runtime arrays
            for (int k = 0; k < maxProblemSize; k++) {

                // Format output strings
                tempOutputString = String.format("\n%7d%19d%19d", k+1, recursiveRunTimes[k], iterativeRunTimes[k]);

                // Print the data to the table
                output.write(tempOutputString);
            }

            output.close();
        }
        catch (IOException ioExc) {
            System.out.println("Error writing to file " + ioExc.getMessage() + ". Program exiting.");
            System.exit(1);
        }
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
            System.out.println("Error reading file " + ioExc.getMessage() + ". Program exiting.");
            System.exit(1);
        }

        return problemSize;

    }

    /**
     * This method accepts a value and an output file name and writes the value to the file.
     * @param problemSize       Integer representing the max number of rings involved in the problem.
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
            output.write("\nRuntime: " + runtime + "ns");
            output.write("\n\n--------------------------------------------\n");
            output.close();
        }
        catch (IOException ioExc) {
            System.out.println("Error writing to file " + ioExc.getMessage() + ". Program exiting.");
        }
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
     * @param destinationTower             The identifier or the destination tower.
     * @param outputFile            The file name of the output file.
     */
    private static void moveRingsRecursive(int remainingDisks, char originTower,
        char auxiliaryTower, char destinationTower, String outputFile) {

        // Base case if n = 1
        if (remainingDisks == 1) {
            printMoveToFile(remainingDisks, originTower, destinationTower, outputFile);
            return;
        }

        // Move n-1 disks to the auxiliary tower, using the destination tower as the auxiliary
        moveRingsRecursive(remainingDisks - 1, originTower, destinationTower, auxiliaryTower, outputFile);
        printMoveToFile(remainingDisks, originTower, destinationTower, outputFile);

        // Move the n-1 disks from the auxiliary tower to the destination tower, using the origin tower as the auxiliary
        moveRingsRecursive(remainingDisks - 1, auxiliaryTower, originTower, destinationTower, outputFile);
    }

    /**
     * This method performs the operations iteratively.
     * @param problemSize           Integer representing the max number of rings involved in the problem.
     * @param outputFile            The file name of the output file.
     */
    private static void moveRingsIterative(int problemSize, String outputFile) {

        // Create and initialize the towers
        Stack originTower = new Stack(problemSize);
        Stack auxiliaryTower = new Stack(problemSize);
        Stack destinationTower = new Stack(problemSize);
        //int tempDisk;

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
        long numMoves = (long) (Math.pow(2, problemSize) - 1);

        // Swap the towers, if the problem size is even
        if (problemSize % 2 == 0) {
            auxTowerChar = 'C';
            destinationTowerChar = 'B';
        }

        // Perform the iterative moves
        for (long j = 1; j <= numMoves; j++) {
            if (j % 3 == 0) {

                // Move from B to C
                try { // TODO remove this trc block?
                    checkMoveIterative(auxiliaryTower, destinationTower, auxTowerChar, destinationTowerChar, outputFile);
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
                try { // TODO remove this trc block?
                    checkMoveIterative(originTower, destinationTower, originTowerChar, destinationTowerChar, outputFile);
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
                try { // TODO remove this trc block?
                    checkMoveIterative(originTower, auxiliaryTower, originTowerChar, auxTowerChar, outputFile);
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

    /**
     * This method deletes the previous file, if one exists of the same name.
     *
     * @param filename         The name of the output file, specified in runtime parameters
     */
    private static void deletePreviousFile(String filename) {
        File oldFile = new File(filename);
        oldFile.delete();
        System.out.println("Previous file \'" + filename + "\' deleted.\n");
    }

}