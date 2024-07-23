import java.io.FileNotFoundException;
import java.util.InputMismatchException;
/**
 * An interface for the Frontend of the Social Track App
 */
public interface FrontendInterface {
  /**
   * Constructor Comment:
   *
   * Constructs a frontend object and takes in a reference to the Backend
   * and a Scanner instance to read in user input
   *
   * public FrontendInterface(BackendInterface backend, Scanner scnr) {}
   */
  /**
   * Starts the main command loop for the user,
   * prints the messages that the user engages with to demonstrate their
   * choices of what is do-able within the app
   *
   * The corresponding methods are called based on the text inputs in this method
   *
   * Main Menu looks like this:
   *
   * Welcome to the Social Tracker App! What would you like to do?
   *
   * 1) input a data file
   * 2) see user statistics
   * 3) see your closest connection
   * 4) exit the app
   *
   * Type your choice here:
   *
   * @throws InputMismatchException if unexpected input is found
   */
  public void startMainMenu() throws InputMismatchException;


   /**
   * Specifies and loads the data file by sending it to the corresponding
   * backend method
   *
   * If an error is found (either in this method or the corresponding backend
   * method), prints out the related error statement to let the user know that
   * the file was not loaded correctly
   *
   * If the file is loaded correctly into the backend, prints a success message
   *
   * @throws FileNotFoundException if file doesn't exist
   */
  public boolean loadDataFile(String filename) throws FileNotFoundException;

  /**
   * When called, calls on the backend methods to receive the statistics related
   * to: the number of participants (nodes), the number of friends (edges), and
   * the average number of friends
   *
   * Once this information is received, composes a String to show the
   * data to the user. Then prints this String
   *
   * String Format:
   *
   * Number of Participants: (number of nodes)
   * Number of Friendships: (number of edges)
   * Average number of friends: (average number of friends, expect double)
   */
  public void showUserStatistics();


  /**
   * When given two participants from the user, lists the closest connection
   * between the two given participants, including all intermediary friends
   *
   * Uses a Scanner to receive user input, then sends that input to the backend
   * corresponding method.
   *
   * With the information received from the backend, prints out a String containing
   * the closest connection between the two participants, and the intermediary friends
   *
   * String Format: *Likely temporary, depends on how the method is implemented on the backend*
   *
   * Between [Participant 1] and [Participant 2] the closest connection is: (closest connection)
   * Intermediary Friends found are: (list of intermediary friends)
   *
   * @throws InputMismatchException if user input is unexpected
   */
  public void closestConnection(String participant1, String participant2) throws InputMismatchException;

  /**
   * Exits the app, prints an exit message, and makes sure to close the system
   */
  public void exitApp();
}
