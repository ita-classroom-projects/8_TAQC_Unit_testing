package com.softserve.edu;

import java.util.List;

import com.softserve.edu.dao.FileTools;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.User;
import com.softserve.edu.service.UserService;

public class AppUser {
    
    public static void main(String[] args) {
        FileTools fileTools = new FileTools("users.csv");
        UserService userService = new UserService("mydb", fileTools);
        //
        List<User> fileUsers = userService.readFromFile();
        System.out.println("Saved: " + userService.saveToDB(fileUsers));
        //
        List<User> dbUsers = userService.readAllFromDB();
        System.out.println("dbUsers: " + dbUsers);
        //
        System.out.println("\nRemove Duplicate: " + userService.removeDuplicateFromDB());
        dbUsers = userService.readAllFromDB();
        System.out.println("Unique: " + dbUsers);
        System.out.println("\nSorted: " + userService.sortByCompanyAndFirstName(dbUsers));
        //
        System.out.println("\nDevelopers: " + userService.readDevelopersFromDB());
        //
        UserDao.closeAll();
    }
    
}
