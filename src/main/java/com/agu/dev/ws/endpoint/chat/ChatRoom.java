package com.agu.dev.ws.endpoint.chat;

import com.agu.dev.ws.SocketConfig;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by DJ on 2016/12/29.
 */
@ServerEndpoint("/chatroom")
public class ChatRoom {

    private Session session;
    private List<String> names = new ArrayList<>();
    private String username;

    @OnOpen
    public void open(Session session) {
        this.username = "用户-->" + session.getId();
        this.session = session;
        SocketConfig.getInstance().add(this);
        names.add(username);
        broadCast(username + " 进入聊天室！！");
    }

    @OnMessage
    public void receive(Session session, String msg) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(new Date());

        broadCast(format + " " + username + "：" + msg);
    }

    @OnClose
    public void close(Session session) {
        SocketConfig.getInstance().remove(this);
        names.remove(username);
        broadCast(username + " 退出聊天室！！");
    }

    public void broadCast(String msg) {
        Set<ChatRoom> sockets = SocketConfig.getInstance().getSockets();
        System.out.println("sockets-->" + sockets.size());
        for (Iterator iterator = sockets.iterator(); iterator.hasNext(); ) {
            try {
                ChatRoom chatRoom = (ChatRoom) iterator.next();
                chatRoom.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
