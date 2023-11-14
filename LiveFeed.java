import javax.swing.DefaultListModel;

public class LiveFeed implements Observer {
    // Define DefaultListModel instances
    private DefaultListModel<Tweet> feed = new DefaultListModel<>();
    private DefaultListModel<Tweet> feed2 = new DefaultListModel<>();
    private DefaultListModel<Tweet> unorganizedFeed = new DefaultListModel<>();

    // Method to update the user feed when changes occur
    public void update(Subject subject) {
        if (subject instanceof UserFeed) {
            this.feed2 = (((UserFeed) subject).getTweets());
            addTweets(); // Update the user feed with new tweets
        }
    }

    // Method to add tweets to the feed
    public void addTweets() {
        feed.clear(); // Clear the current user feed
        unorganizedFeed.clear(); // Clear the unsorted user feed

        // Add tweets from feed2 to unorganizedFeed
        for (int x = 0; x < feed2.size(); x++) {
            this.unorganizedFeed.add(x, (feed2.get(x)));
        }

        organizeTweets(); // Sort the tweets based on timestamp
    }

    // Method to sort tweets based on their timestamps
    private void organizeTweets() {
        feed.clear(); // Clear the user feed
        feed2.clear(); // Clear feed2 (this seems unnecessary)

        int y = 0;
        while (unorganizedFeed.size() != 0) {
            long time;
            long largest = Long.MAX_VALUE;
            int latestIndex = 0;

            // Find the tweet with the smallest timestamp
            for (int x = 0; x < unorganizedFeed.size(); x++) {
                time = unorganizedFeed.getElementAt(x).getTimeStamp();

                if (time < largest) {
                    largest = time;
                    latestIndex = x;
                }
            }

            // Add the tweet with the smallest timestamp to feed
            this.feed.add(y, unorganizedFeed.get(latestIndex));
            this.unorganizedFeed.removeElementAt(latestIndex);
            y++;
        }
    }

    // Method to retrieve the user feed
    public DefaultListModel<Tweet> getFeed() {
        return feed;
    }
}
