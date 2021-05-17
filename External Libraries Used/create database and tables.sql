# create the database required
DROP DATABASE IF EXISTS `issue_tracker_system`;
CREATE DATABASE IF NOT EXISTS `issue_tracker_system`;
USE `issue_tracker_system`;

# create the tables required

CREATE TABLE project
(
	projectID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),

    PRIMARY KEY(projectID)
);

CREATE TABLE user
(
	userID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),

    PRIMARY KEY(userID)
);

CREATE TABLE issue
(
	issueID INT NOT NULL AUTO_INCREMENT,
    projectID INT,
    creatorID INT,
    assigneeID INT,
    title VARCHAR(100),
    description TEXT,
    time TIMESTAMP,
    tag VARCHAR(100),
    priority INT,
    status VARCHAR(100),


    PRIMARY KEY(issueID),
    FOREIGN KEY (projectID) REFERENCES project(projectID),
    FOREIGN KEY (creatorID) REFERENCES user(userID),
    FOREIGN KEY (assigneeID) REFERENCES user(userID)
);

CREATE TABLE comment
(
	commentID INT NOT NULL AUTO_INCREMENT,
    issueID INT,
    userID INT,
    time TIMESTAMP,
    description TEXT,
    reactions TEXT,

    PRIMARY KEY(commentID),
    FOREIGN KEY (issueID) REFERENCES issue(issueID),
    FOREIGN KEY (userID) REFERENCES user(userID)
);



### some initial values
USE `issue_tracker_system`;
INSERT INTO user VALUES(1, "loh", "loh@gmail.com", "LOH");
INSERT INTO user VALUES(2, "yen", "yen@gmail.com", "YEN");
INSERT INTO user VALUES(3, "shen", "shen@gmail.com", "SHEN");
INSERT INTO user VALUES(4, "shawn", "shawn@gmail.com", "SHAWN");
INSERT INTO user VALUES(5, "mentos", "mentos@gmail.com", "MENTOS");
