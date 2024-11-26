package org.apache.dubbo.springboot.demo.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

@Component  // 确保这个类是 Spring 管理的 Bean
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    // 这个方法会由 Spring 容器自动调用，并将 ApplicationContext 注入
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    // 提供一个静态方法访问 ApplicationContext
    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
