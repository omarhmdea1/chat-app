package chatApp.service;

import chatApp.Entities.User;
import chatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    /**
     * method that responsible for finding user by id
     * @param id -
     * @return  user if exits
     */
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }


    /**
     * method that responsible for updating user , if user exits user repository updated him with new data
     *                                             else user repository create new user with new data
     * @param user -
     * @return  user
     */
    public User update(User user) {
        return userRepository.save(user);
    }

    /**
     * method that responsible for finding user by matching query
     * @param query -
     * @return  set of users
     */
    public Set<User> findByQuery(String query) {
        Set<User> result = new HashSet<User>();
        result.addAll(userRepository.findByFirstNameStartingWith(query));
        result.addAll(userRepository.findByLastNameStartingWith(query));

        return result;
    }

}
