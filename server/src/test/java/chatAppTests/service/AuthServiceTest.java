package chatAppTests.service;

import chatApp.Entities.ConfirmationToken;
import chatApp.Entities.User;
import chatApp.Response.ResponseHandler;
import chatApp.repository.ConfirmationTokenRepository;
import chatApp.repository.UserRepository;
import chatApp.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService = new AuthService();

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    String confirmationToken;
    @Test
    void wrongToken_Confirmation_isFalse() {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
//        assertEquals(authService.confirmation("abcdef"), "new first name for user", "Expected equal name");
    }

    @Test
    void notVerifiedEmail_Login_isFalse(){
        User user = new User();
        final Map<String, Object> responseMap = new HashMap<>();
        user.setFirstName("Test");
        user.setLastName("Testerson");
        user.setEmail("testemail@gmail.com");
        user.setId(123456);
        user.setPassword("testPassword");
        assertEquals(authService.login(user),ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, responseMap), "Cannot login without verifying email");
    }

    @Test
    void nullToken_Confirmation_isNull(){
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        assertNotNull(token);
    }

    @Test
    void createUser() {
    }

    @Test
    void confirmation() {
    }

    @Test
    void loginPageOrErrorPage() {
    }

    @Test
    void login() {
    }

    @Test
    void addTokenToUser() {
    }

    @Test
    void findByToken() {
    }

    @Test
    void isAuth() {
    }
}