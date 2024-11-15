package org.apache.dubbo.springboot.demo.consumer;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.cluster.router.state.StateRouter;
import org.apache.dubbo.rpc.cluster.router.state.StateRouterFactory;

public class StickFirstStateRouterFactory implements StateRouterFactory {
    @Override
    public <T> StateRouter<T> getRouter(Class<T> interfaceClass, URL url) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return new StickFirstStateRouter<>(url);
    }
}