public class MessageVisitor implements IdVisitor {
    private int totalMessages = 0;

    public void visitGroup(Group group) {
    }

    public void visitUser(User user) {
        int userMessages = user.getUserTweets().size();

        // Increment the total message count
        totalMessages += userMessages;
    }

    public void setTotalMessages(int count) {
        this.totalMessages = count;
    }

    public int getTotalMessages() {
        return totalMessages;
    }
}
