/*
 * Assignment # : In Class Assignment 1
 * File Name : 801092498_InClass01.zip
 * Name : Srishti Tiwari
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainPart1 {
    /*
     * Question 1:
     * - In this question you will use the Data.users array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - Insert each of the users in a list.
     * - Print out the TOP 10 oldest users.
     * */

    public static void main(String[] args) {

        List<User> userList = new ArrayList<User>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            System.out.println(str);
            String[] userData = str.split(",");
            User user = new User(userData[0], userData[1], Integer.parseInt(userData[2]), userData[3], userData[4], userData[5], userData[6]);
            userList.add(user);
        }

        //Print TOP oldest users

        Collections.sort(userList, new Comparator<User>() {
            public int compare(User user1, User user2) {
                if (user1.getAge() < user2.getAge()) {
                    return 1;
                } else if (user1.getAge() > user2.getAge()) {
                    return -1;
                }
                return 0;
            }
        });
        for (int i = 0; i < 10; i++) {
            System.out.println(userList.get(i).getFirstname() + "" + userList.get(i).getAge());
        }


    }


}