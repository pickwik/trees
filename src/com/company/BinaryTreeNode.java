package com.company;

public class BinaryTreeNode<T> {

    private BinaryTreeNode<T> parent;
    private T value;
    private long numberOfDuplicates;

    private BinaryTreeNode<T> right;
    private BinaryTreeNode<T> left;


    public BinaryTreeNode(BinaryTreeNode<T> parent, T value) {
        this.parent = parent;
        this.value = value;
        this.numberOfDuplicates = 0;
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

    public long getNumberOfDuplicates() {
        return numberOfDuplicates;
    }

    public void setNumberOfDuplicates(long numberOfDuplicates) {
        this.numberOfDuplicates = numberOfDuplicates;
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
}
