package com.company;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TreePrinter<T> {

    private final BinaryTreeNode<T> root;
    private final long placeholderLength = 2; // hardcoded for now
    private final long treeDepth;
    private final Map<BinaryTreeNode<T>, Long> nodeLevelsMap = new HashMap<>();
    private final Map<Long, StringBuilder> levelStringBuildersMap = new HashMap<>();


    public TreePrinter(BinaryTreeNode<T> root, long treeDepth) {
        this.root = root;
        this.treeDepth = treeDepth;
        initNodeLevelsMapRecursive(root, 0);
        initLevelStringBuildersMapWithMargins();
    }


    public void print() {
        walkthrough(root, node -> {
            long level = nodeLevelsMap.get(node);
            StringBuilder thisLevelStringBuilder = levelStringBuildersMap.get(level);
            thisLevelStringBuilder.append(String.format("%02d", node.getValue()))
                    .append(multiply(" ", calculatePadding(treeDepth - level) * placeholderLength));
        });
        levelStringBuildersMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingLong(l -> l)))
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue().toString()));
    }

    private void walkthrough(BinaryTreeNode<T> node, Consumer<BinaryTreeNode<T>> function) {
        function.accept(node); // visit node

        long level = nodeLevelsMap.get(node);

        if (node.getLeft() != null) {
            walkthrough(node.getLeft(), function);
        } else if (level < treeDepth) {
            addPaddingsInsteadOfNullNodes(level + 1);
        }

        if (node.getRight() != null) {
            walkthrough(node.getRight(), function);
        } else if (level < treeDepth) {
            addPaddingsInsteadOfNullNodes(level + 1);
        }
    }

    private void initNodeLevelsMapRecursive(BinaryTreeNode<T> node, long level) {
        nodeLevelsMap.put(node, level++);

        if (node.getLeft() != null) {
            initNodeLevelsMapRecursive(node.getLeft(), level);
        }
        if (node.getRight() != null) {
            initNodeLevelsMapRecursive(node.getRight(), level);
        }
    }

    private void initLevelStringBuildersMapWithMargins() {
        for (long i = 0; i < treeDepth; i++) {
            StringBuilder sb = new StringBuilder();
            long margin = calculatePadding((treeDepth - i) - 1); // margin for current level == padding for next level
            sb.append(multiply(" ", margin * placeholderLength));
            levelStringBuildersMap.put(i, sb);
        }
        levelStringBuildersMap.put(treeDepth, new StringBuilder()); // no margin for last level
    }

    private void addPaddingsInsteadOfNullNodes(long levelOfNullNode) {
        long childMultiplier = 1;
        for (long i = levelOfNullNode; i <= treeDepth; i++) {
            StringBuilder sb = levelStringBuildersMap.get(i);
            long padding = (1 + calculatePadding(treeDepth - i)) * childMultiplier; // (node itself + padding for current level) * number of possible nodes in following subtrees
            sb.append(multiply(" ", padding * placeholderLength));
            childMultiplier *= 2;
        }
    }

    private long calculatePadding(long invertedLevel) {
        if (invertedLevel == 0) {
            return 1;
        }
        return calculatePadding(invertedLevel - 1) * 2 + 1;
    }

    private String multiply(String string, long times) {
        StringBuilder result = new StringBuilder();
        for (long i = 0; i < times; i++) {
            result.append(string);
        }
        return result.toString();
    }
}
