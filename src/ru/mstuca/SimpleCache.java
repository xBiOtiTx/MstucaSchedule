package ru.mstuca;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Евгений on 28.01.2015.
 */
public class SimpleCache {
    private static SimpleCache sSimpleCache;

    public static SimpleCache getInstance() {
        if (sSimpleCache == null) {
            sSimpleCache = new SimpleCache();
        }
        return sSimpleCache;
    }

    private final Map<Object, Object> sCache = new HashMap<Object, Object>();

    public void put(Object k, Object v) {
        sCache.put(k,v);
    }

    public Object get(Object k) {
        return sCache.get(k);
    }

    public void remove(Object k) {
        sCache.remove(k);
    }

    public void clear() {
        sCache.clear();
    }

    public boolean containsKey(Object k) {
        return sCache.containsKey(k);
    }

}
