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
import java.util.Stack;

public class Lab2 {

    /**
     * This is the main driver method that runs the program.
     * @param args Expects two arguments: input filename (containing the value of n) and an output filename
     */

    public static void main(String[] args) {

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
        Tower originTower = new Tower(problemSize);
        Tower auxiliaryTower = new Tower(problemSize);
        Tower destinationTower = new Tower(problemSize);
        int tempDisk;

        // Load the originTower with values
        for (int i = problemSize; i > 0; i--) {
            try {
                originTower.push(i);
            }
            catch (StackOverflow so) {
                so.toString();
            }
        }

        // Calculate the number of moves in the problem
        int numMoves = (int) (Math.pow(2, problemSize) - 1);

        // Perform the initial move
        if (problemSize % 2 == 0) {
            try {
                destinationTower.push(originTower.pop());
                printMoveToFile(problemSize, 'A', 'C', outputFile);
            }
            catch (StackUnderflow su) {
                su.toString();
            }
            catch (StackOverflow so) {
                so.toString();
            }
        }

        // Perform the iterative moves
        for (int j = 1; j <= numMoves; j++) {
            if (j % 3 == 0) {

                // Move from B to C
                try { // TODO remove this trc block
                    tempDisk = auxiliaryTower.pop();
                    printMoveToFile(tempDisk, 'B', 'C', outputFile );
                    destinationTower.push(tempDisk);
                }
                catch (StackUnderflow su) {
                    su.toString();
                }
                catch (StackOverflow so) {
                    so.toString();
                }
            }
            else if (j % 3 == 1) {

                // Move from A to C
                try { // TODO remove this trc block
                    tempDisk = originTower.pop();
                    printMoveToFile(tempDisk, 'A', 'C', outputFile );
                    destinationTower.push(tempDisk);
                }
                catch (StackUnderflow su) {
                    su.toString();
                }
                catch (StackOverflow so) {
                    so.toString();
                }
            }
            else if (j % 3 == 2) {

                // Move from A to B
                try { // TODO remove this trc block
                    tempDisk = originTower.pop();
                    printMoveToFile(tempDisk, 'A', 'B', outputFile );
                    auxiliaryTower.push(tempDisk);
                }
                catch (StackUnderflow su) {
                    su.toString();
                }
                catch (StackOverflow so) {
                    so.toString();
                }
            }
        }
    }
}
