package com.birdsnail.rpc.lib;

/**
 * @author BirdSnail
 * @date 2020/3/23
 */
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String sayHi(String contend) {
        return String.format("hello %s", contend);
    }
}
