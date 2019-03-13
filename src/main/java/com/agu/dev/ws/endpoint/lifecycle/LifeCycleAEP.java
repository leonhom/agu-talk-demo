package com.agu.dev.ws.endpoint.lifecycle;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by DJ on 2016/12/30.
 */
@ServerEndpoint("/lifecycle")
public class LifeCycleAEP {

    private String START_TIME = "Start Time";
    private Session session;
    private String INVALID_TXT = "xxx";

    @OnOpen
    public void whenOpen(Session session) {
        this.session = session;
        session.getUserProperties().put(START_TIME, System.currentTimeMillis());
        sendMessage("3:Just opened");
    }

    @OnMessage
    public void whenGettingMessage(String msg) {
        System.out.println(msg);
        if (msg.indexOf(INVALID_TXT) != -1) {
            throw new IllegalArgumentException(INVALID_TXT + "not allowed!");
        } else {
            if (msg.indexOf("close") != -1) {
                try {
                    sendMessage("1:Server closing after " + getConnectionSeconds() + "s");
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        sendMessage("3:Just processed a message");
    }

    @OnError
    public void whenSomethingGoesWrong(Throwable throwable) {
        sendMessage("2:Error : " + throwable.getMessage());
    }

    @OnClose
    public void whenClosing(){
        System.out.println("Goodbye !");
    }

    private void sendMessage(String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int getConnectionSeconds() {
        long millis = System.currentTimeMillis() - ((Long) this.session.getUserProperties().get(START_TIME));
        return (int) millis / 1000;
    }
}
