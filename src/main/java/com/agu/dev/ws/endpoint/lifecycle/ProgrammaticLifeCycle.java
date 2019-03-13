package com.agu.dev.ws.endpoint.lifecycle;


import javax.websocket.*;
import java.io.IOException;

/**
 * Created by DJ on 2016/12/30.
 */
public class ProgrammaticLifeCycle extends Endpoint {
    private static String START_TIME = "Start Time";
    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        final Session mySession = session;
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                if (message.indexOf("xxx") != -1) {
                    throw new IllegalArgumentException("xxx not allowed !");
                } else if (message.indexOf("close") != -1) {
                    try {
                        sendMessage("1:Server closing after " + getConnectionSeconds() + " s");
                        mySession.close();
                    } catch (IOException ioe) {
                        System.out.println("Error closing session " + ioe.getMessage());
                    }
                    return;
                }
                sendMessage("3:Just processed a message");
            }
        });
        session.getUserProperties().put(START_TIME, System.currentTimeMillis());
        this.sendMessage("3:Just opened");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Goodbye !");
    }

    @Override
    public void onError(Session session, Throwable thr) {
        this.sendMessage("2:Error: " + thr.getMessage());
    }

    void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ioe) {
            System.out.println("Error sending message " + message);
        }
    }

    int getConnectionSeconds() {
        long millis = System.currentTimeMillis() - ((Long) this.session.getUserProperties().get(START_TIME));
        return (int) millis / 1000;
    }
}
