///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           12z, H12CustomApp
// Course:          CS 200, Spring 2024
//
// Author:          Winston Chan
// Email:           wchan35@wisc.edu
// Lecturer's Name: Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// Utilized elements from 11z, Addition using files.
//
///////////////////////////////// REFLECTION ///////////////////////////////////
//
// 1. Describe the problem you wrote the program to solve:
//    The program finds the average of all the integers within the file.
// 2. Why did you choose the method header for the read file method (e.g., return type,
//    parameters, throws clause)?  The return type returns a double, since it needs to be
//    assigned into the output file. The throws clause catches whether if the file exists
//    or not.
// 3. Why did you choose the method header for the write file method (e.g., return type,
//    parameters, throws clause)?  This return type returns boolean since the output contents
//    are stored within the file. Therefore, there is no need to pass on the output to another
//    file to be read.
// 4. What are the biggest challenges you encountered: Managing all the exceptions was the most
//    difficult part of the project.
// 3. What did you learn from this assignment: How to read files, and to make calculations with the
//    data within them. I had also learned to output the proper data into an outputFile as well.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This program takes the integers from a valid file and finds the average. The average of
 * all the integers are then outputted into a specified output file.
 *
 * @author Winston Chan
 */
public class H12CustomApp {

    /**
     * This method reads the file data, and calculates the average of the integers.
     *
     * @param inputFile The name of the file to read from
     * @return The contents of the file or "" on an error reading from the file.
     */
    public static double readFile(String inputFile) throws IOException {
        int numValues;
        int valueSum = 0;
        int count = 0;
        double fileAverage;

        FileInputStream fileInStream = null;
        Scanner inFS = null;

        fileInStream = new FileInputStream(inputFile);
        inFS = new Scanner(fileInStream);

        if (!inFS.hasNext()) {
            fileAverage = 0.0;
            return fileAverage;
        }

        while (inFS.hasNext()) {
            numValues = inFS.nextInt();
            valueSum += numValues;
            count++;
        }

        fileAverage = (double) valueSum / count;
        fileInStream.close();

        return fileAverage;
    }

    /**
     * This method writes the average into a new file created by the user.
     *
     * @param outputFile  The name of the file to write to.
     * @param fileAverage The contents to be written to the file.
     * @return true if the file was created, false otherwise.
     */
    public static boolean writeFile(String outputFile, double fileAverage) {
        FileOutputStream fileStream = null;
        PrintWriter outFS = null;

        try {
            fileStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        outFS = new PrintWriter(fileStream);

        outFS.println(fileAverage);
        outFS.close();
        return false;
    }

    /**
     * This method calculates the average of all the integers within the file.
     *
     * @param args usused
     */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String inputFile;
        double fileAverage = 0.0;
        String outputFile;

        try {
            System.out.println("What is the name of the file you want to open?");
            if (!scnr.hasNext()) {
                return;
            } else {
                inputFile = scnr.next();
            }
            fileAverage = readFile(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("File name is invalid!");
            System.out.println("What is the name of the file you want to open?");
            inputFile = scnr.next();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("What is the name of the file you want to write in?");
        outputFile = scnr.next();
        writeFile(outputFile, fileAverage);
    }
}
