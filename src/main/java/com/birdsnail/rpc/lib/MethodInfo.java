package com.birdsnail.rpc.lib;

import lombok.Data;
import lombok.val;

import java.util.stream.Stream;

/**
 * @author BirdSnail
 * @date 2020/3/23
 */
@Data
public class MethodInfo {

    private String methodName;
    private Object[] paras;

    public MethodInfo(String methodName, Object[] paras) {
        this.methodName = methodName;
        this.paras = paras;
    }

    public Class<?>[] getParameters() {
        return Stream.of(paras).map(Object::getClass).toArray(Class[]::new);
    }
}
