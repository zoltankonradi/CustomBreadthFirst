package com.codecool.bfsexample;

import com.codecool.bfsexample.model.BreadthFirst;
import com.codecool.bfsexample.model.UserNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BFSExample {

    private static final Logger logger = LoggerFactory.getLogger(BFSExample.class);


    private static void populateDB() {

        RandomDataGenerator generator = new RandomDataGenerator();
        List<UserNode> users = generator.generate();

        GraphPlotter graphPlotter = new GraphPlotter(users);
        
        logger.info("Done!");

        BreadthFirst breadthFirst = new BreadthFirst(users);
        logger.info("/////// Minimum Distance ///////");
        logger.info("Distance: " + breadthFirst.calcMinDist(111,10));

        List<UserNode> userNodeList = breadthFirst.getFriendsOfFriends(68,2);
        logger.info("/////// Friends of Friends ///////");
        userNodeList.forEach(userNode -> logger.info(userNode.toString()));
    }

    public static void main(String[] args) {
        populateDB();
    }
}
