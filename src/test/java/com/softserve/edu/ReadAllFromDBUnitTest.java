package com.softserve.edu;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.User;
import com.softserve.edu.service.UserService;

@PrepareForTest(UserService.class)
public class ReadAllFromDBUnitTest {
    private UserService userService;
    //
    private List<User> expected;

    @ObjectFactory
    public IObjectFactory setObjectFactory() {
        return new PowerMockObjectFactory();
    }
    
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        expected = new ArrayList<>();
        expected.add(new User("Ivan", "Ivanov", "Sun", "Developer"));
        expected.add(new User("Perto", "Petrov", "Sun", "Developer"));
        //
        UserDao userDao = Mockito.mock(UserDao.class);
        PowerMockito.when(userDao.getAll()).thenReturn(expected);
        //PowerMockito.whenNew(UserDao.class).withArguments(Mockito.anyString()).thenReturn(userDao);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        //
        userService = new UserService("dbTest", null);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAfterClass() {
        UserDao.closeAll();
    }
    
    @DataProvider
    public Object[][] usersData() {
        return new Object[][] { 
            { expected },
        };
    }

    @Test(dataProvider = "usersData")
    public void checkReadAllFromDBValid(List<User> expected) {
        // Code
        List<User> actual = userService.readAllFromDB();
        //System.out.println("actual = " + actual);
        //System.out.println("expectedUsers = " + expected);
        Assert.assertEquals(actual, expected);
    }

    //@Test//(dataProvider = "usersInvalidData")
    public void checkReadAllFromDBInvalid(List<User> expected) {
        // Code 
        // TODO
    }
}
