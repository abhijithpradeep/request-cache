package com.github.abhijithpradeep.requestCache.config;

import com.github.abhijithpradeep.requestCache.config.IRequestCacheManager;
import com.github.abhijithpradeep.requestCache.config.Request;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abhijithpradeep on 26/9/17.
 */
public class ThreadlocalCacheManager implements IRequestCacheManager {

    private static ThreadLocal<Map<String, ConcurrentMapCache>> threadLocalMap = new ThreadLocal<>();
    private static ThreadLocal<Request> threadLocalRequest = new ThreadLocal<>();


    public Cache getCache(String name) {
        if (threadLocalMap.get().get(name) == null) {
            threadLocalMap.get().put(name, new ConcurrentMapCache(name));
        }
        return threadLocalMap.get().get(name);
    }

    public void startRequest(String name) {
        threadLocalMap.set(new HashMap<String, ConcurrentMapCache>());
        threadLocalRequest.set(new Request());
        threadLocalRequest.get().setRequestName(name);
    }

    @Override
    public String getRequestName() {
        if (threadLocalRequest.get() == null) {
            return null;
        }
        return threadLocalRequest.get().getRequestName();
    }

    public void endRequest(String name) {
        threadLocalMap.set(null);
    }

}
