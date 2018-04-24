package com.example.teamc.friendfinder;

/**
 * Created by shahkhan on 08/04/2018.
 */

public class myUser {

    String userId;
    String userName;
    int userAge;
    String userGender;

    //Default constructor will have/set the default info for the users such as:
    //(auto generated) id name, age, and gender initialised at creation time
    public myUser(String userId, String userName, int userAge, String userGender) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userGender = userGender;
    }

    //Getters
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getUserGender() {
        return userGender;
    }


}
