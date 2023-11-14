import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class UserFeed extends Subject {
    private DefaultListModel<Tweet> feed = new DefaultListModel<>();

    public void addTweet(Tweet newTweet) {
        feed.addElement(newTweet);
        notifyObservers();
    }

    public void setTweets(ArrayList<Tweet> f) {
        feed.clear();
        for (Tweet tweet : f) {
            feed.addElement(tweet);
        }
        notifyObservers();
    }

    public DefaultListModel<Tweet> getTweets() {
        return feed;
    }
}
