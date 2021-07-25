package com.softserve.edu.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softserve.edu.dao.FileTools;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.User;

public class UserService {
    private final String CSV_SPLIT = ";";
    //
    private UserDao userDao;
    private FileTools fileTools;

    public UserService(String dbName, FileTools fileTools) {
        this.userDao = new UserDao(dbName);
        this.fileTools = fileTools;
        //this.fileTools = new FileTools(fileName);
    }

    public List<String> getNodes(String row) {
        List<String> nodes = new ArrayList<>();
        // Split row to List<String>;
        //
        // Code
        for (String node : row.split(CSV_SPLIT)) {
            nodes.add(node);
        }
        //
        return nodes;
    }

    public User createFromRow(String row) {
        User user = null;
        // Call getNodes(...) method; create User;
        //
        // Code
        List<String> nodes = getNodes(row);
        if (nodes.size() == 4) {
            user = new User(nodes.get(0), nodes.get(1), nodes.get(2), nodes.get(3));
        }
        //
        return user;
    }

    public List<User> readFromFile() {
        List<User> users = new ArrayList<>();
        // Call fileTools.readRows(); createFromRow(...) methods;
        //
        // Code
        for (String row : fileTools.readRows()) {
            users.add(createFromRow(row));
        }
        //
        return users;
    }

    public int saveToDB(List<User> users) {
        int insertSize = 0;
        // Use userDao;
        //
        // Code
        User titleUser = new User("firstName", "lastName", "companyName", "roleName");
        for (User user : users) {
            if (!user.equals(titleUser)) {
                userDao.add(user);
                insertSize++;
            }
        }
        //
        return insertSize;
    }

    public List<User> readAllFromDB() {
        List<User> users = null;
        // Use userDao;
        //
        // Code
        users = userDao.getAll();
        //
        return users;
    }

    public List<User> readDevelopersFromDB() {
        List<User> users = null;
        // Use userDao;
        //
        // Code
        users = userDao.getByRole("Developer");
        //
        return users;
    }

    public int removeDuplicateFromDB() {
        int removeSize = 0;
        // Call readAllFromDB(); userDao
        // Code
        Set<User> uniques = new HashSet<>();
        for (User user : readAllFromDB()) {
            if (!uniques.add(user)) {
                userDao.delete(user);
                removeSize++;
            }
        }
        //
        return removeSize;
    }

    public List<User> sortByCompanyAndFirstName(List<User> users) {
        users.sort(
         Comparator.<User, User>comparing(user -> user,
             (user1, user2) -> user1.getCompanyName().compareTo(user2.getCompanyName()))
             .thenComparing((user1, user2) -> user1.getFirstName().compareTo(user2.getFirstName())));
        return users;
    }
    
}
