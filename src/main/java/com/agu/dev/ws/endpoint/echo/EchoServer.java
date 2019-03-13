package com.agu.dev.ws.endpoint.echo;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/echo")
public class EchoServer {

    //每次调用这个方法，创建一个新的WebSocket通道
    public EchoServer() {
        System.out.println("EchoServer Constructor!!!");
    }

    //接收客户端消息的响应方法
    @OnMessage
    public String receive(String incomingMessage) {
        return "I got this (" + incomingMessage + ")" + " so I am sending it back !";
    }

    //通道建立成功的响应方法
    @OnOpen
    public void open(Session session) {
        //session代表一次socket连接。可以接受数据，也可以发送数据
        System.out.println("建立连接成功！！" + session.getId());
    }

    //关闭通道的响应方法
    @OnClose
    public void close(Session session) {
        System.out.println(session.getId() + "session 关闭啦！！！");
    }

}
