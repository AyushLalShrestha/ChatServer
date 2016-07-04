package com.leapfrog.ChatServer.dao.impl;

import com.leapfrog.ChatServer.dao.UserDAO;
import com.leapfrog.ChatServer.entity.User;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public UserDAOImpl() {
    }

    @Override
    public User login(String userName, String password) {
        for (User u : getAll()) {
            if (u.getUserName().equalsIgnoreCase(userName) && u.getPassword().equalsIgnoreCase(password)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "Aadesh", "pandey", true));
        userList.add(new User(2, "Abhash", "pradhananga", true));
        userList.add(new User(3, "Abishek", "pandey", true));
        userList.add(new User(4, "Adarsh", "shrestha", true));
        userList.add(new User(5, "Ayush", "shrestha", true));
        userList.add(new User(6, "Nischal", "khanal", true));
        userList.add(new User(7, "Manish", "shakya", true));
        userList.add(new User(8, "Prince", "Thakuri", true));
        userList.add(new User(9, "Prajwol", "Gautam", true));
        

        return userList;

    }

}
