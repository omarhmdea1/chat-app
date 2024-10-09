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

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private GroupChatsRepository groupChatsRepository;

    @Autowired
    private GroupMembersRepository groupMembersRepository;

    @Autowired
    private GroupRepository groupRepository;

    private static Logger logger = LogManager.getLogger(ChatService.class.getName());


    /**
     * method that responsible for saving PrivateChat
     * @param chat
     * @return PrivateChat
     */
    public PrivateChat savePrivateChat(PrivateChat chat) {
        logger.info("saving private message in data base");
        return privateChatRepository.save(chat);
    }

    /**
     * method that responsible for saving GroupChats
     * @param chat
     * @return GroupChats
     */
    public GroupChats saveGroupChat(GroupChats chat) {
        logger.info("saving group message in data base");
        return groupChatsRepository.save(chat);
    }

    /**
     * method that responsible for fetching message history between senderUser and receiverUser
     * @param senderUser
     * @param receiverUser
     * @return message history
     */
    public ResponseEntity<Object> getPrivateHistoryMessages(int senderUser, int receiverUser) {

        logger.info("getting private history messages between user id " + senderUser + " and user id " + receiverUser);
        List<Map<String, Object>> messages = new ArrayList<>();
        List<PrivateChat> privateChats = privateChatRepository.findBySenderUserAndReceiverUser(senderUser, receiverUser);
        privateChats.addAll(privateChatRepository.findBySenderUserAndReceiverUser(receiverUser, senderUser));
        List<PrivateChat> sortedChats = privateChats.stream().sorted(this::comparePrivateChat).collect(Collectors.toList());

        sortedChats.forEach(p -> {
            Map<String, Object> formattedMap = new HashMap<>();
            formattedMap.put("sender", userRepository.getUserById(p.getSenderUser()));
            formattedMap.put("receiver", userRepository.getUserById(p.getReceiverUser()));
            formattedMap.put("message", messageRepository.findById(p.getMessage()).getContent());
            messages.add(formattedMap);
        });

        return ResponseHandler.generateResponse(true, HttpStatus.OK, messages);
    }

    /**
     * method that responsible for fetching message group history
     * @param groupName -
     * @return group message history
     */
    public ResponseEntity<Object> getGroupHistoryMessages(String groupName) {

        logger.info("getting group history messages from " + groupName);

        Optional<PublicGroups> byGroupName = groupRepository.findByGroupName(groupName);
        int groupId = byGroupName.get().getId();

        List<Map<String, Object>> messages = new ArrayList<>();
        List<GroupChats> groupMessages = new ArrayList<>();
        List<Integer> membersIds = groupMembersRepository.findByGroupId(groupId).stream().map(GroupMembers::getUserId).collect(Collectors.toList());

        for (int id : membersIds) {
            List<GroupChats> messagesToGroup = groupChatsRepository.findBySenderUserAndGroupId(id, groupId);
            groupMessages.addAll(messagesToGroup);
        }

        if (groupMessages.size() > 1) {
            groupMessages = groupMessages.stream().sorted(this::compareGroupChat).collect(Collectors.toList());
        }

        groupMessages.forEach(m -> {
            Map<String, Object> formattedMap = new HashMap<>();
            formattedMap.put("sender", userRepository.getUserById(m.getSenderUser()));
            formattedMap.put("receiver", groupRepository.findById(groupId).get().getGroupName());
            formattedMap.put("message", messageRepository.findById(m.getMessage()).getContent());
            messages.add(formattedMap);
        });

        return ResponseHandler.generateResponse(true, HttpStatus.OK, messages);
    }

    /**
     * method that responsible for fetching group members
     * @param groupName
     * @return group members
     */
    public List<User> getGroupMembers(String groupName) {

        logger.info("getting group members list of " + groupName);
        Optional<PublicGroups> byGroupName = groupRepository.findByGroupName(groupName);
        int groupId = byGroupName.get().getId();
        List<GroupMembers> members = groupMembersRepository.findByGroupId(groupId);

        if (members.size() > 1) {
            members = members.stream().sorted(this::compareGroupMembers).collect(Collectors.toList());
        }

        List<User> membersAsUsers = new ArrayList<>();

        members.forEach(m -> {
            membersAsUsers.add(userRepository.getUserById(m.getUserId()).get());
        });
        return membersAsUsers;
    }

    /**
     * method that responsible for exports private chat between senderUser and receiverUser
     * @param receiverUser
     * @param senderUser
     * @return string that contains all formatted messages
     */
    public String exportMessages(int senderUser, int receiverUser) {

        logger.info("exporting private messages between user id " + senderUser + " and user id " + receiverUser);
        List<Map<String, Object>> messages = new ArrayList<>();
        List<PrivateChat> privateChats = privateChatRepository.findBySenderUserAndReceiverUser(senderUser, receiverUser);
        privateChats.addAll(privateChatRepository.findBySenderUserAndReceiverUser(receiverUser, senderUser));
        List<PrivateChat> sortedChats = privateChats.stream().sorted(this::comparePrivateChat).collect(Collectors.toList());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder exportedMsg = new StringBuilder();
        for (PrivateChat p : sortedChats) {

            exportedMsg.append("[").append(messageRepository.findById(p.getMessage()).getDateTime().format(formatter)).append("]");
            exportedMsg.append(" ").append(userRepository.getUserById(p.getSenderUser()).get().getFullName()).append(":");
            exportedMsg.append(" ").append(messageRepository.findById(p.getMessage()).getContent()).append(" \n");
        }

        return exportedMsg.toString(); // need to remove response handlers
    }

    /**
     * method that responsible for exports group chat
     * @param groupName
     * @return string that contains all formatted messages
     */
    public String exportPublicMessages(String groupName) {

        logger.info("exporting group messages from " + groupName);
        Optional<PublicGroups> byGroupName = groupRepository.findByGroupName(groupName);
        int groupId = byGroupName.get().getId();

        List<GroupMembers> members = groupMembersRepository.findByGroupId(groupId);
        List<Integer> membersIds = groupMembersRepository.findByGroupId(groupId).stream().map(GroupMembers::getUserId).collect(Collectors.toList());

        List<GroupChats> gc = new ArrayList<>();
        for (int id : membersIds) {

            gc.addAll(groupChatsRepository.findBySenderUserAndGroupId(id, groupId));

        }

        List<GroupChats> sortedGroupChats = gc.stream().sorted(this::compareGroupChat).collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder exportedGroupChats = new StringBuilder();
        for (GroupChats groupChat : sortedGroupChats) {

            exportedGroupChats.append("[").append(messageRepository.findById(groupChat.getMessage()).getDateTime().format(formatter)).append("]");
            exportedGroupChats.append(" ").append(userRepository.getUserById(groupChat.getSenderUser()).get().getFullName()).append(":");
            exportedGroupChats.append(" ").append(messageRepository.findById(groupChat.getMessage()).getContent()).append(" \n");
        }

        return exportedGroupChats.toString(); // need to remove response handlers
    }

    /**
     * method that responsible for fetching private chats for specific user id
     * @param id
     * @return private chats
     */
    public List<User> getPrivateChats(int id) {
        List<Integer> privateChats = privateChatRepository.findPrivateChats(id);
        List<User> users = new ArrayList<>();

        privateChats.forEach(c -> {
            users.add(userRepository.findById(c).get());
        });

        return users;
    }

    /**
     * method that responsible for compering two PrivateChats
     * @param p1
     * @param p2
     * @return positive number if p1 greater then p2 , 0 if p1 equals p2 and -1 if p2 greater then p1
     */
    public int comparePrivateChat(PrivateChat p1, PrivateChat p2) {
        logger.info("comparing and sorting private messages by date");
        return messageRepository.findById(p1.getMessage()).compareTo(messageRepository.findById(p2.getMessage()));
    }

    /**
     * method that responsible for compering two GroupChats
     * @param g1
     * @param g2
     * @return positive number if g1 greater then g2 , 0 if g1 equals g2 and -1 if g2 greater then g1
     */
    public int compareGroupChat(GroupChats g1, GroupChats g2) {
        logger.info("comparing and sorting group messages by date");
        return messageRepository.findById(g1.getMessage()).compareTo(messageRepository.findById(g2.getMessage()));
    }

    /**
     * method that responsible for compering two GroupMembers by member role
     * @param m1
     * @param m2
     * @return positive number if m1.getRole() greater then m2.getRole() , 0 if m1.getRole() equals m2.getRole()
     *                         and -1 if m2.getRole() greater then m1.getRole()
     */
    public int compareGroupMembers(GroupMembers m1, GroupMembers m2) {

        logger.info("sorting group members by roles");
        Optional<User> user1 = userRepository.getUserById(m1.getUserId());
        Optional<User> user2 = userRepository.getUserById(m2.getUserId());
        return user2.get().getRole().compareTo(user1.get().getRole());
    }


    /**
     * method that responsible for finding GroupChat by group name par
     * @param groupName
     * @return GroupChat if exits
     */
    public Optional<PublicGroups> findGroupChatByName(String groupName) {
        return groupRepository.findByGroupName(groupName);
    }

}
