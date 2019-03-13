package com.agu.dev.ws;

import com.agu.dev.ws.endpoint.chat.ChatRoom;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DJ on 2016/12/29.
 */
public class SocketConfig {

    private static SocketConfig instance;
    private Set<ChatRoom> sockets = new HashSet<>();

    public static SocketConfig getInstance(){
        if (null == instance) {
            instance = new SocketConfig();
        }
        return instance;
    }

    private SocketConfig(){
    }

    public Set<ChatRoom> getSockets() {
        return sockets;
    }

    public void add(ChatRoom socket){
        sockets.add(socket);
    }

    public void remove(ChatRoom socket){
        sockets.remove(socket);
    }
}
