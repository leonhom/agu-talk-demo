package com.agu.dev.ws.endpoint.echo;

import javax.websocket.*;
import java.io.IOException;

/**
 * Created by DJ on 2016/12/27.
 */
public class ProgrammaticEchoServer extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        Session mySession = session;
        mySession.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String text) {
                try {
                    mySession.getBasicRemote().sendText("I got (" + text + ")" + "so I am send the message back.anna");
                } catch (IOException e) {
                    System.out.println("oh dear, something was wrong send the message back:" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        super.onError(session, throwable);
    }
}
