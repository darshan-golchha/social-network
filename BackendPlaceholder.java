// --== CS400 Fall 2023 File Header Information ==--
// Name: Darshan Golchha
// Email: dgolchha@wisc.edu
// Group: G15
// TA: Connor Bailey
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

/**
 * BackendPlaceholder is a placeholder for the BackendInterface. It is used for
 * testing purposes only. The methods in this class are not implemented and
 * only return dummy values.
 */
public class BackendPlaceholder implements BackendInterface{

    /**
     * This method thorws a FileNotFoundException if the filepath is not
     * "socialnetwork.dot". If the filepath is null or empty, it throws a
     * FileNotFoundException.
     * 
     * @param filepath -> filepath for the .dot file for the project
     * @throws FileNotFoundException if file doesn't exist
     */
    @Override
    public void readDataFromFile(String filepath) throws FileNotFoundException {
        if (filepath == null || filepath.isEmpty())
            throw new FileNotFoundException("Enter a valid filepath");
        if (!filepath.equals("socialnetwork.dot"))
            throw new FileNotFoundException("File " + filepath + " not found");
        return;
    }

    /**
     * This method returns a String array of size 5 with the following values:
     * participant1, intermediate1, intermediate2, intermediate3, participant2
     * where participant1 and participant2 are the input parameters. The
     * intermediate values are dummy values. It assumes that the input
     * parameters are not integers for testing purposes.
     * 
     * @param participant1 -> first participant
     * @param participant2 -> second participant
     * @return list of closest connection/closest connection
     * @throws InputMismatchException if input is not expected
     */
    @Override
    public String[] getCloseConnect(String participant1,String participant2) throws InputMismatchException{
        // for testing purposes, if the values inputted can be parsed as integers, throw an exception
        try{
            Integer.parseInt(participant1);
            Integer.parseInt(participant2);
            // if the values can be parsed as integers,
            // we throw an InputMismatchException
            throw new InputMismatchException();
        }catch(NumberFormatException e){
            // since the values cannot be parsed as integers, continue
        }

        // Create a dummy array of size 5 which contains the input parameters
        // and dummy values.
        String[] result = new String[5];
        result[0] = participant1;
        result[1] = "intermediate1";
        result[2] = "intermediate2";
        result[3] = "intermediate3";
        result[4] = participant2;
        return result;
    }

    /**
     * This method returns a Double array of size 3 with the following values:
     * 39.0, 43.0, 43.0. These values are dummy values which correspond to the
     * number of participants, friendships, and average number of friends of all
     * the participants respectively.
     * 
     * @return Array of Doubles with that statistic about the dataset.
     */
    @Override
    public String getStats() {
        String output = "\nNumber of Participants: " + "100" + "\nNumber of Friendships: " + "343" + "\nAverage number of friends: " + "3.43";
	return output;
    }
    
}
