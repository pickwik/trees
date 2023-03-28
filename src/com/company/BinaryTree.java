package com.company;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> {
    private BinaryTreeNode<T> root;

    private final long placeholderLength = 2; // hardcoded for now

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
        Map<Long, StringBuilder> levelsMap = initLevelsMapWithMargins();
        walkthrough(root, levelsMap, node -> {
            long level = node.getLevel();
            StringBuilder thisLevelStringBuilder = levelsMap.get(level);
            thisLevelStringBuilder.append(String.format("%02d", node.getValue()))
                    .append(multiply(" ", calculatePadding(root.getDepth() - level) * placeholderLength));
        });
        levelsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingLong(l -> l)))
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue().toString()));
    }

    private Map<Long, StringBuilder> initLevelsMapWithMargins() {
        Map<Long, StringBuilder> levelsMap = new HashMap<>();
        for (long i = 0; i < root.getDepth(); i++) {
            StringBuilder sb = new StringBuilder();
            long margin = calculatePadding((root.getDepth() - i) - 1); // margin for current level == padding for next level
            sb.append(multiply(" ", margin * placeholderLength));
            levelsMap.put(i, sb);
        }
        levelsMap.put(root.getDepth(), new StringBuilder()); // no margin for last level
        return levelsMap;
    }

    private long calculatePadding(long invertedLevel) {
        if (invertedLevel == 0) {
            return 1;
        }
        return calculatePadding(invertedLevel - 1) * 2 + 1;
    }

    private void walkthrough(BinaryTreeNode<T> node, Map<Long, StringBuilder> levelsMap, Consumer<BinaryTreeNode<T>> function) {
        function.accept(node); // visit node

        if (node.getLeft() != null) {
            walkthrough(node.getLeft(), levelsMap, function);
        } else if (node.getLevel() < root.getDepth()) {
            addPaddingsInsteadOfNullNodes(node.getLevel() + 1, levelsMap);
        }

        if (node.getRight() != null) {
            walkthrough(node.getRight(), levelsMap, function);
        } else if (node.getLevel() < root.getDepth()) {
            addPaddingsInsteadOfNullNodes(node.getLevel() + 1, levelsMap);
        }
    }

    private void addPaddingsInsteadOfNullNodes(long levelOfNullNode, Map<Long, StringBuilder> levelsMap) {
        long treeDepth = root.getDepth();
        long childMultiplier = 1;
        for (long i = levelOfNullNode; i <= treeDepth; i++) {
            StringBuilder sb = levelsMap.get(i);
            long padding = (1 + calculatePadding(treeDepth - i)) * childMultiplier; // (node itself + padding for current level) * number of possible nodes in following subtrees
            sb.append(multiply(" ", padding * placeholderLength));
            childMultiplier *= 2;
        }
    }

    private String multiply(String string, long times) {
        StringBuilder result = new StringBuilder();
        for (long i = 0; i < times; i++) {
            result.append(string);
        }
        return result.toString();
    }
}
