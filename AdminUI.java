import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class AdminUI implements ActionListener, TreeSelectionListener {
    private static AdminUI instance = null;


    private JFrame frame; //Main window


    private JTree treeView;


    private JTextField userIDField;
    private JTextField groupIDField;


    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton userViewButton;
    private JButton validateIDButton;


    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messageTotalButton;
    private JButton positiveTotalButton;


    private String newUserID;
    private String newGroupID;


    private DefaultMutableTreeNode userTreeNode = null;
    private DefaultMutableTreeNode groupTreeNode = null;


    private DefaultMutableTreeNode selectedGroup;


    private DefaultMutableTreeNode rootNode;


    private DefaultTreeModel treeModel;


    private Group rootGroup = new Group("Root"); //Root node


    //List of groups and users that exist
    private ArrayList<Group> groupList = new ArrayList<Group>();
    ArrayList<User> userList = new ArrayList<User>();


    protected AdminUI() {
        frame = new JFrame("Admin Panel"); //Title of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Allow user to close window
        frame.setSize(800, 500); //Size of the window
        frame.setLayout(null); //No layout


        frame.getContentPane().setBackground(Color.decode("#CDE990")); //Background color


        //User and group text fields
        userIDField = createAndConfigureTextField("User ID", 250, 25);
        groupIDField = createAndConfigureTextField("Group ID", 250, 100);


        //Buttons to add user and groups + open user view
        createAndConfigureUserGroupButtons();


        //Buttons to show user total, group total, messages total, and positive percentage
        createAndConfigureShowButtons();


        //Adds root node of the tree
        groupList.add(rootGroup);
        rootNode = new DefaultMutableTreeNode(rootGroup);
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new TreeListener());


        //Creating the tree
        createAndConfigureTree();


        //Adding components to the frame
        frame.add(treeView);


        frame.add(userIDField);
        frame.add(groupIDField);
        frame.add(addUserButton);
        frame.add(addGroupButton);
        frame.add(userViewButton);
        frame.add(validateIDButton);


        frame.add(userTotalButton);
        frame.add(groupTotalButton);
        frame.add(messageTotalButton);
        frame.add(positiveTotalButton);


        frame.setVisible(true); //Set frame visible
    }


    //Singleton pattern
    public static AdminUI getInstance() {
        if(instance == null) {
            instance = new AdminUI();
        }
        return instance;
    }


    //Method to create tree and modify it
    private void createAndConfigureTree() {
        treeView = new JTree(treeModel);
        treeView.setBounds(25, 25, 200, 400);
        treeView.setEditable(true);
        treeView.setFocusable(false);
        treeView.setShowsRootHandles(true);
        treeView.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeView.addTreeSelectionListener(this);
        treeView.setBackground(Color.decode("#FFD4D4"));
        treeView.setCellRenderer(new CustomTreeCellRenderer());
    }


    //Method to set background of nodes
    private class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                    boolean selected, boolean expanded,
                                                    boolean leaf, int row, boolean hasFocus) {
            Component component = super.getTreeCellRendererComponent(tree, value,
                    selected, expanded, leaf, row, hasFocus);


            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setBackground(Color.decode("#FFD4D4"));
                label.setOpaque(true);
            }

            return component;
        }
    }


    //Method to create add user and group buttons + user view button and position them
    private void createAndConfigureUserGroupButtons() {
        addUserButton = createAndConfigureButton("Add User", 500, 25, 200);
        addGroupButton = createAndConfigureButton("Add Group",500, 100, 200);
        userViewButton = createAndConfigureButton("Open User View", 250, 200, 200);
        validateIDButton = createAndConfigureButton("Valid ID", 500, 200, 200);
    }


    //Method to create show user total, group total, messages total, and positive percentage buttons and position them
    private void createAndConfigureShowButtons() {
        userTotalButton = createAndConfigureButton("Show User Total", 250, 300, 200);
        groupTotalButton = createAndConfigureButton("Show Group Total", 500, 300, 200);
        messageTotalButton = createAndConfigureButton("Show Messages Total", 250, 375, 200);
        positiveTotalButton = createAndConfigureButton("Show Positive Percentage", 500, 375, 200);
    }
   
    //Preferred look for userIDField and groupIDField
    private JTextField createAndConfigureTextField(String text, int x, int y) {
        JTextField textField = new JTextField(text);
        textField.setBounds(x, y, 200, 50); //Dimension of the text fields
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
   
    //Method to update the tree
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeView.getLastSelectedPathComponent(); //Retreives last node in the tree


        //If node is not null and not a leaf, set selectedGroup to the currently non-leaf node
        if (node != null && !node.isLeaf()) {
            selectedGroup = node;
        }
    }


    //Method to add an action listener to all the buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addUserButton) {
            addUser();
        } else if (e.getSource() == addGroupButton) {
            addGroup();
        } else if (e.getSource() == userViewButton) {
            openUserPanel();
        } else if (e.getSource() == groupTotalButton) {
            showGroupTotal();
        } else if (e.getSource() == userTotalButton) {
            showUserTotal();
        } else if (e.getSource() == messageTotalButton) {
            showMessageTotal();
        } else if (e.getSource() == positiveTotalButton) {
            showPositivePercentage();
        } else if (e.getSource() == validateIDButton) {
            validateID();
        }
    }

    //Method to validate ID
    private void validateID() {
        String idToValidate = userIDField.getText().trim(); //Get ID from the text field and remove leading/trailing spaces

        //Check for duplicate IDs and spaces within the ID
        boolean isValid = isIDValid(idToValidate);

        //Show dialog message based on validation result
        if (isValid) {
            showMessageDialog("ID Validation", "The ID '" + idToValidate + "' is valid.");
        } else {
            showMessageDialog("ID Validation", "The ID '" + idToValidate + "' is not valid.");
        }
    }

    //Method to check if the ID is valid based on criteria: no duplicate IDs and no spaces within the ID
    private boolean isIDValid(String idToValidate) {
        //Check for no spaces within the ID
        if (idToValidate.contains(" ")) {
            return false;
        }

        //Check for duplicate IDs
        for (Group group : groupList) {
            //Check in group IDs
            if (group.getId().equals(idToValidate)) {
                return false;
            }
            //Check in user IDs
            for (User user : userList) {
                if (user.getId().equals(idToValidate)) {
                    return false;
                }
            }
        }

        return true; //ID is valid if no duplicates and no spaces
    }

    //Method to add a new user to the tree structure
    private void addUser() {
        newUserID = userIDField.getText(); //Get user's ID from userIDField
        userIDField.setText(""); //Clear userIDField


        //Create new user object with the userID
        User newUser = new User(newUserID);
        userList.add(newUser); //Add user to the user list
        userTreeNode = new DefaultMutableTreeNode(newUser); //Create user node in tree


        //Check if the selected node is a group node, add user under group node
        DefaultMutableTreeNode groupNode = getSelectedNode();
        if (groupNode != null && groupNode.getUserObject().getClass().equals(rootGroup.getClass())) {
            insertNodeIntoTree(userTreeNode, groupNode);
            addToGroup(newUser, groupNode);
        }
    }


    //Method to add a new group to the tree structure
    private void addGroup() {
        newGroupID = groupIDField.getText(); //Get group's ID from groupIDField
        groupIDField.setText(""); //Clear groupIDField


        //Create new group object with groupID
        Group newGroup = new Group(newGroupID);
        groupList.add(newGroup); //Add group to the group list
        groupTreeNode = new DefaultMutableTreeNode(newGroup); //Create group node in the tree


        //Check if the selected node is a group node, add group under group node
        DefaultMutableTreeNode groupNode = getSelectedNode();
        if (groupNode != null && groupNode.getUserObject().getClass().equals(rootGroup.getClass())) {
            insertNodeIntoTree(groupTreeNode, groupNode);
            addToGroup(newGroup, groupNode);
        }
    }


    //Method to get the current selected node in the tree
    private DefaultMutableTreeNode getSelectedNode() {
        TreePath parentPath = treeView.getSelectionPath(); //Retreive tree path of the node
        //Return selected node if it exists, otherwise, return root node
        return (parentPath == null) ? rootNode : (DefaultMutableTreeNode) parentPath.getLastPathComponent();
    }


    //Method to insert a new node into the tree
    private void insertNodeIntoTree(DefaultMutableTreeNode childNode, DefaultMutableTreeNode groupNode) {
        treeModel.insertNodeInto(childNode, groupNode, groupNode.getChildCount()); //Inset child node to tree under specified group node
        treeView.scrollPathToVisible(new TreePath(childNode.getPath())); //Scroll the tree view to make the newly added node visible
    }


    //Method to add user or group inot a group node in the tree
    private void addToGroup(Id id, DefaultMutableTreeNode groupNode) {
        //If group node is the root, add new node to root, else find the selected group and add the node to that group
        if (groupNode == rootNode) {
            rootGroup.addId(id);
        } else {
            int index = groupList.indexOf(groupNode.getUserObject());
            groupList.get(index).addId(id);
        }
    }


    //Method to open user UI
    private void openUserPanel() {
        TreePath currentPath = treeView.getSelectionPath(); //Get the path of the node
        //If node is currently in the tree, retreive selected node and group node
        if (currentPath != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentPath.getLastPathComponent();
            DefaultMutableTreeNode groupNode = (DefaultMutableTreeNode) currentNode.getParent();


            //If the selected node is a user, find the index of the group and user, then get user object from group, and open user view for that user
            if (currentNode.getUserObject().getClass().equals(rootGroup.getClass()) == false) {
                int index1 = groupList.indexOf(groupNode.getUserObject());
                int index2 = groupList.get(index1).findIdIndex(currentNode.getUserObject());
                User user = (User) groupList.get(index1).getId(index2);
                UserUI uPanel = new UserUI(user, userList);
            }
        }
    }


    //Method to display the total number of groups
    private void showGroupTotal() {
        VisitorCounter visitor= new VisitorCounter(); //Count groups
        rootGroup.accept(visitor); //Traverse through root group and count the # of groups
        String message = "Total Number of Groups: " + visitor.getGroupCounter(); //Retreive count and create message
        showMessageDialog("Group Total", message); //Dialog box display the total number of groups
    }


    //Method to display the total number of users
    private void showUserTotal() {
        VisitorCounter visitor = new VisitorCounter(); //Count users
        rootGroup.accept(visitor); //Traverse through root group and count # of users
        String message = "Total Number of Users: " + visitor.getUserCounter(); //Retreive count and create message
        showMessageDialog("User Total", message); //Dialog box display the total number of users
    }


    //Method to display total number of messages
    private void showMessageTotal() {
        MessageVisitor visitor= new MessageVisitor(); //Count messages
        rootGroup.accept(visitor); //Traverse through root group and count # of messages
        String message = "Total Number of Messages: " + visitor.getTotalMessages(); //Retreive count and create message
        showMessageDialog("Message Total", message); //Dialog box display the total number of messages
    }


    //Method to display percentage of positive messages
    private void showPositivePercentage() {
        MessageVisitor visitor= new MessageVisitor(); //Count messages
        rootGroup.accept(visitor); //Traverse through root group and count # of messages
        int messagesTotal = visitor.getTotalMessages(); //Get the total number of messages from the visitor


        //PositivityVisitor oject to count positive messages
        PositiveVisitor visitor2= new PositiveVisitor();
        rootGroup.accept(visitor2); //Traverse through root group and count # of positive messages
        int positivityTotal = visitor2.getPositiveCounter(); //Get the total number of positive messages from the visitor


        //If no messages, show percentage as 0; else calculate % and print dialog box displaying positive message percentage
        if (messagesTotal == 0) {
            showMessageDialog("Positive Percentage", "Positive Message Percentage: 0.0%");
        } else {
            double positivePercent = ((double) positivityTotal / messagesTotal) * 100;
            String percentage = String.format("%.1f", positivePercent);
            showMessageDialog("Positive Percentage", "Positive Message Percentage: " + percentage + "%");
        }
    }


    //Method to display a message in dialog box
    private void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
 
}
