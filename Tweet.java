public class Tweet {
    private String tweet; //Stores the text of the tweet
    private long timeStamp; //Stores the timestamp when the tweet was sent
    private boolean positive; //Indicates whether the tweet is positive or not

    //Default constructor for creating an empty tweet with default values
    public Tweet() {
        this.tweet = "";
        this.timeStamp = System.currentTimeMillis(); //Set the timestamp to the current time
        this.positiveCheck(); //Check the positivity of the tweet
    }

    //Constructor to create a tweet with text and associated user
    public Tweet(String text, User user) {
        this.tweet = user.toString() + ": " + text; //Concatenate user information with text
        this.timeStamp = System.currentTimeMillis(); //Set the timestamp to the current time
        this.positiveCheck(); //Check the positivity of the tweet
    }

    //Method to check if the tweet contains positive words
    private void positiveCheck() {
        String lowercaseText = tweet.toLowerCase(); //Convert text to lowercase for case-insensitive check
        //List of positive words
        String[] positiveWords = {"happy", "good", "yay", "love", "slay", "cute", "laugh", "great", "funny", "pretty", "yummy"};

        //For every word in a tweet, look for positive word
        for (String word : positiveWords) {
            if (lowercaseText.contains(word)) {
                this.positive = true; //Set the positive flag to true if a positive word is found
                return; //Once a positive word is found, set positive flag and exit the loop
            }
        }

        //If no positive words are found, set the flag to false
        this.positive = false;
    }

    //Method to return the tweet text
    public String getText() {
        return tweet;
    }

    //Method to display the tweet text
    public void displayText() {
        System.out.println(tweet);
    }

    //Method to get the timestamp of the tweet
    public long getTimeStamp() {
        return timeStamp;
    }

    //Method to check if the tweet is positive or not
    public boolean getPositivity() {
        return positive;
    }

    //Override the toString method to return the tweet text
    public String toString() {
        return tweet;
    }
}
