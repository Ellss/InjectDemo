package com.wudh.study.injectmanager.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wudh on 2019/3/21.
 **/
public class ClickInvocationHandler implements InvocationHandler {

    Object target;
    Method method;

    public ClickInvocationHandler(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return this.method.invoke(target,objects);
    }
}
