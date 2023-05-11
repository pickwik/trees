package com.company.trees.util;

import com.company.trees.avl.BinaryTree;
import com.company.trees.avl.BinaryTreeNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class TreePrinter {

    private static final long PLACEHOLDER_LENGTH = 2; // hardcoded for now


    public static void print(BinaryTree<?, ?> tree) {
        // init
        BinaryTreeNode<?, ?> root = tree.getRoot();
        long treeDepth = tree.getDepth();
        Map<BinaryTreeNode<?, ?>, Long> nodeLevelsMap = new HashMap<>();
        initNodeLevelsMapRecursive(nodeLevelsMap, root, 0);
        Map<Long, StringBuilder> levelStringBuildersMap = new HashMap<>();
        initLevelStringBuildersMapWithMargins(levelStringBuildersMap, treeDepth);

        // fill lines
        walkthrough(root, nodeLevelsMap, levelStringBuildersMap, treeDepth, node -> {
            long level = nodeLevelsMap.get(node);
            StringBuilder thisLevelStringBuilder = levelStringBuildersMap.get(level);
            thisLevelStringBuilder.append(String.format("%02d", node.getKey()))
                    .append(multiply(" ", calculatePadding(treeDepth - level) * PLACEHOLDER_LENGTH));
        });

        // print lines
        levelStringBuildersMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingLong(l -> l)))
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue().toString()));
    }

    private static void walkthrough(BinaryTreeNode<?, ?> node,
                                    Map<BinaryTreeNode<?, ?>, Long> nodeLevelsMap,
                                    Map<Long, StringBuilder> levelStringBuildersMap,
                                    long treeDepth,
                                    Consumer<BinaryTreeNode<?, ?>> function) {
        function.accept(node); // visit node

        long level = nodeLevelsMap.get(node);

        if (node.getLeft() != null) {
            walkthrough(node.getLeft(), nodeLevelsMap, levelStringBuildersMap, treeDepth, function);
        } else if (level < treeDepth) {
            addPaddingsInsteadOfNullNodes(level + 1, levelStringBuildersMap, treeDepth);
        }

        if (node.getRight() != null) {
            walkthrough(node.getRight(), nodeLevelsMap, levelStringBuildersMap, treeDepth, function);
        } else if (level < treeDepth) {
            addPaddingsInsteadOfNullNodes(level + 1, levelStringBuildersMap, treeDepth);
        }
    }

    private static void initNodeLevelsMapRecursive(Map<BinaryTreeNode<?, ?>, Long> nodeLevelsMap, BinaryTreeNode<?, ?> node, long level) {
        nodeLevelsMap.put(node, level++);

        if (node.getLeft() != null) {
            initNodeLevelsMapRecursive(nodeLevelsMap, node.getLeft(), level);
        }
        if (node.getRight() != null) {
            initNodeLevelsMapRecursive(nodeLevelsMap, node.getRight(), level);
        }
    }

    private static void initLevelStringBuildersMapWithMargins(Map<Long, StringBuilder> levelStringBuildersMap, long treeDepth) {
        for (long i = 0; i < treeDepth; i++) {
            StringBuilder sb = new StringBuilder();
            long margin = calculatePadding((treeDepth - i) - 1); // margin for current level == padding for next level
            sb.append(multiply(" ", margin * PLACEHOLDER_LENGTH));
            levelStringBuildersMap.put(i, sb);
        }
        levelStringBuildersMap.put(treeDepth, new StringBuilder()); // no margin for last level
    }

    private static void addPaddingsInsteadOfNullNodes(long levelOfNullNode,
                                                      Map<Long, StringBuilder> levelStringBuildersMap,
                                                      long treeDepth) {
        long childMultiplier = 1;
        for (long i = levelOfNullNode; i <= treeDepth; i++) {
            StringBuilder sb = levelStringBuildersMap.get(i);
            long padding = (1 + calculatePadding(treeDepth - i)) * childMultiplier; // (node itself + padding for current level) * number of possible nodes in following subtrees
            sb.append(multiply(" ", padding * PLACEHOLDER_LENGTH));
            childMultiplier *= 2;
        }
    }

    private static long calculatePadding(long invertedLevel) {
        if (invertedLevel == 0) {
            return 1;
        }
        return calculatePadding(invertedLevel - 1) * 2 + 1;
    }

    private static String multiply(String string, long times) {
        StringBuilder result = new StringBuilder();
        for (long i = 0; i < times; i++) {
            result.append(string);
        }
        return result.toString();
    }
}
