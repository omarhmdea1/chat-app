package chatApp.controller;

import chatApp.Entities.User;
import chatApp.Response.ResponseHandler;
import chatApp.Utils.Role;
import chatApp.Utils.Validator;
import chatApp.service.AuthService;
import chatApp.service.GroupMembersService;
import chatApp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    private final Map<String, Object> responseMap = new HashMap<>();
    private static Logger logger = LogManager.getLogger(UserController.class.getName());


    /**
     * end point that responsible for updating user
     *
     * @param token
     * @param fields -> "field":"updated value"...
     * @return updated user
     */
    @RequestMapping(value = "update", method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateFields(@RequestHeader String token, @RequestBody Map<String, String> fields) {
        responseMap.clear();

        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()) {
            logger.info("updating field for user  " + user.get().getFullName());
            Optional<Map<String, String>> validationErrors = Validator.validateFields(fields);
            if(validationErrors.isPresent()) {
                return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, validationErrors);
            }
            fields.forEach((key, value) -> {

                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user.get(), value);
            });
            User updatedUser = userService.update(user.get());
            return ResponseHandler.generateResponse(true, HttpStatus.OK, updatedUser);
        }

        responseMap.put("error", "invalid token");
        logger.error("tried to update field with invalid token");
        return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, responseMap);
    }


    /**
     * end point that responsible for searching users by query
     *
     * @param token
     * @param query
     * @return search results
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@RequestHeader String token, @RequestParam("query") String query) {
        responseMap.clear();

        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()) {
            logger.info("searching users starts with " + query);
            Set<User> byQuery = userService.findByQuery(query);
            return ResponseHandler.generateResponse(true, HttpStatus.OK, byQuery);
        }
        responseMap.put("error", "invalid token");
        logger.error("token invalid");
        return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, responseMap);
    }

    /**
     * end point that responsible for mute user by Admin in Main chat
     *
     * @param token
     * @param id
     * @return user after changing muted status
     */
    @RequestMapping(value = "mute/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> mutedUser(@RequestHeader String token, @PathVariable int id) {
        responseMap.clear();

        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()) {
            logger.info("trying to mute user " + user.get().getFullName());
            if(user.get().getRole().equals(Role.ADMIN)) {
                Optional<User> userById = userService.getUserById(id);
                if(userById.isPresent()) {
                    userById.get().setMuted(!userById.get().isMuted());
                    User updatedUser = userService.update(userById.get());
                    return ResponseHandler.generateResponse(true, HttpStatus.OK, updatedUser);
                } else {
                    responseMap.put("error", "invalid id");
                    logger.error("invalid id for muted user");
                    return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, responseMap);
                }
            } else {
                responseMap.put("error", "permission denied");
                logger.error("permission denied for muting a user");
                return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, responseMap);
            }

        }

        responseMap.put("error", "invalid token");
        return ResponseHandler.generateErrorResponse(false, HttpStatus.BAD_REQUEST, responseMap);

    }


}
