/*
 *  Copyright 2012 Unicommerce Technologies (P) Limited . All Rights Reserved.
 *  UNICOMMERCE TECHONOLOGIES PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Feb 14, 2012
 *  @author singla
 */
package com.dawnbit.common.utils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @param <K>
 * @param <V>
 * @author singla
 */
public class LRUCache<K, V> implements Map<K, V> {

    private final Map<K, V> cacheStore;

    /**
     * @param maxElements
     */
    public LRUCache(final int maxElements) {
        this.cacheStore = new LinkedHashMap<K, V>(16, 0.75f, true) {
            /**
             *
             */
            private static final long serialVersionUID = -285617760349622327L;

            /* (non-Javadoc)
             * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
             */
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxElements;
            }
        };
    }

    /* (non-Javadoc)
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public V put(K key, V elem) {
        return cacheStore.put(key, elem);
    }

    /* (non-Javadoc)
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public V get(Object key) {
        return cacheStore.get(key);
    }

    /* (non-Javadoc)
     * @see java.util.Map#clear()
     */
    @Override
    public void clear() {
        cacheStore.clear();
    }

    /* (non-Javadoc)
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return cacheStore.containsKey(key);
    }

    /* (non-Javadoc)
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        return cacheStore.containsValue(value);
    }

    /* (non-Javadoc)
     * @see java.util.Map#entrySet()
     */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return cacheStore.entrySet();
    }

    /* (non-Javadoc)
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return cacheStore.isEmpty();
    }

    /* (non-Javadoc)
     * @see java.util.Map#keySet()
     */
    @Override
    public Set<K> keySet() {
        return cacheStore.keySet();
    }

    /* (non-Javadoc)
     * @see java.util.Map#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        cacheStore.putAll(m);
    }

    /* (non-Javadoc)
     * @see java.util.Map#remove(java.lang.Object)
     */
    @Override
    public V remove(Object key) {
        return cacheStore.remove(key);
    }

    /* (non-Javadoc)
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return cacheStore.size();
    }

    /* (non-Javadoc)
     * @see java.util.Map#values()
     */
    @Override
    public Collection<V> values() {
        return cacheStore.values();
    }
}
