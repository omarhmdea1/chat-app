package chatApp.controller;

import chatApp.Entities.Message;
import chatApp.Entities.PrivateChat;
import chatApp.Entities.User;
import chatApp.service.AuthService;
import chatApp.service.ChatService;
import chatApp.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/chat")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ChatService chatService;

    private static Logger logger = LogManager.getLogger(MessagesController.class.getName());

    /**
     * end point that responsible for creating message obj and save it to DB
     * @param content
     * @return message obj
     */
    @RequestMapping(value = "message", method = RequestMethod.POST)
    public Message creteMessage(@RequestBody String content) {
        return messageService.create(content);
    }

    /**
     * end point that responsible for creating PrivateChat and save it to DB
     * @param chat
     * @return
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public void sendMessage(@RequestBody PrivateChat chat) {
        chatService.savePrivateChat(chat);
    }

    /**
     * end point that responsible for fetching private chats
     * @param token
     * @return private chats
     */
    @RequestMapping(value = "privateChat", method = RequestMethod.GET)
    public List<User> getPrivateChats(@RequestHeader String token) {
        Optional<User> user = authService.findByToken(token);
        return chatService.getPrivateChats(user.get().getId());
    }

    /**
     * end point that responsible for fetching message history between user with valid token and id in the path
     * @param token
     * @param id
     * @return message history
     */
    @RequestMapping(value = "history/private/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getPrivateHistory(@RequestHeader String token, @PathVariable int id) {
        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()) {
            return chatService.getPrivateHistoryMessages(user.get().getId(), id);
        }
        return null;
    }

    /**
     * end point that responsible for fetching message history between user with valid token and group (Main Chat)
     * @param token
     * @param groupName
     * @return message history
     */
    @RequestMapping(value = "history/public/{groupName}", method = RequestMethod.GET)
    public ResponseEntity<Object> getGroupHistory(@RequestHeader String token, @PathVariable String groupName) {
        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()) {
            return chatService.getGroupHistoryMessages(groupName);
        }
        return null;
    }

    /**
     * end point that responsible for fetching group members
     * @param token
     * @param groupName
     * @return group members
     */
    @RequestMapping(value = "list/public/{groupName}", method = RequestMethod.GET)
    public List<User> getGroupMembers(@RequestHeader String token, @PathVariable String groupName) {
        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()) {
            return chatService.getGroupMembers(groupName);
        }
        return null;
    }

    /**
     * end point that responsible for exports private chat between user with valid token and id in the path
     * @param token
     * @param receiverUser
     * @return string that contains all formatted messages
     */
    @RequestMapping(value = "export/private/{receiverUser}", method = RequestMethod.GET)

    public String exportPrivateMessages(@RequestHeader String token, @PathVariable int receiverUser) {
        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()){
        return chatService.exportMessages(user.get().getId(), receiverUser);
        }
        return null;
    }

    /**
     * end point that responsible for exports group chat between user with valid token and id in the path
     * @param token
     * @param groupName
     * @return string that contains all formatted messages
     */
    @RequestMapping(value = "export/public/{groupName}", method = RequestMethod.GET)

    public String exportPublicMessages(@RequestHeader String token, @PathVariable String groupName) {
        Optional<User> user = authService.findByToken(token);
        if(user.isPresent()){
            return chatService.exportPublicMessages(groupName);
        }
        return null;
    }

}
