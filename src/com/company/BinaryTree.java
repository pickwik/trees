package com.company;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> {
    private BinaryTreeNode<T> root;

    public BinaryTree() {
        this.root = null;
    }

    public void add(T value) {
        if (root == null) {
            root = new BinaryTreeNode<>(null, value, 0, 0);
        } else {
            add(root, value);
        }
    }

    private long add(BinaryTreeNode<T> node, T value) {
        T nodeValue = node.getValue();
        if (nodeValue.compareTo(value) > 0) {
            // go left
            if (node.getLeft() != null) {
                long underlyingDepth = add(node.getLeft(), value);
                node.updateDepthIfGreater(underlyingDepth + 1);
            } else {
                node.setLeft(new BinaryTreeNode<>(node, value, 0));
                node.updateDepthIfGreater(1);
            }
        }
        if (nodeValue.compareTo(value) < 0) {
            // go right
            if (node.getRight() != null) {
                long underlyingDepth = add(node.getRight(), value);
                node.updateDepthIfGreater(underlyingDepth + 1);
            } else {
                node.setRight(new BinaryTreeNode<>(node, value, 0));
                node.updateDepthIfGreater(1);
            }
        }
        if (nodeValue.compareTo(value) == 0) {
            // handle duplicates later
        }
        return node.getDepth();
    }

    public void remove(T value) {

    }

    public void search(T value) {

    }

    public void printTree() {
        new TreePrinter<>(root).print();
    }
}
