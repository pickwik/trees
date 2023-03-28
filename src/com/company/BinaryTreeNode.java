package com.company;

public class BinaryTreeNode<T> {
    private BinaryTreeNode<T> parent;
    private T value;

    private BinaryTreeNode<T> right;
    private BinaryTreeNode<T> left;

    private long level; // level relative to the whole tree
    private long depth; // depth of node's subtree


    public BinaryTreeNode(BinaryTreeNode<T> parent, T value, long depth) {
        this.parent = parent;
        this.value = value;
        this.depth = depth;
        this.level = parent.level + 1;
    }

    public BinaryTreeNode(BinaryTreeNode<T> parent, T value, long depth, long level) {
        this.parent = parent;
        this.value = value;
        this.depth = depth;
        this.level = level;
    }


    public void updateDepthIfGreater(long newDepth) {
        if (newDepth > getDepth()) {
            setDepth(newDepth);
        }
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

    public long getDepth() {
        return depth;
    }

    public void setDepth(long depth) {
        this.depth = depth;
    }
}
