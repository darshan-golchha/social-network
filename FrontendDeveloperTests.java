import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Scanner;

/**
 * This class contains the tests for the FrontendDeveloper class.
 */
public class FrontendDeveloperTests {

    /**
     * This method tests the startMainMenu method of the Frontend class
     * by checking if the output contains the expected info.
     * 
     * It checks if the output contains the main menu options and if an
     * error message is printed when an invalid input is entered.
     */
    @Test
    public void testStartMainMenu() {

        // Checking that the main menu command contains the Main Menu Options:

        // Creating a valid user input string
        String input = "4\n";

        // Creating the tester helper object
        TextUITester tester = new TextUITester(input);
        // Creating the frontend object with BackendPlaceholder Class and a Scanner
        // Object
        Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        // starting the main command loop
        frontend.startMainMenu();
        // getting the tester output
        String output = tester.checkOutput();
        // ensuring correct results returned
        assertTrue(output.contains("\n\n===================================================================\n" +
                "|  Welcome to the Social Tracker App! What would you like to do?  |\n" +
                "===================================================================\n" +
                "|  Command  |            Description                              |\n" +
                "===================================================================\n" +
                "|     1     | Input a data file (Usage: 1 <filename>)             |\n" +
                "|     2     | See user statistics (Usage: 2)                      |\n" +
                "|     3     | See your closest connection (Usage: 3 <user> <user>)|\n" +
                "|     4     | Exit the app (Usage: 4)                             |\n" +
                "===================================================================\n\n" +
                "Type your choice here:"));
        // Checking that an exception is thrown for unexpected input
        String invalidInput = "5\n4\n";
        // Changing the tester object to a new one with invalid input and reinitializing
        // frontend
        tester = new TextUITester(invalidInput);
        frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        // starting the main command loop with invalid input
        frontend.startMainMenu();
        // getting the invalid output
        String invalidOutput = tester.checkOutput();
        // Ensuring the correct exception was thrown
        assertTrue(invalidOutput.contains("Invalid Command Entered, Check The MainMenu."));
    }

    /**
     * This method tests the loadDataFile method of the Frontend class
     * by checking if the output contains the expected info.
     * 
     * It checks if the output contains the expected info when a valid
     * file is loaded and if an error message is printed when an invalid
     * file is loaded.
     */
    @Test
    public void testLoadDataFile() {
        // Creating a user input string for loading a file
        String input = "1 socialnetwork.dot\n4\n";
        // Creating the tester helper object
        TextUITester tester = new TextUITester(input);
        // Creating the frontend object with BackendPlaceholder Class and a Scanner
        // Object
        Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        try {
            // starting the main command loop
            frontend.startMainMenu();
            // getting the tester output
            String output = tester.checkOutput();

            // Checking if the output contains the expected info
            assertTrue(output.contains("File socialnetwork.dot successfully loaded."));
        } catch (Exception e) {
            fail("An unexpected exception has been thrown");
        }

        // Checking that an exception is thrown if the file is not found
        String invalidInput = "1 invalid.dot\n4\n";
        // Changing the tester object to a new one with invalid input and reinitializing
        // frontend
        tester = new TextUITester(invalidInput);
        frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        try {
            // starting the main command loop with invalid input
            frontend.startMainMenu();
            // getting the invalid output
            String invalidOutput = tester.checkOutput();
            // Ensuring the correct exception was thrown
            assertTrue(invalidOutput.contains("File invalid.dot not found"));
        } catch (Exception e) {
            fail("An unexpected exception has been thrown");
        }
    }

    /**
     * This method tests the showUserStatistics method of the Frontend class
     * by checking if the output contains the expected info.
     * 
     * It checks if the output contains the expected info when a valid
     * file is loaded and if an error message is printed when an invalid
     * file is loaded.
     */
    @Test
    public void testShowUserStatistics() {
        // Creating a user input string for loading a file
        String input = "1 socialnetwork.dot\n2\n4\n";
        // Creating the tester helper object
        TextUITester tester = new TextUITester(input);
        // Creating the frontend object with BackendPlaceholder Class and a Scanner
        // Object
        Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        try {
            // starting the main command loop
            frontend.startMainMenu();
            // getting the tester output
            String output = tester.checkOutput();
            // Checking if the output contains the expected info
            assertTrue(!output.contains("Number of Participants: 0"));
            assertTrue(!output.contains("Number of Friendships: 0"));
            assertTrue(!output.contains("Average number of friends: 0"));
        } catch (Exception e) {
            fail("An unexpected exception has been thrown.");
        }

        // Checking that a message to load a dataset is thrown if the
        // statistics is asked before loading the dataset

        String invalidInput = "2\n4\n";
        // Changing the tester object to a new one with invalid input and reinitializing
        // frontend
        tester = new TextUITester(invalidInput);
        frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        try {
            // starting the main command loop with invalid input
            frontend.startMainMenu();
            // getting the invalid output
            String invalidOutput = tester.checkOutput();
            // Ensuring the correct exception was thrown
            assertTrue(invalidOutput.contains("No File Loaded, Please Load A File First"));
        } catch (Exception e) {
            fail("An unexpected exception has been thrown.");
        }
    }

    /**
     * This method tests the showClosestConnection method of the Frontend class
     * by checking if the output contains the expected info.
     * 
     * It checks if the output contains the expected info when a valid
     * file is loaded.
     */
    @Test
    public void testClosestConnection() {
        // Creating a user input string for loading a file
        String input = "1 socialnetwork.dot\n3 user1 user10\n4\n";
        // Creating the tester helper object
        TextUITester tester = new TextUITester(input);
        // Creating the frontend object with BackendPlaceholder Class and a Scanner
        // Object
        Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        try {
            // starting the main command loop
            frontend.startMainMenu();
            // getting the tester output
            String output = tester.checkOutput();
            // Checking if the output contains the expected info
            assertTrue(output.contains("Between user1 and user10 the closest connection is:"));
        } catch (Exception e) {
            fail("An unexpected exception has been thrown.");
        }
    }

    /**
     * This method tests the exitApp method of the Frontend class
     * by checking if the output contains the expected info.
     * 
     * It checks if the output contains the expected info when a valid
     * file is loaded.
     */
    @Test
    public void testExitApp() {
        // Creating a user input string for loading a file
        String input = "4\n";
        // Creating the tester helper object
        TextUITester tester = new TextUITester(input);
        // Creating the frontend object with BackendPlaceholder Class and a Scanner
        // Object
        Frontend frontend = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        try {
            // starting the main command loop
            frontend.startMainMenu();
            // getting the tester output
            String output = tester.checkOutput();
            // Checking if the output contains the expected info
            assertTrue(output.contains("Thankyou for using the App."));
        } catch (Exception e) {
            fail("An unexpected exception has been thrown.");
        }
    }

    @Test
    public void testIntegrationReadData() {
        // Creating a user input string for loading a file
        String input = "1 socialnetwork.dot\n4\n";
        BackendInterface backend = new Backend();
        // Creating the tester helper object
        TextUITester tester = new TextUITester(input);
        // Creating the frontend object with BackendPlaceholder Class and a Scanner
        // Object
        Frontend frontend = new Frontend(backend, new Scanner(System.in));
        try {
            // starting the main command loop
            frontend.startMainMenu();
            // getting the tester output
            String output = tester.checkOutput();
            // Checking if the output contains the expected info
            assertTrue(output.contains("File socialnetwork.dot successfully loaded."));
        } catch (Exception e) {
            fail("An unexpected exception has been thrown.");
        }
    }

    @Test
    public void testIntegrationClosestConnection() {
        // Prepare valid input for finding the closest connection
        String input = "1 socialnetwork.dot\n3 user37 user86\n4\n";
        TextUITester tester = new TextUITester(input);

        // Create frontend and backend instances
        BackendInterface backend = new Backend();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        // Start the main menu, which includes loading a data file and finding the
        // closest connection
        frontend.startMainMenu();

        // Check the output to ensure successful loading and closest connection finding
        String output = tester.checkOutput();
        assertTrue(output.contains("File socialnetwork.dot successfully loaded."));
        assertTrue(output.contains("Between user37 and user86 the closest connection is:"));
    }

    /**
     * This method tests the Backend class (Partner's Code) by checking the
     * ReadData method of the Backend class with an invalid file.
     */
    @Test
    public void testBackendInvalidFile(){
        // Creating an instance of the Backend class
        BackendInterface backend = new Backend();
        try{
            // Try to load an invalid file
            backend.readDataFromFile("invalid.dot");
            fail("An exception should have been thrown");
        }
        catch(Exception e){
            // Check that the correct exception was thrown
            assertTrue(e.getMessage().contains("The file could not be found."));
        }
    }

    /**
     * This method tests the Backend class (Partner's Code) by checking the
     * getStats method of the Backend class without loading a file.
     */
    @Test
    public void testBackendInvalidUser(){
        // Creating an instance of the Backend class
        BackendInterface backend = new Backend();
        // Loading a valid file
        try{
            backend.readDataFromFile("socialnetwork.dot");
        }
        catch(Exception e){
            fail("An exception should not have been thrown for a valid file.");
        }
        try{
            // Passing invalid users to the closest connection method
            backend.getCloseConnect("part1", "part2");
            fail("An exception should have been thrown");
        } catch(Exception e){
            // Check that the correct exception was thrown
            assertTrue(e.getMessage().contains("Please enter a valid user"));
        }
    }

}
