package com.company;

public class BinaryTreeNode<K, V> {

    private BinaryTreeNode<K, V> parent;
    private K key;
    private V value;

    private BinaryTreeNode<K, V> right;
    private BinaryTreeNode<K, V> left;


    public BinaryTreeNode(BinaryTreeNode<K, V> parent, K key, V value) {
        this.parent = parent;
        this.key = key;
        this.value = value;
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

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
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
