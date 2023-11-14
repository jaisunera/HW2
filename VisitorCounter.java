public class VisitorCounter implements IdVisitor {
    private int userCounter = 0; //Counter for users
    private int groupCounter = 0; //Counter for groups

    public void visitGroup(Group g) {
        setGroupCounter(getGroupCounter() + 1); //Increment the group counter when visiting a group
    }

    public void visitUser(User u) {
        setUserCounter(getUserCounter() + 1); //Increment the user counter when visiting a user
    }

    //Setter method for the user counter
    public void setUserCounter(int count) {
        this.userCounter = count;
    }

    //Getter method for the user counter
    public int getUserCounter() {
        return userCounter;
    }
    
    //Setter method for the group counter
    public void setGroupCounter(int count) {
        this.groupCounter = count;
    }

    //Getter method for the group counter
    public int getGroupCounter() {
        return groupCounter;
    }
}
