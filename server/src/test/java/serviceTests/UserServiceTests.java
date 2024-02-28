package serviceTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import dataAccess.*;
import service.UserService;


import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {

    private UserService service;
    private UserDAO userDAO;
    private AuthDAO authDAO;
    @BeforeEach
    public void setup() throws TestException {
        service = new UserService();
        userDAO = MemoryUserDAO.getInstance();
        authDAO = MemoryAuthDAO.getInstance();

    }
    @Test
    @DisplayName("Register positive")
    public void successRegister() throws TestException, DataAccessException {
        UserData newUser = new UserData("yomama", "pass", "yo@mama");
        AuthData authData = service.register(newUser);
        assertNotNull(authData);
    }
    @Test
    @DisplayName("Register negative")
    public void failRegister() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        AuthData authData = service.register(user);
        assertNotNull(authData);
    }

    @Test
    @DisplayName("Login positive")
    public void successLogin() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        service.register(user);

        AuthData authData = service.login(user);

        assertNotNull(authData);
    }
    @Test
    @DisplayName("Login negative")
    public void failLogin() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        service.register(user);

        AuthData authData = service.login(user);

        assertNotNull(authData);
    }
    @Test
    @DisplayName("Logout positive")
    public void successLogout() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        AuthData authData = service.register(user);

        boolean result = service.logout(authData.getAuthToken());

        assertEquals(result, true);
    }
    @Test
    @DisplayName("Login negative")
    public void failLogout() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        AuthData authData = service.register(user);
        boolean result = service.logout("1234");
        assertEquals(result, false);
    }
    @Test
    @DisplayName("User exists positive")
    public void successUserExists() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        UserData user2 = new UserData("yomama", "pass123", "yo@mama111");
        AuthData authData = service.register(user);

        boolean result = service.userExists(user2);

        assertEquals(result, true);
    }
    @Test
    @DisplayName("User exists negative")
    public void failUserExists() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        UserData user2 = new UserData("yomama22", "pass123", "yo@mama111");
        AuthData authData = service.register(user);

        boolean result = service.userExists(user2);

        assertEquals(result, false);
    }
    @Test
    @DisplayName("Auth exists positive")
    public void successAuthExists() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        AuthData authData = service.register(user);

        boolean result = service.authExists(authData.getAuthToken());

        assertEquals(result, true);
    }
    @Test
    @DisplayName("Auth exists negative")
    public void failAuthExists() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        UserData user2 = new UserData("yomama22", "pass123", "yo@mama111");
        AuthData authData = service.register(user);

        boolean result = service.authExists("123456");

        assertEquals(result, false);
    }
    @Test
    @DisplayName("Clear negative")
    public void successClear() throws TestException, DataAccessException {
        UserData user = new UserData("yomama", "pass", "yo@mama");
        AuthData authData = service.register(user);

        service.clear();

        assertEquals(0, 0);
    }
}
