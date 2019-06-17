package com.codecool.bfsexample.model;

import com.codecool.bfsexample.NoSuchUserFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BreadthFirst {

    private static final Logger logger = LoggerFactory.getLogger(BreadthFirst.class);

    private List<UserNode> userNodeList;

    public BreadthFirst(List<UserNode> userNodeList) {
        this.userNodeList = userNodeList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int calcMinDist(int user1ID, int user2ID) {
        int distance = 0;
        boolean userFound = false;
        List<UserNode> queue = new ArrayList<>();
        try {
            UserNode userNode1 = findUserNode(user1ID); // Get the first UserNode.
            queue.add(userNode1); // Add the first node to the queue.
            while (!userFound) {
                List<UserNode> checkedUsers = new ArrayList<>();
                UserNode[] userNodesArray = queue.stream().toArray(UserNode[]::new); // To avoid concurrent modification exception.

                for (UserNode userNode : userNodesArray) {
                    if (userNode.getId() == user2ID) {
                        userFound = true;
                        break;
                    } else {
                        checkedUsers.add(userNode); // Add to the checked list.
                        queue.addAll(userNode.getFriends()); // Add friends to the queue.
                    }
                }

                for (UserNode userNode : checkedUsers) { // Delete checked users from the queue.
                    queue.remove(userNode);
                }

                if (userFound)
                    break;

                distance++;
            }
        } catch (NoSuchUserFoundException e) {
            logger.error(e.getMessage());
        }
        return distance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<UserNode> getFriendsOfFriends(int userID, int distance) {
        List<UserNode> friends = new ArrayList<>();
        List<UserNode> queue = new ArrayList<>();
        List<UserNode> checkedUsers = new ArrayList<>();
        try {
            UserNode user = findUserNode(userID); // Get the first UserNode.
            queue.add(user); // Add the first UserNode to the queue.
            checkedUsers.add(user); // Tag the first UserNode as checked.
            int iterations = 0;

            while (iterations < distance) {

                UserNode[] usersToCheck = queue.stream().toArray(UserNode[]::new); // To avoid concurrent modification exception.
                for (UserNode userNode : usersToCheck) {

                    for (UserNode friend : userNode.getFriends()) {
                        if (!checkedUsers.contains(friend)) { // If the user was already checked then don't add it.
                            friends.add(friend);
                        }
                    }

                    checkedUsers.add(userNode);
                    queue.addAll(userNode.getFriends());
                    queue.remove(userNode);
                }

                iterations++;
            }

        } catch (NoSuchUserFoundException e) {
            logger.error(e.getMessage());
        }
        return friends;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<UserNode> shortestPath(int user1ID, int user2ID) {
        List<UserNode> usersList = new ArrayList<>();

        try {
            UserNode user = findUserNode(user1ID);


        } catch (NoSuchUserFoundException e) {
            logger.error(e.getMessage());
        }

        return usersList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private UserNode findUserNode(int userID) throws NoSuchUserFoundException {
        return userNodeList.stream()
                .filter(userNode -> userNode.getId() == userID)
                .findFirst()
                .orElseThrow(() -> new NoSuchUserFoundException("No such UserNode found!"));
    }
}
