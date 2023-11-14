public class PositiveVisitor implements IdVisitor {
    private int positiveMessages = 0; //Positive counter set to 0

    public void visitGroup(Group g) {
    }

    //Override visitUser method to count positive tweets for users
    public void visitUser(User user) {
        int count = 0; //Counter for positive tweets

        //Loop through user's tweets and count positive ones
        for (Tweet tweet : user.getUserTweets()) {
            if (tweet.getPositivity()) {
                count++;
            }
        }

        //Increment positiveMessages by the count of positive tweets for this user
        positiveMessages += count;
    }

    //Getter method for positiveMessages
    public int getPositiveCounter() {
        return positiveMessages;
    }
}