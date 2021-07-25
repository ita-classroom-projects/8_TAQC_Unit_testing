package com.softserve.edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.softserve.edu.dao.FileTools;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.User;
import com.softserve.edu.service.UserService;

public class ReadFromFileUnitTest {
    private UserService userService;

    @BeforeClass
    public void setUpBeforeClass() {
        List<String> validList = Arrays.asList("Ivan;Ivanov;Sun;Developer", "Perto;Petrov;Sun;Developer");
        //
        FileTools fileTools = Mockito.mock(FileTools.class);
        PowerMockito.when(fileTools.readRows()).thenReturn(validList);
        // TODO Mock userDao
        userService = new UserService("dbTest", fileTools);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAfterClass() {
        UserDao.closeAll();
    }
    
    @DataProvider
    public Object[][] usersData() {
        List<User> expected = new ArrayList<>();
        expected.add(new User("Ivan", "Ivanov", "Sun", "Developer"));
        expected.add(new User("Perto", "Petrov", "Sun", "Developer"));
        return new Object[][] { 
            { expected },
        };
    }

    @Test(dataProvider = "usersData")
    public void checkReadFromFileValid(List<User> expected) {
        // Code
        List<User> actual = userService.readFromFile();
        //System.out.println("actual = " + actual);
        //System.out.println("expected = " + expected);
        Assert.assertEquals(actual, expected);
    }

    //@Test//(dataProvider = "usersInvalidData")
    public void checkReadFromFileInvalid(List<User> expected) {
        // Code 
        // TODO
    }
}
