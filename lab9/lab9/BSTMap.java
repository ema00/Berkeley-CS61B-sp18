package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Emanuel Aguirre
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

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node sentinel = new Node(null, null);
        sentinel.right = root;
        Node parent = getParent(key, sentinel);

        if (parent == null) {
            return null;
        }

        Node removed = parent.right != null && parent.right.key.compareTo(key) == 0 ?
                parent.right :
                parent.left;

        if (removed == parent.left) {
            if (removed.isLeaf()) {
                parent.left = null;
            } else if (removed.hasLeftChild() && !removed.hasRightChild()) {
                parent.left = removed.left;
            } else if (removed.hasRightChild() && !removed.hasLeftChild()) {
                parent.left = removed.right;
            } else {
                if (!removed.left.hasRightChild()) {
                    parent.left = removed.left;
                } else {
                    Node replacement = extractGreatest(removed.left);
                    replacement.left = removed.left;
                    replacement.right = removed.right;
                    parent.left = replacement;
                }
            }
        } else {
            if (removed.isLeaf()) {
                parent.right = null;
            } else if (removed.hasRightChild() && !removed.hasLeftChild()) {
                parent.right = removed.right;
            } else if (removed.hasLeftChild() && !removed.hasRightChild()) {
                parent.right = removed.left;
            } else {
                if (!removed.right.hasLeftChild()) {
                    parent.right = removed.right;
                } else {
                    Node replacement = extractSmallest(removed.right);
                    replacement.left = removed.left;
                    replacement.right = removed.right;
                    parent.right = replacement;
                }
            }
        }

        root = root == removed ? sentinel.right : root;
        size -= 1;
        return removed.value;
    }

    /**
     * Returns the parent of the node with the given key.
     * It the key doesn't exist in the tree returns null.
     */
    private Node getParent(K key, Node parent) {
        if (parent == null || parent.isLeaf()) {
            return null;
        } else if (parent.hasLeftChild() && key.compareTo(parent.left.key) == 0) {
            return parent;
        } else if (parent.hasRightChild() && key.compareTo(parent.right.key) == 0) {
            return parent;
        }

        // if parent is the sentinel node in remove(), then parent.key == null
        if (parent.key == null || !(key.compareTo(parent.key) < 0)) {
            return getParent(key, parent.right);
        } else {
            return getParent(key, parent.left);
        }
    }

    /**
     * Extracts the greatest child of a node.
     * Preconditions: the node must not be null.
     * @return the greatest node removed.
     */
    private Node extractGreatest(Node node) {
        while (node.right.hasRightChild()) {
            node = node.right;
        }
        Node removed = node.right;
        if (node.right.hasLeftChild()) {
            node.right = removed.left;
            removed.left = null;
        } else {
            node.right = null;
        }
        return removed;
    }

    /**
     * Extracts the smallest child of a node.
     * Preconditions: the node must not be null.
     * @return the smallest node removed.
     */
    private Node extractSmallest(Node node) {
        while (node.left.hasLeftChild()) {
            node = node.left;
        }
        Node removed = node.left;
        if (node.left.hasRightChild()) {
            node.left = removed.right;
            removed.right = null;
        } else {
            node.left = null;
        }
        return removed;
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
