// --== CS400 Fall 2023 File Header Information ==--
// Name: Darshan Golchha
// Email: dgolchha@wisc.edu
// Group: G15
// TA: Connor Bailey
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is the Frontend for the Social Network App. It implements the
 * FrontendInterface. It takes an instance of the BackendInterface as a
 * constructor parameter and exposes the following functionality to the user.
 */
public class Frontend implements FrontendInterface {

    BackendInterface backend; // Creating a varible for the backend
    Scanner scnr; // Creating a variable for the scanner
    Boolean fileLoaded; // Creating a variable to check if the file is loaded

    /**
     * Constructor for the Frontend class. It takes an instance of the
     * BackendInterface and a Scanner as parameters.
     * 
     * @param backend -> instance of the BackendInterface
     * @param scnr    -> instance of the Scanner
     */
    public Frontend(BackendInterface backend, Scanner scnr) {
        this.backend = backend;
        this.scnr = scnr;
        this.fileLoaded = false;
    }

    /**
     * This method starts the main menu for the Social Network App. The menu
     * provides the following options to the user:
     * 1. input a data file
     * 2. see user statistics
     * 3. see your closest connection
     * 4. exit the app
     * It runs in a loop until the user chooses to exit the app taking
     * care of all the exceptions.
     */
    public void startMainMenu() {
        // Initializing the input variable
        String input = "";
        // Creating the welcome message
        String welcome = "\n\n===================================================================\n" +
                "|  Welcome to the Social Tracker App! What would you like to do?  |\n" +
                "===================================================================\n" +
                "|  Command  |            Description                              |\n" +
                "===================================================================\n" +
                "|     1     | Input a data file (Usage: 1 <filename>)             |\n" +
                "|     2     | See user statistics (Usage: 2)                      |\n" +
                "|     3     | See your closest connection (Usage: 3 <user> <user>)|\n" +
                "|     4     | Exit the app (Usage: 4)                             |\n" +
                "===================================================================\n\n" +
                "Type your choice here:";

        // Running the loop until the user chooses to exit the app
        while (true) {
            // Printing the welcome message
            System.out.println(welcome);
            // Getting the input from the user and trimming it to extract the command and
            // the params
            input = scnr.nextLine();
            input = input.trim();

            // Checking if the input is empty
            if (input.length() == 0) {
                // If the input is empty, we ask the user to enter a command
                System.out.println("Please Enter A Command, Check The MainMenu.");
                continue;
            }
            // Checking if the input is a valid command and running the corresponding
            // function
            if (input.charAt(0) == '1') {
                // If no filename is provided, we print an error message
                if (input.length() == 1) {
                    System.out.println("Please Provide a File to Read.");
                } else {
                    // Since the user chose to input a data file, we call the loadDataFile function
                    // and pass the params to it
                    try {
                        this.fileLoaded = loadDataFile(input.substring(1).trim());
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());

                    } catch (Exception e) {
                        System.out.println(e.getMessage());

                    }
                }
            } else if (input.charAt(0) == '2') {
                // Since the user chose to see the user statistics, we call the
                // showUserStatistics function
                if (input.length() != 1) {
                    // If the user entered any params, we print an error message
                    // since this function doesn't take any params
                    System.out.println("Invalid Input For User Statistics, No Params Required");
                }
                // Calling the showUserStatistics function
                showUserStatistics();
            } else if (input.charAt(0) == '3') {
                // Since the user chose to see the closest connection, we call the
                // closestConnection function
                // and pass the params to it
                String[] params = input.split("\\s+"); // Splitting the input to extract the params
                if (params.length != 3) {
                    // If the user inputs more or less than 2 params, we print an error message
                    // since this function requires only two participants
                    System.out.println("Invalid Input For Closest Connection, Excatly Two Participants Required");
                    continue;
                }
                // Calling the closestConnection function within a try-catch block
                // to handle any exceptions
                try {
                    closestConnection(params[1], params[2]);
                } catch (InputMismatchException e) {
                    System.out.println("The provided usernames are invalid");
                } catch (NoSuchElementException e) {
                    System.out.println("No connection found between " + params[1] + " and " + params[2]);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (input.charAt(0) == '4') {
                // Since the user chose to exit the app, we call the exitApp function
                if (input.length() != 1) {
                    // If the user inputs any params, we print an error message
                    System.out.println("Invalid Input For Exit Command, No Params Required");
                }
                // Calling the exitApp function
                exitApp();
                // Breaking out of the loop
                break;
            } else {
                // If the user inputs an invalid command, we print an error message
                System.out.println("Invalid Command Entered, Check The MainMenu.");
            }
        }
    }

    /**
     * This method loads the data file into the backend. It takes the filename as
     * a parameter and returns true if the file was loaded successfully and false
     * otherwise.
     * 
     * @param filename -> name of the file to be loaded
     * @return true if the file was loaded successfully and false otherwise
     * @throws FileNotFoundException if the file doesn't exist
     */
    @Override
    public boolean loadDataFile(String filename) throws FileNotFoundException {
        // Checking if the filename is empty
        if (filename == null || filename.isEmpty()) {
            // If the filename is empty, we print an error message
            System.out.println("Incorrect filename");
            return false;
        }
        // Calling the readDataFromFile function from the backend
        backend.readDataFromFile(filename);
        // Since no exception was thrown, we print a success message
        System.out.println("File " + filename + " successfully loaded.");
        // Returning true since the file was loaded successfully
        return true;
    }

    /**
     * This method prints the user statistics to the console. It prints the
     * following statistics:
     * 1. Number of participants
     * 2. Number of friendships
     * 3. Average number of friends of all the participants
     */
    @Override
    public void showUserStatistics() {
        // Checking if the file is loaded, if not we print an error message
        if (!fileLoaded) {
            System.out.println("No File Loaded, Please Load A File First");
            return;
        }
        // Calling the getStats function from the backend and storing the result in
        // values
        String stats = backend.getStats();

        // Checking if the result is null
        if (stats == null) {
            // If the result is null, we print an error message
            System.out.println("An internal error occured, wrong stats returned");
            return;
        }
        System.out.println(stats);
    }

    /**
     * This method prints the closest connection between two participants to the
     * console. It takes the two participants as parameters and prints the closest
     * connection between them. If there is no connection between the two
     * participants, it prints a message saying so.
     */
    @Override
    public void closestConnection(String participant1, String participant2) throws InputMismatchException {
        // Checking if the file is loaded, if not we print an error message
        if (!fileLoaded) {
            System.out.println("No File Loaded, Please Load A File First");
            return;
        }
        // Calling the getCloseConnect function from the backend and storing the result
        // in result
        String[] result = backend.getCloseConnect(participant1, participant2);

        // Checking if the result is null
        if (result == null) {
            System.out.println("An internal error occured, wrong data returned");
            return;
        }
        // Checking if the result is empty and printing a message if it is
        // otherwise printing the result
        if (result.length == 0) {
            System.out.println("No Connection Found Between " + participant1 + " and " + participant2);
            return;
        }
        String output = "Between " + participant1 + " and " + participant2 + " the closest connection is: ";
        for (int i = 0; i < result.length; i++) {
            output += result[i];
            if (i != result.length - 1) {
                output += " -> ";
            }
        }
        // Printing the output
        System.out.println(output);

    }

    /**
     * This method prints a goodbye message to the console.
     */
    @Override
    public void exitApp() {
        // Printing the goodbye message
        System.out.println("Thankyou for using the App.");
    }

    /**
     * This is the main method of the Frontend class. It creates an instance of
     * the Backend class and an instance of the Scanner class. It then
     * starts the main menu which is the main loop of the app.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Frontend frontend = new Frontend(new Backend(), new Scanner(System.in));
        frontend.startMainMenu();
    }

}
