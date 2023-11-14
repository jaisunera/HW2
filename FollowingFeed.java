import javax.swing.DefaultListModel;

public class FollowingFeed implements Observer {
    //Two DefaultListModel instances to manage followers
    private DefaultListModel<User> followers = new DefaultListModel<>();
    private DefaultListModel<User> followers2 = new DefaultListModel<>();

    //This method gets called when there's an update from the observed Subject
    public void update(Subject subject) {
        //If the update is related to Following, retreive the followers list from the subject and assign followers2 reference to followers
        if (subject instanceof Following) {
            this.followers2 = ((Following) subject).getFollowers();

            followers = followers2;
        }
    }

    // Getter method to retrieve the followers list
    public DefaultListModel<User> getFollowers() {
        return followers;
    }
}
