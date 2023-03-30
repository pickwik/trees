package com.company;

public class BinaryTreeNode<T> {

    private BinaryTreeNode<T> parent;
    private T value;

    private BinaryTreeNode<T> right;
    private BinaryTreeNode<T> left;

    private long level; // level relative to the whole tree


    public BinaryTreeNode(BinaryTreeNode<T> parent, T value) {
        this.parent = parent;
        this.value = value;
        this.level = parent.level + 1;
    }

    public BinaryTreeNode(BinaryTreeNode<T> parent, T value, long level) {
        this.parent = parent;
        this.value = value;
        this.level = level;
    }


    public BinaryTreeNode<T> getParent() {
        return parent;
    }

    public void setParent(BinaryTreeNode<T> parent) {
        this.parent = parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }
}
