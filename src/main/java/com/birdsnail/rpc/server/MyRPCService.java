package com.birdsnail.rpc.server;

import com.alibaba.fastjson.JSON;
import com.birdsnail.rpc.lib.GreetingService;
import com.birdsnail.rpc.lib.MethodInfo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * rpc服务段，启动一个socket，将过来的字节流反序列化成对象。
 *
 * @author BirdSnail
 * @date 2020/3/23
 */
public class MyRPCService<T> {

    private T serviceImpl;
    private ServerSocket ss;

    public MyRPCService(T serviceImpl) throws IOException {
        this.serviceImpl = serviceImpl;
        this.ss = new ServerSocket(8888);
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = ss.accept();
            new WorkThread<T>(socket, serviceImpl).start();
        }
    }


    private static class WorkThread<U> extends Thread {

        private Socket socket;
        private U serviceImpl;

        public WorkThread(Socket socket, U serviceImpl) {
            this.socket = socket;
            this.serviceImpl = serviceImpl;
        }

        @Override
        public void run() {
            try {
                String line = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                MethodInfo methodInfo = JSON.parseObject(line, MethodInfo.class);
                Method method = serviceImpl.getClass().getMethod(methodInfo.getMethodName(),
                        methodInfo.getParameters());
                Object returnValue = method.invoke(serviceImpl, methodInfo.getParas());
                socket.getOutputStream().write((JSON.toJSONString(returnValue) + "\n").getBytes());
                socket.getOutputStream().flush();
            } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
