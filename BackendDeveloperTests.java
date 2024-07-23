import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class BackendDeveloperTests{

    @Test
    public void testReadFile() throws FileNotFoundException {
        Backend backend = new Backend();
        try {
            backend.readDataFromFile("socialnetwork.dot");
            System.out.println("File successfully loaded");
        } catch (Exception e){
            Assertions.fail("File not loaded");
        }

    }

    @Test
    public void testCloseConnect() throws FileNotFoundException {
        Backend backend = new Backend();
        backend.readDataFromFile("socialnetwork.dot");
        System.out.println(Arrays.toString(backend.getCloseConnect("user37", "user86")));
        Assertions.assertEquals(Arrays.toString(backend.getCloseConnect("user37","user86")), Arrays.toString(new String[]{"user37", "user86"}));
    }

    @Test
    public void testCloseConnectNull(){
        Backend backend = new Backend();
        Assertions.assertThrows(NullPointerException.class, () -> backend.getCloseConnect(null,null));
    }

    @Test
    public void testGetStats() throws FileNotFoundException {
        Backend backend = new Backend();
        backend.readDataFromFile("socialnetwork.dot");
        String expected = "\nNumber of Participants: " + "100.0" + "\nNumber of Friendships: " + "587.0" + "\nAverage number of friends: "+ "5.87";
	Assertions.assertTrue(expected.equals(backend.getStats()));
    }

    @Test
    public void testGetNullStats(){
        Backend backend = new Backend();
	String expected = "\nNumber of Participants: " + "0.0" + "\nNumber of Friendships: " + "0.0" + "\nAverage number of friends: "+ "NaN";
        Assertions.assertTrue(expected.equals(backend.getStats()));
    }
}
