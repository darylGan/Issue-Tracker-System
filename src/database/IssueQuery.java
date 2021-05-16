package database;

import system.Comment;
import system.Issue;
import system.User;

import java.sql.*;

public class IssueQuery extends Query{
    /**
     * return array of issues based on 'ResultSet rs'
     */
    private static Issue[] constructIssues( ResultSet rs) throws SQLException, ClassNotFoundException {
        int L = size( rs );
        Issue[] issues = new Issue[L];

        for (int i = 0; i < issues.length; i++) {
            rs.next();

            // issueID, projectID, creatorID, assigneeID, title, description, time, tag, priority, status
            int issueID         = rs.getInt("issueID");
            int projectID       = rs.getInt("projectID");  // belongs to which project
            User creator        = UserQuery.getUser( rs.getInt("creatorID") );
            User assignee       = UserQuery.getUser( rs.getInt("assigneeID") );
            String title        = rs.getString("title");
            String description  = rs.getString("description");
            Timestamp time      = rs.getTimestamp("time");
            String tag          = rs.getString("tag");
            int priority        = rs.getInt("priority");
            String status       = rs.getString("status");
            Comment[] comments  = CommentQuery.getComments( issueID );//////////////////////////////////////////////////////

            issues[i] = new Issue(issueID, projectID, creator, assignee, title, description, time, tag, priority, status, comments);
        }

        return issues;
    }
    /**
     * return an array of all issues that belong to a project (based on projectIDToSearch)
     */
    public static Issue[] getIssues( int projectIDToSearch) throws SQLException, ClassNotFoundException{
        String query =  "SELECT *\n" +
                        "FROM issue\n" +
                        "WHERE projectID = " + projectIDToSearch + " ;";
        return constructIssues( constructResultSet( query ) );
    }
    /**
     * return an issue (based on issueIDToSearch)
     */
    public static Issue getIssue( int issueIDToSearch) throws SQLException, ClassNotFoundException{
        String query =
                "SELECT *\n" +
                "FROM issue\n" +
                "WHERE issueID = " + issueIDToSearch + " ;";
        return constructIssues( constructResultSet( query ) )[0]; // 0 bcuz only 1 element in array
    }

    /**
     * return an array of all issues that belong to a project sorted by 'priority'
     */
    public static Issue[] getIssues_SortedBy_Priority( int projectIDToSearch,  boolean asc_or_desc) throws SQLException, ClassNotFoundException {
        String order = (asc_or_desc) ? "ASC" : "DESC";
        String query =
                "SELECT *\n" +
                "FROM issue\n" +
                "WHERE projectID = " + projectIDToSearch + " \n" +
                "ORDER BY priority " + order + " ;";
        return constructIssues( constructResultSet( query ) );
    }

    /**
     * return an array of all issues that belong to a project sorted by 'time'
     */
    public static Issue[] getIssues_SortedBy_Time(int projectIDToSearch, boolean asc_or_desc) throws SQLException, ClassNotFoundException{
        String order = (asc_or_desc) ? "ASC" : "DESC";
        String query =
                "SELECT *\n" +
                "FROM issue\n" +
                "WHERE projectID = "+ projectIDToSearch +" \n" +
                "ORDER BY time "+ order +" ;";
        return constructIssues( constructResultSet( query ) );
    }

    /**
     * return an array of all issues that belong to a project filtered by 'status'
     */
    public static Issue[] getIssues_FilterBy_Status(int projectIDToSearch, String statusToFilter) throws SQLException, ClassNotFoundException{
        String query =
                "SELECT *\n" +
                "FROM issue\n" +
                "WHERE \n" +
                "\t  projectID = "+projectIDToSearch+" AND \n" +
                "    status = \""+statusToFilter+"\";";
        return constructIssues( constructResultSet( query ) );
    }

    /**
     * return an array of all issues that belong to a project filtered by 'tag'
     */
    public static Issue[] getIssues_FilterBy_Tag(int projectIDToSearch, String tagToFilter) throws SQLException, ClassNotFoundException{
        String query =
                "SELECT *\n" +
                "FROM issue\n" +
                "WHERE \n" +
                "\t  projectID = "+projectIDToSearch+" AND \n" +
                "    tag = \""+tagToFilter+"\";";
        return constructIssues( constructResultSet( query ) );
    }

    /**
     * return an array of all issues that belong to a project
     *      where (title / description / comments) matches keyword entered by user
     */
    public static Issue[] getIssues_SearchBy_Key_Word( int selected_project_id, String keyWord) throws SQLException, ClassNotFoundException {
        String query =
                "SELECT * \n" +
                "FROM issue\n" +
                "WHERE \n" +
                "    projectID = "+ selected_project_id +" AND\n" +
                "    ( title REGEXP \""+keyWord+"\" OR \n" +
                "      description REGEXP \""+keyWord+"\" OR \n" +
                "      issueID = ANY (SELECT issueID\n" +
                "                     FROM comment\n" +
                "                     WHERE description REGEXP \""+keyWord+"\")  );";
        return constructIssues( constructResultSet( query ) );
    }

    /**
     * insert a new record of issue into database
     */
    public static void insertNewIssue(int issueID, int projectID, int creatorID, int assigneeID, String title, String description, Timestamp time, String tag, int priority, String status) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO issue VALUES(?,?,?,?,?,?,?,?,?,?)";

        Connection con = getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, issueID);
        pst.setInt(2, projectID);
        pst.setInt(3, creatorID);
        pst.setInt(4, assigneeID);
        pst.setString(5, title);
        pst.setString(6, description);
        pst.setTimestamp(7, time);
        pst.setString(8, tag);
        pst.setInt(9, priority);
        pst.setString(10, status);
        pst.executeUpdate();

        pst.close();
        con.close();
    }
}