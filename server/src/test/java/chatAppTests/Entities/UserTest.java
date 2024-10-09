package chatAppTests.Entities;

import chatApp.Entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user = new User();

    @Test
    void setFirstName_nameIsValid_isEquals() {
        user.setFirstName("new first name for user");
        assertEquals(user.getFirstName(), "new first name for user", "Expected equal name");
    }

    @Test
    void setLastName_nameIsValid_isEquals() {
        user.setLastName("new first name for user");
        assertEquals(user.getLastName(), "new first name for user", "Expected equal name");
    }

    @Test
    void setEmail_nameIsValid_isEquals() {
        user.setEmail("new email for user");
        assertEquals(user.getEmail(), "new email for user", "Expected equal name");
    }

    @Test
    void setPassword_nameIsValid_isEquals() {
        user.setPassword("new email for user");
        assertEquals(user.getPassword(), "new email for user", "Expected equal name");
    }

    @Test
    void setId_nameIsValid_isEquals() {
        user.setId(123456);
        assertEquals(user.getId(), 123456, "Expected equal name");
    }

    @Test
    void setIsEnabled_nameIsValid_isEquals() {
        user.setEnabled(true);
        assertEquals(user.isEnabled(), true, "Expected equal name");
    }

    @Test
    void getFirstName() {
    }

    @Test
    void setFirstName() {
    }

    @Test
    void getLastName() {
    }

    @Test
    void setLastName() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void isEnabled() {
    }

    @Test
    void setEnabled() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}