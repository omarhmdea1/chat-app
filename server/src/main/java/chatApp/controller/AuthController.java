package chatApp.controller;


import chatApp.Entities.User;
import chatApp.Response.ResponseHandler;
import chatApp.Utils.Role;
import chatApp.Utils.Validator;
import chatApp.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    private final Map<String, Object> errorsMap = new HashMap<>();
    private static Logger logger = LogManager.getLogger(AuthController.class.getName());



    /**
     * end point that responsible to create user if he not exists
     * @param req -> User
     * @return if there are errors method returns ResponseEntity with relevant status code,
     *         else returns ResponseEntity with relevant status data,
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody User req) {
        logger.info("registering with email " +req.getEmail());
        Optional<Map<String, String>> validationErrors = Validator.validateRegister(req);
        if(validationErrors.isPresent()) {
            return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, validationErrors);
        }
        return authService.createUser(req);
    }

    /**
     * end point that responsible for confirm account
     * @param confirmationToken
     * @return
     */
    @RequestMapping(value="/confirm-account", method= RequestMethod.GET)
    public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
        logger.info("verifying user email with confirmation token " + confirmationToken);
        return authService.confirmation(confirmationToken);
    }


    /**
     * end point that responsible to login
     * @param req -> User
     * @return if there are errors method returns ResponseEntity with relevant status code,
     *         else returns ResponseEntity with relevant status data,
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<Object> logIn(@RequestBody User req) {
        logger.info("logging into " + req.getEmail());
        Optional<Map<String, String>> errors = Validator.validateLogin(req);
        if(errors.isPresent()) {
            return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, errors);
        }

        return authService.login(req);
    }

    /**
     * end point that responsible for guset login
     * @param req -> User
     * @return if there are errors method returns ResponseEntity with relevant status code,
     *         else returns ResponseEntity with relevant status data,
     */
    @RequestMapping(value = "loginAsGuest", method = RequestMethod.POST)
    public ResponseEntity<Object> logInAsGuest(@RequestBody User req) {
        logger.info(req .getNikeName() + " guest as logged in");
        Optional<Map<String, String>> errors = Validator.validateLoginAsGuest(req.getNikeName());
        if(errors.isPresent()) {
            return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, errors);
        }
        return authService.loginAsGuest(req);
    }

    /**
     * end point that responsible for logout
     * @param token
     * @return if there are errors method returns ResponseEntity with relevant status code,
     *         else returns ResponseEntity with relevant status data,
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity<Object> logout(@RequestHeader String token) {
        logger.info("trying to logout using token " + token);
        return authService.logout(token);
    }

}
