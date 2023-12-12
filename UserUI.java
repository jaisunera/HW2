import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserUI implements ActionListener {
    private JFrame frame; //Main window

    private JTextField userIDField;
    private JTextField messageField;

    private JButton followUserButton;
    private JButton tweetMessageButton;

    private JList<User> followingList;
    private JList<Tweet> feedList;

    private User currentUser;
    private String newFollowerID;
    private String newTweetText;
    private ArrayList<User> userList;

    UserUI(User u, ArrayList<User> uList) {
        this.currentUser = u;
        this.userList = uList;

        frame = new JFrame(u.toString() + "'s View"); //Title of the window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Allow user to exit the window
        frame.setSize(500, 500); //Size of the window
        frame.setLayout(null); //No layout

        frame.getContentPane().setBackground(Color.decode("#CDE990")); //Background color

        //User ID and message text fields
        userIDField = createAndConfigureTextField("User ID", 50, 10);
        messageField = createAndConfigureTextField("Message", 50, 235);

        //Follow and tweet button
        followUserButton = createAndConfigureButton("Follow User", 300, 10, 150);
        tweetMessageButton = createAndConfigureButton("Post Tweet", 300, 235, 150);

        //List view of current following
        followingList = new JList<>(currentUser.getFollowersListFeed().getFollowers());
        followingList.setBounds(50, 75, 400, 150);
        followingList.setFocusable(false);
        followingList.setBackground(Color.decode("#FFD4D4")); //Background color

        //List view of live feed
        feedList = new JList<>(currentUser.getLiveUserFeed().getFeed());
        feedList.setBounds(50, 300, 400, 150);
        feedList.setFocusable(false);
        feedList.setBackground(Color.decode("#FFD4D4")); //Background color

        //Display creation time of the User + last updated
        displayUserCreationTime();
        displayUserLastUpdateTime();

        //Adding components to the frame
        frame.add(userIDField);
        frame.add(messageField);

        frame.add(followUserButton);
        frame.add(tweetMessageButton);

        frame.add(followingList);
        frame.add(feedList);

        frame.setVisible(true); //Set frame visible
    }

    //Method to display the time in which the user was created in milliseconds
    private void displayUserCreationTime() {
        long creationTime = currentUser.getCreationTime();
        System.out.println(this.currentUser + ": " + creationTime + "ms"); //Print creationTime to console
    }

    //Method to display the user's last updated time in milliseconds
    private void displayUserLastUpdateTime() {
        long lastUpdateTime = currentUser.getLastUpdateTime();
        System.out.println(this.currentUser + "'s last update': " + lastUpdateTime + "ms"); //Print lastUpdateTime to console
    }

    //Action listeners for follow user and tweet buttons
    public void actionPerformed(ActionEvent e) {
        //If follow button is clicked, perform followUserAction
        if (e.getSource() == followUserButton) {
        followUserAction();
        }

        //If tweet button is clicked, perform tweetMessageAction
        if (e.getSource() == tweetMessageButton) {
        tweetMessageAction();
        }
    }

    //Method to handle action when follow button is clicked
    private void followUserAction() {
        String newFollowerID = userIDField.getText(); //Get userID from text field
        userIDField.setText(""); //Clear text field

        int index = findUserIndex(newFollowerID); //Find index of the user in the user list
        //If the user is found, get the user, add to following
        if (index != -1) {
        User userToFollow = userList.get(index);
        userToFollow.addToFollowers(currentUser);
        currentUser.addToFollowing(userToFollow);
        }
    }

    //Method to find the index of the user in the user list
    private int findUserIndex(String userID) {
        for (int x = 0; x < userList.size(); x++) {
        if (userID.equals(userList.get(x).toString())) {
            return x;
        }
        }
        return -1; //User not found
    }

    //Method to handle the action when tweet button is clicked
    private void tweetMessageAction() {
        String newTweetText = messageField.getText(); //Get message from text field
        messageField.setText(""); //Clear text field

        //Create a new tweet using entered text and the current user
        Tweet newTweet = new Tweet(newTweetText, currentUser);
        currentUser.makeTweet(newTweet); //Add the tweet to list of tweets

        //Get the list of followers of the current user
        ArrayList<User> followers = currentUser.getFollowers();
        //Iterate through the followers list, and add the tweet for each follower
        for (User follower : followers) {
        follower.addToFeed(newTweet);
        }
    }

    //Preferred look for text fields
    private JTextField createAndConfigureTextField(String text, int x, int y) {
        JTextField textField = new JTextField(text);
        textField.setBounds(x, y, 225, 50); //Dimension of text fields
        textField.setBackground(Color.decode("#FFFFE8")); //Background color
        //Raised bevel border for text area
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }

    //Preferred look for buttons
    private JButton createAndConfigureButton(String text, int x, int y, int width) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, 50);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(width, 50)); //Dimension of buttons
        button.setBackground(Color.decode("#FFFFE8")); //Background color
        //Raised bevel border for button
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return button;
    }
}
