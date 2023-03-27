package com.company;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class BinaryTree<T extends Comparable<T>> {
    private BinaryTreeNode<T> root;

    public BinaryTree() {
        this.root = null;
    }

    public void add(T value) {
        if (root == null) {
            root = new BinaryTreeNode<>(null, value, 0);
        } else {
            add(root, value);
        }
    }

    private long add(BinaryTreeNode<T> node, T value) {
        T nodeValue = node.getValue();
        if (nodeValue.compareTo(value) > 0) {
            //go left
            if (node.getLeft() != null) {
                long underlyingHeight = add(node.getLeft(), value);
                if (underlyingHeight >= node.getHeight()) {
                    node.setHeight(underlyingHeight + 1);
                }
            } else {
                node.setLeft(new BinaryTreeNode<>(node, value, 0));
                node.setHeight(1);
            }
        }
        if (nodeValue.compareTo(value) <= 0) {
            //go right
            if (node.getRight() != null) {
                long underlyingHeight = add(node.getRight(), value);
                if (underlyingHeight >= node.getHeight()) {
                    node.setHeight(underlyingHeight + 1);
                }
            } else {
                node.setRight(new BinaryTreeNode<>(node, value, 0));
                node.setHeight(1);
            }
        }
        return node.getHeight();
    }

    public void remove(T value) {

    }

    public void search(T value) {

    }

    public void printTree() {
        Map<Long, StringBuilder> levelsMap = new LinkedHashMap<>();
        walkthrough(root, node -> {
            StringBuilder thisLevelStringBuilder = levelsMap.computeIfAbsent(node.getHeight(), any -> new StringBuilder());
            BinaryTreeNode<T> parent = node.getParent();
            if (parent != null) {
                if (parent.getRight() == node) {
                    if (parent.getLeft() == null) {
                        thisLevelStringBuilder.append("\t").append(String.format("%04d", 0)).append("\t");
                    } else {
                        //do nothing
                    }
                }
                thisLevelStringBuilder.append("\t").append(String.format("%04d", node.getValue())).append("\t");
                if (parent.getLeft() == node) {
                    if (parent.getRight() == null) {
                        thisLevelStringBuilder.append("\t").append(String.format("%04d", 0)).append("\t");
                    } else {
                        //do nothing
                    }
                }

            } else {
                thisLevelStringBuilder.append("\t").append(String.format("%04d", node.getValue())).append("\t");
            }
            levelsMap.put(node.getHeight(), thisLevelStringBuilder);
            return null;
        });
        levelsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((l1, l2) -> Long.compare(l2, l1)))
                .forEach(entry -> System.out.println(entry.getKey() + "\t\t\t" + multiply("\t\t", entry.getKey()) + entry.getValue().toString()));
    }

    private void walkthrough(BinaryTreeNode<T> node, Function<BinaryTreeNode<T>, ?> function) {
        function.apply(node);//visit node

        if (node.getLeft() != null) {
            walkthrough(node.getLeft(), function);
        }

        if (node.getRight() != null) {
            walkthrough(node.getRight(), function);
        }
    }

    private String multiply(String string, long times) {
        String result = string;
        for (long i = 0; i <= times; i++) {
            result = result + string;
        }
        return result;
    }
}
