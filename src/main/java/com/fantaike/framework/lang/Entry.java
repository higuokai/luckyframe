package com.fantaike.framework.lang;

import java.io.Serializable;
import java.util.Map;

public class Entry<K, V> implements Map.Entry<K, V>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private K k;

    private V v;

    @Override
    public K getKey() {
        return k;
    }

    @Override
    public V getValue() {
        return v;
    }

    @Override
    public V setValue(V value) {
        return this.v = value;
    }

    public void setKey(K key) {
        this.k = key;
    }

    public Entry(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public Entry() {
    }

}