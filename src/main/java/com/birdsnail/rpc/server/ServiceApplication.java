package com.birdsnail.rpc.server;

import com.birdsnail.rpc.lib.GreetingServiceImpl;

import java.io.IOException;

/**
 * @author BirdSnail
 * @date 2020/3/23
 */
public class ServiceApplication {

    public static void main(String[] args) throws IOException {
        new MyRPCService<>(new GreetingServiceImpl()).start();
        System.in.read();
    }
}
