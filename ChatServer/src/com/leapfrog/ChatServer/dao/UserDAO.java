
package com.leapfrog.ChatServer.dao;

import com.leapfrog.ChatServer.entity.User;
import java.util.List;

public interface UserDAO {
    
    User login(String userName, String password); // Returns user u
    
    List<User> getAll(); // Returns ArrayList of registered users
    
}
