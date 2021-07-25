package com.softserve.edu;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.softserve.edu.dao.UserDao;
import com.softserve.edu.service.UserService;

public class GetNodesUnitTest {
    private UserService userService;

    @BeforeClass
    public void setUpBeforeClass() {
        System.out.println("@BeforeClass");
        // TODO Mock userDao
        userService = new UserService("dbtest", null);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAfterClass() {
        UserDao.closeAll();
    }
    
    @DataProvider
    public Object[][] rowsData() {
        System.out.println("@DataProvider");
        List<String> expected = Arrays.asList("Ivan", "Ivanov", "Sun", "Developer");
        return new Object[][] { 
            { "Ivan;Ivanov;Sun;Developer", expected },
            };
    }

    @Test(dataProvider = "rowsData")
    public void checkGetNodesValid(String row, List<String> expected) {
        // Code
        List<String> actual = userService.getNodes(row);
        Assert.assertEquals(actual, expected);
    }
    
    //@Test(dataProvider = "rowsInvalidData")
    public void checkGetNodesInvalidRow(String row, List<String> expected) {
        // Code 
        // TODO
    }
}
