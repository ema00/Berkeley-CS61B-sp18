package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Emanuel Aguirre
 * @code uses code from this implementation https://algs4.cs.princeton.edu/32bst/BST.java.html
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        private boolean hasLeftChild() {
            return left != null;
        }

        private boolean hasRightChild() {
            return right != null;
        }
    }


    /* Root node of the tree. */
    private Node root;
    /* The number of key-value pairs in the tree */
    private int size;


    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }


    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        }

        if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return size == 0 ? null : getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     *  Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        if (key.compareTo(p.key) == 0) {
            p.value = value;
            return p;
        }

        if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
            return p;
        } else {
            p.right = putHelper(key, value, p.right);
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
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
        return keySetHelper(root, keySet);
    }

    /* Returns the keys of children of a node */
    private Set<K> keySetHelper(Node node, Set<K> keySet) {
        if (node == null) {
            return keySet;
        }

        keySetHelper(node.left, keySet);
        keySet.add(node.key);
        keySetHelper(node.right, keySet);
        return keySet;
    }

    /** Removes KEY from the tree if present.
     *  @return VALUE removed or null on failed removal.
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (value == null) {
            return null;
        }
        root = delete(root, key);
        return value;
    }

    /**
     * Deletes the node whose key is key, and is in the tree rooted at node, if key exists.
     * @return the resulting tree with the node, whose key is key, deleted; if the key is not in
     * the tree, returns the same tree.
     */
    private Node delete(Node node, K key) {
        if (node == null) return null;

        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = delete(node.left, key);
        } else if (compare > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.right == null) {
                node = node.left;
            } else if (node.left == null) {
                node = node.right;
            } else {
                Node tree = node;
                node = min(tree.right);
                node.right = deleteMin(tree.right);
                node.left = tree.left;
            }
        }
        size -= 1;
        return node;
    }

    /**
     * Finds the node whose key is the minimum, rooted at node.
     * @return the node whose key is minimum.
     */
    private Node min(Node node) {
        return node.left == null
                ? node
                : min(node.left);
    }

    /**
     * Deletes the node whose key is the minimum, rooted at node.
     * @return the resulting tree with the node whose key is minimum deleted.
     */
    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        Set<K> keySet = keySet();
        return keySet.iterator();
    }

}
