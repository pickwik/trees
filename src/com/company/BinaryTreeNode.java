package com.company;

import java.util.HashSet;
import java.util.Set;

public class BinaryTreeNode<K, V> {

    private BinaryTreeNode<K, V> parent;
    private K key;
    private Set<V> values;

    private BinaryTreeNode<K, V> right;
    private BinaryTreeNode<K, V> left;


    public BinaryTreeNode(BinaryTreeNode<K, V> parent, K key, V value) {
        this.parent = parent;
        this.key = key;
        values = new HashSet<>();
        values.add(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryTreeNode<?, ?> that = (BinaryTreeNode<?, ?>) o;

        if (!key.equals(that.key)) return false;
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + values.hashCode();
        return result;
    }


    public Set<V> getValues() {
        return values;
    }

    public void setValues(Set<V> values) {
        this.values = values;
    }

    public void addValue(V value) {
        values.add(value);
    }

    public void removeValue(V value) {
        values.remove(value);
    }

    public boolean hasValue(V value) {
        return values.contains(value);
    }

    public BinaryTreeNode<K, V> getParent() {
        return parent;
    }

    public void setParent(BinaryTreeNode<K, V> parent) {
        this.parent = parent;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public BinaryTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<K, V> right) {
        this.right = right;
    }

    public BinaryTreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<K, V> left) {
        this.left = left;
    }
}
