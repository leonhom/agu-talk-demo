package com.agu.dev.ws.init;

import com.agu.dev.ws.endpoint.echo.ProgrammaticEchoServer;
import com.agu.dev.ws.endpoint.lifecycle.ProgrammaticLifeCycle;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by BruceDing on 2016/12/27.
 * 1.  getAnnotatedEndpointClasses
 * 2.  getEndpointConfigs
 * <p>
 * 上面的两个方法都是用来注册 webSocket的。   只不过注册的方式不同。
 * 方法1：注解的方式
 * 方法2：接口的方式
 * <p>
 * 注解的方式更加的，灵活简单。
 * 接口的方式更加的传统，严谨。
 * <p>
 * (non-Javadoc)
 *
 * @see javax.websocket.server.ServerApplicationConfig#getAnnotatedEndpointClasses(java.util.Set)
 */
public class ProgrammaticEchoServerAppConfig implements ServerApplicationConfig {

    //pass all classes that extend Endpoint
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
        System.out.println("实现EndPoint接口类的数量-->" + set.size());
        Set configs = new HashSet<ServerEndpointConfig>();
        configs.add(ServerEndpointConfig.Builder.create(ProgrammaticLifeCycle.class, "/programmaticLifeCycle").build());
        configs.add(ServerEndpointConfig.Builder.create(ProgrammaticEchoServer.class, "/programmaticEcho").build());
        return configs;
    }

    //pass all de annotated EndPoint
    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        System.out.println("EndPoint扫描到的数量-->" + scanned.size());
        return scanned;
    }
}
