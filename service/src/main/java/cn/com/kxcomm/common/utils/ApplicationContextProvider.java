package cn.com.kxcomm.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider {

    /**
     *
     */
    @Autowired
    private static ApplicationContext context;



    /**
     * @return the context
     */
    public static ApplicationContext  getContext() {
        return context;
    }
}