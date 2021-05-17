package system;

import java.util.ArrayList;
import java.util.Arrays;

public class Project {
    private int projectID;
    private String name;
    private Issue[] issues;

    public Project(int projectID, String name, Issue[] issues) {
        this.projectID = projectID;
        this.name = name;
        this.issues = issues;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getName() {
        return name;
    }

    public Issue[] getIssues() {
        return issues;
    }


    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", name='" + name + '\'' +
                ", issues=" + Arrays.toString(issues) +
                '}';
    }
}
