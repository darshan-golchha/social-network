import java.util.List;

/*
 * Interface for class that stores the results of a shortest path search.
 */
public interface ShortestPathInterface<T extends Comparable<T>> {
    
    /* 
     * Getter method to get the shortest path between the two partcipants
     * 
     * @return list of shortest paths 
    */
    public List<T> getShortPath();

    /*
     * Getter method for the number of intermediary friends that connect the two
     * participants.
     */
    public double getNumFriends(String user1, String user2);

}
