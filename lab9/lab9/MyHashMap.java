package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Emanuel Aguirre
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;


    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }


    private int loadFactor() {
        return size / buckets.length;
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hash = hash(key);
        return buckets[hash].get(key);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * Null values are not allowed as keys.
     */
    @Override
    public void put(K key, V value) {
        int hash = hash(key);
        size = buckets[hash].containsKey(key) ? size : size + 1;
        buckets[hash].put(key, value);

        if (loadFactor() > MAX_LF) {
            resize(buckets.length * 2);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (ArrayMap<K,V> bucket : buckets) {
            keySet.addAll(bucket.keySet());
        }
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int hash = hash(key);
        V value = buckets[hash].remove(key);

        if (size < MAX_LF / 4) {
            resize(buckets.length / 2);
        }
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        } else {
            return null;
        }
    }

    /**
     * Resizes the array of ArrayMap used to store (K,V) pairs. That is, changes number of buckets.
     * @param length is the new number of buckets.
     */
    private void resize(int length) {
        ArrayMap<K, V>[] oldBuckets = buckets;
        Set<K> keys = keySet();

        buckets = new ArrayMap[length];
        clear();

        for (K key : keys) {
            int hash = hash(key);
            int oldHash = Math.floorMod(key.hashCode(), oldBuckets.length);
            V value = oldBuckets[oldHash].get(key);
            buckets[hash].put(key, value);
            size += 1;
        }
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
