///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           12z, TestH12CustomApp
// Course:          CS 200, Spring 2024
//
// Author:          Winston Chan
// Email:           wchan35@wisc.edu
// Lecturer's Name: Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// No help given or received.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is the test bench that contains testing methods for the H12CustomApp class.
 * The createTestDataFile and readTestDataFile are private testing methods intended to
 * be used within the test cases.
 * <p>
 * All the test cases within the testH12CustomApp method should be changed to test the
 * methods in your H12CustomApp class.
 *
 * @author Jim Williams
 * @author Winston Chan
 */
public class TestH12CustomApp {

    /**
     * This method runs the selected tests.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        testH12CustomApp();
    }

    /**
     * This is a testing method to create a file with the specified name and fileContents
     * to be used by other testing methods. On a FileNotFoundException a stack trace is printed and
     * then returns.
     *
     * @param testDataFilename The filename of the testing file to create.
     * @param fileContents     The data to put into the file.
     */
    private static void createTestDataFile(String testDataFilename, String fileContents) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(testDataFilename);
            writer.print(fileContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    /**
     * This is a testing method to read and return the entire contents of the specified file to
     * be used soley by other testing methods.
     * On a FileNotFoundException a stack trace is printed and then "" returned.
     *
     * @param dataFilename The name of the file to read.
     * @return The contents of the file or "" on error.
     */
    private static String readTestDataFile(String dataFilename) {
        File file = new File(dataFilename);
        Scanner input = null;
        String contents = "";
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                contents += input.nextLine() + "\n"; //assuming all lines end with newline
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (input != null) input.close();
        }
        return contents;
    }

    /**
     * Tests that the H12CustomApp read input and write output methods handle
     * the cases described in their method header comments.
     *
     * @return true for passing all testcases, false otherwise
     */
    public static boolean testH12CustomApp() {
        boolean error = false;
        {  //test that a file with a few lines of text can be read

            //create a data file to be read by the read method.
            String fileToRead = "testRead.txt";
            String expectedContents = "23\n24\n25\n";
            createTestDataFile(fileToRead, expectedContents);
            double expectedAverage = 24.0;

            //now read the file using the H12CustomApp read method that we are testing
            double actualContents = 0;
            try {
                actualContents = H12CustomApp.readFile(fileToRead);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (Math.abs(actualContents - expectedAverage) > 0.001) {
                error = true;
                System.out.println("readFile 1) expected:" + expectedAverage
                        + " actual: " + actualContents);
            } else {
                System.out.println("readFile 1) success");
                File file = new File(fileToRead);
                file.delete();
            }
        }

        { //test that an invalid file returns "File name is invalid!\n" +
            // "What is the name of the file you want to open?" for content.

            //make sure the file doesn't exist by deleting it if it does.
            String fileToRead = "fileThatShouldNotExist";
            File file = new File(fileToRead);
            if (file.exists()) {
                file.delete();
            }

            String expectedContents = "File name is invalid!\n" +
                    "What is the name of the file you want to open?";
            String expectedAverage = null;

            //now try to read the file using the H12CustomApp method we are testing
            double actualContents = 0;
            try {
                actualContents = H12CustomApp.readFile(fileToRead);
            } catch (IOException e) {
                expectedAverage = "File name is invalid!\n" +
                        "What is the name of the file you want to open?";
            }

            //check if the contents are as described in the H12CustomApp.readFile method header
            if (!expectedContents.equals(expectedAverage)) {
                error = true;
                System.out.println("readFile 2) expected:" + expectedContents
                        + " actual: " + actualContents);
            } else {
                System.out.println("readFile 2) success");
            }
        }

        { //test that contents are correctly written to the specified file.

            //use our write file method to write some data to a file
            String fileToRead = "testRead.txt";
            String fileNameToWrite = "testWrite.txt";
            String fileContents = "23\n24\n25\n";
            createTestDataFile(fileToRead, fileContents);
            double averageContents = 0.0;
            try {
                averageContents = H12CustomApp.readFile(fileToRead);
                H12CustomApp.writeFile(fileNameToWrite, averageContents);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //use the testing method to read the file
            String actualStringContents = readTestDataFile(fileNameToWrite);
            double expectedContents = 24.0;
            double actualContents = Double.parseDouble(actualStringContents);

            //check if the contents are the same
            if (Math.abs(actualContents - expectedContents) > 0.001) {
                error = true;
                System.out.println("writeFile 3) expected:" + expectedContents
                        + " actual: " + actualContents);
            } else {
                System.out.println("writeFile 3) success");
                //since the test succeeded, remove the temporary testing file.
                File file = new File(fileNameToWrite);
                file.delete();
            }
        }

        { //test that an invalid file returns "" for content.
            String fileNameToWrite = "missingDirectory/fileThatShouldNotExist";
            boolean expectedResult = false;

            //check that the directory doesn't exist, since we want writeFile to handle the
            // exception when it tries to write the file to that non-existing directory.
            File file = new File(fileNameToWrite);
            if (file.getParentFile().exists()) {
                error = true;
                System.out.println("writeFile 4) The directory: " + file.getParentFile().getName()
                        + " should not exist for this test to run correctly.");
            } else {
                System.out.println("writeFile 4) success");
            }

            //now try to write some actual data to the file
            boolean actualResult = false;
            actualResult = H12CustomApp.writeFile("testWrite.txt", 24.0);

            if (actualResult != expectedResult) {
                error = true;
                System.out.println("writeFile 5) expected:" + expectedResult
                        + " actual: " + actualResult);
            } else {
                System.out.println("writeFile 5) success");
            }
        }

        if (error) {
            System.out.println("\nTestH12CustomApp failed");
            return false;
        } else {
            System.out.println("\nTestH12CustomApp passed");
            System.out.println("There may be output from the methods being tested.");
            return true;
        }
    }
}
