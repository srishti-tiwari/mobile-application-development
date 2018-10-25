/*
 * Assignment # : In Class Assignment 1
 * File Name : 801092498_InClass01.zip
 * Name : Srishti Tiwari
 */

import java.util.*;

public class MainPart2 {
    /*
     * Question 2:
     * - In this question you will use the Data.users array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - The goal is to count the number of users living each state.
     * - Print out the list of State, Count order in ascending order by count.
     * */

    public static void main(String[] args) {
        List<User> userList = new ArrayList<User>();
        Map<String, Integer> stateCount = new HashMap<>();
        //example on how to access the Data.users array.
        for (String str : Data.users) {
            System.out.println(str);
            String[] userData = str.split(",");
            User user = new User(userData[0], userData[1], Integer.parseInt(userData[2]), userData[3], userData[4], userData[5], userData[6]);
            userList.add(user);
            if (!stateCount.containsKey(user.getState())) {
                stateCount.put(user.getState(), 1);
            } else {
                stateCount.put(user.getState(), stateCount.get(user.getState()) + 1);
            }
        }
        System.out.println(stateCount);
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(stateCount.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }

        });

        System.out.println(list);
    }
}