package com.birdsnail.rpc.client;

import com.alibaba.fastjson.JSON;
import com.birdsnail.rpc.lib.MethodInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * RPC框架的客户端，负责将调用的方法名和参数封装成{@link com.birdsnail.rpc.lib.MethodInfo},
 * 通过socket发送到远程服务端
 *
 * @author BirdSnail
 * @date 2020/3/23
 */
public class MyRPCClient<T> {

    private Socket socket;
    private Class<T> interfaceClass;

    public MyRPCClient(Class<T> interfaceClass) throws IOException {
        this.socket = new Socket();
        this.interfaceClass = interfaceClass;
        this.socket.connect(new InetSocketAddress("127.0.0.1", 8888));
    }

    public T getRef() {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        final MethodInfo methodInfo = new MethodInfo(method.getName(), args);
                        // 发送调用的方法
                        socket.getOutputStream().write((JSON.toJSON(methodInfo) + "\n").getBytes());
                        socket.getOutputStream().flush();

                        final String result = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                        return JSON.parse(result);
                    }
                });

    }

}
