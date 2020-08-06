package com.fantaike.framework.lang;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LIFOMap<K, V> {

    private final Map<K, LinkedList<V>> container = new HashMap<>();

    public void put(K key, V value) {
        container.putIfAbsent(key, new LinkedList<>());
        LinkedList<V> object = container.get(key);
        // put header
        object.addFirst(value);
    }

    public V get(K key) {
        LinkedList<V> list = container.get(key);
        if (list != null) {
            // 代表有值放入,不提供删除
            return list.getFirst();
        }
        return null;
    }

    public boolean containsKey(K key) {
        return container.containsKey(key);
    }
    
}
