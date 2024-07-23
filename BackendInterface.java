import java.io.FileNotFoundException;
import java.util.InputMismatchException;

/*
 * Interface for backend that takes an instance of the GraphADT as a constructor 
 * parameter and exposes the following functionality to the frontend.
 */
public interface BackendInterface{
    /**
     * Loading/Reading the .dot file (via filepath)
     * 
     * @param filepath -> filepath for the .dot file for the project
     * @throws FileNotFoundException if file doesn't exist
     */
    public void readDataFromFile(String filepath) throws FileNotFoundException;

    /**
     * Getter for the closest connection between two participants
     * 
     * @param participant1 -> first participant
     * @param participant2 -> second participant
     * @return list of closest connection/closest connection
     * @throws InputMismatchException if input is not expected
     */
    public String[] getCloseConnect(String participant1, String participant2) throws InputMismatchException;

    /**
     * Getter for statistics about the dataset that includes the number of 
     * participants, friendships, and average number of friends of all the 
     * participants.
     * 
     * @return Double with that statistic stuff.
     */
    public String getStats();
}
