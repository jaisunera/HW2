import java.util.ArrayList;

public class User implements Id {
    private String userID;
    private ArrayList<User> followersList = new ArrayList<>();
    private ArrayList<User> followingList = new ArrayList<>();
    private ArrayList<Tweet> userFeed = new ArrayList<>();
    private ArrayList<Tweet> userTweets = new ArrayList<>();
    private long creationTime; //Add creationTime
    private long lastUpdateTime; //Add lastUpdateTime

    private FollowingFeed liveFollowers = new FollowingFeed();
    private Following followersSubject = new Following();
    private LiveFeed liveFeed = new LiveFeed();
    private UserFeed feedSubject = new UserFeed();

    //Constructor for the User class that takes id
    public User(String id) {
        this.userID = id; //Assigning id to the userID property of the User object
        this.creationTime = System.currentTimeMillis(); //Set creationTime to current system time in milliseconds
        this.lastUpdateTime = System.currentTimeMillis(); //Set lastUpdateTime to creation time initially

        //Attaching a liveFeed observer to the feedSubject and setting initial tweets
        feedSubject.attach(liveFeed);
        feedSubject.setTweets(this.getUserFeed());

        //Attaching a liveFollowers observer to the followersSubject and setting initial followers
        followersSubject.attach(liveFollowers);
        followersSubject.setFollowers(this.getFollowing());
    }

    //Constructor for the User class
    public User() {
        this.userID = null; //Sets the userID property to null
    }

    //Overrides the toString method of the Object class
    public String toString() {
        return userID; ////Returns the userID as a String
    }

    //Getter method to get the userID
    public String getId() {
        return userID;
    }

    //Setter method to set the userID
    public void setId(String id) {
        this.userID = id;
    }

    //Getter for creationTime
    public long getCreationTime() {
        return creationTime;
    }

    //Method to add a new follower to the user's followers list
    public void addToFollowers(User newFollower) {
        this.followersList.add(newFollower);
    }

    //Method to add a new user to the list of users being followed by the current
    //user
    public void addToFollowing(User newFollowing) {
        this.followingList.add(newFollowing); //Adding the new user to the following list
        this.userFeed.addAll(newFollowing.userTweets); //Adding the new user's tweets to the current user's feed
        followersSubject.addFollower(newFollowing); //Notifying a subject (followersSubject) about the addition of a new follower
        feedSubject.setTweets(userFeed); //Updating the feed subject with the user's updated feed
    }

    //Method to get a copy of the user's followers list
    public ArrayList<User> getFollowers() {
        return new ArrayList<>(followersList);
    }

    //Method to get a copy of the user's following list
    public ArrayList<User> getFollowing() {
        return new ArrayList<>(followingList);
    }

    //Method to create and add a new tweet based on text input
    public void makeTweet(String text) {
        Tweet t = new Tweet(text, this); //Creates a new Tweet object with the given text and 'this' user as the author
        makeTweet(t); //Calls the addTweet method to add the created tweet

        //Update lastUpdateTime when a new tweet is made
        updateLastUpdateTime();
    }

    //Method to directly add a provided Tweet to the user's tweets
    public void makeTweet(Tweet t) {
        addTweet(t); //Calls the addTweet method to add the provided tweet

        //Update lastUpdateTime when a new tweet is added
        //------updateLastUpdateTime();
    }

    //Private method to add a Tweet to the user's tweet list and feed
    private void addTweet(Tweet t) {
        this.userTweets.add(t); //Adds the tweet to the user's list of tweets
        this.userFeed.add(t); //Adds the tweet to the user's feed
        feedSubject.setTweets(userFeed); //Updates the feed subject with the user's updated feed

        //Update lastUpdateTime when a new tweet is added
        updateLastUpdateTime();
    }

    //Method to add a provided tweet to the user's feed
    public void addToFeed(Tweet t) {
        this.userFeed.add(t); //Adds the provided tweet to the user's feed
        feedSubject.setTweets(userFeed); //Updates the feed subject with the user's updated feed
    }

    //Method to get a copy of the user's feed
    public ArrayList<Tweet> getUserFeed() {
        return new ArrayList<>(this.userFeed);
    }

    //Method to get a copy of the user's tweets
    public ArrayList<Tweet> getUserTweets() {
        return new ArrayList<>(this.userTweets);
    }

    //Method to display the user's tweets in reverse chronological order
    public void displayUserTweets() {
        for (int x = userTweets.size() - 1; x >= 0; x--) {
        this.userTweets.get(x).displayText();
        }
    }

    //Observer access methods
    //Method to get access to the LiveFeed object related to the user's feed
    public LiveFeed getLiveUserFeed() {
        return this.liveFeed;
    }

    //Method to get access to the FollowingFeed object related to the user's list of followers
    public FollowingFeed getFollowersListFeed() {
        return liveFollowers;
    }

    //Method implementing the Visitor pattern - accepts a visitor object and calls its visitUser method
    public void accept(IdVisitor visitor) {
        visitor.visitUser(this);
    }

    //Method to update lastUpdateTime
    private void updateLastUpdateTime() {
        this.lastUpdateTime = System.currentTimeMillis(); //Update lastUpdateTime to current system time
    }

    //Getter method to get the last update time
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
}
