/*
 * Assignment # : In Class Assignment 1
 * File Name : 801092498_InClass01.zip
 * Name : Srishti Tiwari
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainPart3 {

    /*
     * Question 3:
     * - In this question you will use the Data.users and Data.otherUsers array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - The goal is to print out the users that are exist in both the Data.users and Data.otherUsers.
     * Two users are equal if all their attributes are equal.
     * - Print out the list of users which exist in both Data.users and Data.otherUsers.
     * */


    public static void main(String[] args) {
        Set<User> userHashSet = new HashSet<User>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            //System.out.println(str);
            String[] userData = str.split(",");
            User user = new User(userData[0], userData[1], Integer.parseInt(userData[2]), userData[3], userData[4], userData[5], userData[6]);
            userHashSet.add(user);
        }
        for (String str : Data.otherUsers) {
            //System.out.println(str);
            String[] userData = str.split(",");
            User user = new User(userData[0], userData[1], Integer.parseInt(userData[2]), userData[3], userData[4], userData[5], userData[6]);
            if (!userHashSet.contains(user)) {
                userHashSet.add(user);
            }
        }

        System.out.println(userHashSet);
    }
}
