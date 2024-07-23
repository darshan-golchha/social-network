import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Backend implements BackendInterface, ShortestPathInterface{

    private DijkstraGraph<String, Integer> graph;
    private ArrayList<String[]> path;

    /**
     * Constructor for Backend (initializes values for DijkstraGraph instance and for search result storing).
     */
    public Backend(){
        this.graph = new DijkstraGraph<>(new PlaceholderMap<>());
        this.path = new ArrayList<>();
    }
    @Override
    public void readDataFromFile(String filepath) throws FileNotFoundException {
        Scanner input;

        try {
            File file = new File(filepath);
            input = new Scanner(file);
            input.nextLine();

            while (input.hasNextLine()) {
                //Split the values and store them in a String array to insert into our required fields
                String line = input.nextLine();
                if (line.endsWith(";")){
                    String[] lineCharacters = line.split(" -- ");
                    String user1 = lineCharacters[0].replace("\"","").trim();
                    String user2 = lineCharacters[1].replace("\"","").replace(";","").trim();
                    if(!graph.containsNode(user1)){
                        this.graph.insertNode(user1);
                      }
                    if(!graph.containsNode(user2)){
                        this.graph.insertNode(user2);
                      }
                    graph.insertEdge(user1,user2,1);
		    graph.insertEdge(user2,user1,1);
                }
            }
            input.close();

        } catch (Exception e) {
            if (filepath.endsWith(".dot")) {
                throw new FileNotFoundException("The file could not be found.");
            } else {
                throw new IllegalArgumentException("Illegal file type. The file should be a .dot file");
            }
        }
    }

    @Override
    public String[] getCloseConnect(String participant1, String participant2) throws InputMismatchException {
        if(participant1 == null || participant2 == null){
            throw new NullPointerException("Participants cannot be null");
        }
        else if(!participant1.contains("user") || !participant2.contains("user")) {
            throw new InputMismatchException("Please enter a valid user");
        }

        try{
            String[] shortestPath = graph.shortestPathData(participant1, participant2).toArray(new String[0]);
            this.path.add(shortestPath);
            return shortestPath;
        }
        catch(NoSuchElementException e){
            throw new NoSuchElementException("No connection to node exists in graph");
        }
    }

    @Override
    public String getStats() {
        double numParticipants = graph.nodes.getSize();
        double numFriendships = graph.edgeCount;
        double averageFriends = numFriendships/numParticipants; 
        String output = "\nNumber of Participants: " + numParticipants + "\nNumber of Friendships: " + numFriendships
	    + "\nAverage number of friends: " + averageFriends;
	return output;
    }

    @Override
    public List<String[]> getShortPath() {
        return path;
    }

    @Override
    public double getNumFriends(String user1, String user2) {
        return graph.shortestPathCost(user1, user2);
    }
}
