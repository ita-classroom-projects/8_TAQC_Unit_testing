package com.softserve.edu;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.User;
import com.softserve.edu.service.UserService;

public class CreateFromRowUnitTest {
    private UserService userService;
    //
    private String validRow;
    private String otherRow;

    @BeforeClass
    public void setUpBeforeClass() {
        validRow = "Ivan;Ivanov;Sun;Developer";
        otherRow = "Ivan1;Ivanov1;Sun1;Developer1";
        List<String> validList = Arrays.asList("Ivan", "Ivanov", "Sun", "Developer");
        // TODO Mock userDao
        userService = Mockito.spy(new UserService("dbTest", null));
        PowerMockito.when(userService.getNodes(otherRow)).thenReturn(validList);
    }
    
    @AfterClass(alwaysRun = true)
    public void tearDownAfterClass() {
        UserDao.closeAll();
    }

    @DataProvider
    public Object[][] rowsData() {
        User expected = new User("Ivan", "Ivanov", "Sun", "Developer");
        return new Object[][] {
                { otherRow, expected },
            };
    }

    @Test(dataProvider = "rowsData")
    public void checkCreateFromRowValid(String row, User expected) {
        // Code
        User actual = userService.createFromRow(row);
        Assert.assertEquals(actual, expected);
    }

    //@Test//(dataProvider = "rowsInvalidData")
    public void checkCreateFromRowInvalidRow(String row, User expected) {
        // Code 
        // TODO
    }
}
