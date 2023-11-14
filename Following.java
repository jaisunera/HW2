import java.util.ArrayList;
import javax.swing.DefaultListModel;

//Represents a list of followers and notifies observers about changes
public class Following extends Subject {
    private DefaultListModel<User> followers = new DefaultListModel<>(); //List to store followers

    // Method to retrieve the list of followers
    public DefaultListModel<User> getFollowers() {
        return followers; // Return the list of followers
    }
    
    //Method to add a new followers to the list and notify observers
    public void addFollower(User newFollowers) {
        this.followers.addElement(newFollowers); //Add the new follower to the list
        notifyObservers(); //Notify observers about the change
    }

    // Method to set the list of followers and notify observers
    public void setFollowers(ArrayList<User> f) {
        for (int x = 0; x < f.size(); x++) {
            this.followers.add(x, f.get(x)); // Add followers from the given list to the existing list
        }
        notifyObservers(); // Notify observers about the change
    }
}
