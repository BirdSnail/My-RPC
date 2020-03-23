package com.birdsnail.rpc.client;

import com.birdsnail.rpc.lib.GreetingService;

import java.io.IOException;

/**
 * @author BirdSnail
 * @date 2020/3/23
 */
public class ClientApplication {


    public static void main(String[] args) throws IOException {
        final MyRPCClient<GreetingService> client = new MyRPCClient<>(GreetingService.class);
        final GreetingService greetingService = client.getRef();

        System.out.println(greetingService.sayHi("yhd11"));
    }
}
