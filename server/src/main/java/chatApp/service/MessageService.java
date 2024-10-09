package chatApp.service;

import chatApp.Entities.*;
import chatApp.Response.ResponseHandler;
import chatApp.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private GroupChatsRepository groupChatsRepository;
    private static Logger logger = LogManager.getLogger(GroupMembersService.class.getName());

    /**
     * method that responsible for creating message obj and save it to DB
     * @param content -
     * @return  message
     */
    public Message create(String content) {
        Message message = new Message();
        message.setContent(content);
        message.setDateTime(LocalDateTime.now());
        logger.info("message created: " + content.toString());
        return messageRepository.save(message);
    }
}
