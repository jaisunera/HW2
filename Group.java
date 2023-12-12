import java.util.ArrayList;
import java.util.List;

public class Group implements Id {
  private String groupID; //ID of the group
  private List<Id> groupMembers = new ArrayList<>(); //List of group members
  private long creationTime; //Add creationTime

  //Constructor to initialize a Group object with a specific ID
  public Group(String id) {
    this.groupID = id;
  }

  //Returns a string representation of the Group object (groupID)
  public String toString() {
    return groupID;
  }

  //Returns the ID of the group
  public String getId() {
    return groupID;
  }

  //Sets the ID of the group
  public void setId(String id) {
    this.groupID = id;
  }

  //Returns the list of entities (group members) in the group
  public List<Id> getEntities() {
    return groupMembers;
  }

  //Gets the entity (group member) at a specific index in the group
  public Id getId(int index) {
    return groupMembers.get(index);
  }

  //Finds the index of a specific object in the group, returns -1 if not found
  public int findIdIndex(Object object) {
    return groupMembers.indexOf(object);
  }

  //Adds an entity (group member) to the group
  public void addId(Id x) {
    groupMembers.add(x);
  }

  //Visitor pattern implementation: Accepts a visitor and performs operations on the group and its members
  public void accept(IdVisitor visitor) {
    visitor.visitGroup(this); //Visit the group
    for (Id e : groupMembers) { //Visit each group member
      e.accept(visitor);
    }
  }

  //Getter for creationTime
  public long getCreationTime() {
    return creationTime;
  }
}
