// User and group IDs
public interface Id {
    String getId(); //Retrieves ID associated with object

    void setId(String id); //Sets the ID for the object

    void accept(IdVisitor visitor); //Accepts a visitor implementing the IdVisitor interface
}
