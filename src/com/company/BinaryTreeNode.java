package com.company;

public class BinaryTreeNode<T> {
    private BinaryTreeNode<T> parent;
    private T value;

    private BinaryTreeNode<T> right;
    private BinaryTreeNode<T> left;

    private long height;

    public BinaryTreeNode(BinaryTreeNode<T> parent, T value, BinaryTreeNode<T> right, BinaryTreeNode<T> left) {
        this.parent = parent;
        this.value = value;
        this.right = right;
        this.left = left;
    }

    public BinaryTreeNode(BinaryTreeNode<T> parent, T value, long height) {
        this.parent = parent;
        this.value = value;
        this.height = height;
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

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}
