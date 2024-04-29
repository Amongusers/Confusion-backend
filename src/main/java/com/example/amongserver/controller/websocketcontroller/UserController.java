package com.example.amongserver.controller.websocketcontroller;



import com.example.amongserver.domain.entity.User;
import com.example.amongserver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import static com.example.amongserver.constant.Const.LINK_CHAT;
import static com.example.amongserver.constant.Const.USER_TOPIC;

@RestController
@RequestMapping(LINK_CHAT)
@AllArgsConstructor
public class UserController {
    private final SimpMessagingTemplate simpleMessageTemplate;
    private final UserService service;
    @MessageMapping("/user")
    public void geoPosSocket(User user) {
        System.out.println("User");
        System.out.println(user.isReady());
        User localUser = service.getById(user.getId());
        List<User> userList = service.getAll();
        for (User i : userList) {
            //i.setGameState(1);
            if (i.getId()==user.getId()) {
                i.setReady(user.isReady());
            }
        }
        localUser.setReady(user.isReady());
        //service.addAll(userList);

        allReady(service.addAll(userList));
    }

    private void allReady(List<User> userList) {
        for (User usrList : userList) {
            System.out.println(usrList.isReady());
        }
        boolean isAllReady = true;
        for (User user : userList) {
            if (!user.isReady()) {
                isAllReady = false;
                break;
            }
        }
        if (!isAllReady /*|| userList.size() < 3*/) {
            sendMessageToUser(userList);
        } else {
            createRole(userList);
        }
    }
    private void createRole(List<User> userList) {
        for (User localuser : userList) {
            localuser.setIsImposter(false);
        }
        if (!userList.isEmpty()) {
            int impostorIndex = (int) (Math.random() * userList.size());
            userList.get(impostorIndex).setIsImposter(true);
            sendMessageToUser(userList);
        }
        service.addAll(userList);
    }
    private void sendMessageToUser(List<User> userList) {
        // если сообщение отправляется в общий чат

        simpleMessageTemplate.convertAndSend(USER_TOPIC, userList);
    }
}
