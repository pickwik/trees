package com.company.trees.util;

import com.company.trees.avl.AvlTree;
import com.company.trees.avl.AvlTreeNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class TreePrinter {

    private static final long PLACEHOLDER_LENGTH = 2; // hardcoded for now


    public static void print(AvlTree<?, ?> tree) {
        // init
        AvlTreeNode<?, ?> root = tree.getRoot();
        long treeDepth = tree.getDepth();
        Map<AvlTreeNode<?, ?>, Long> nodeLevelsMap = new HashMap<>();
        initNodeLevelsMapRecursive(nodeLevelsMap, root, 0);
        Map<Long, StringBuilder> levelStringBuildersMap = new HashMap<>();
        initLevelStringBuildersMapWithLeftOffset(levelStringBuildersMap, treeDepth);

        // fill lines
        walkthrough(root, nodeLevelsMap, levelStringBuildersMap, treeDepth, node -> {
            long level = nodeLevelsMap.get(node);
            StringBuilder thisLevelStringBuilder = levelStringBuildersMap.get(level);
            thisLevelStringBuilder.append(String.format("%02d", node.getKey()))
                    .append(multiply(" ", (calculateRightOffset(treeDepth - level) - 1) * PLACEHOLDER_LENGTH));
        });

        // print lines
        levelStringBuildersMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingLong(l -> l)))
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue().toString()));
    }

    private static void walkthrough(AvlTreeNode<?, ?> node,
                                    Map<AvlTreeNode<?, ?>, Long> nodeLevelsMap,
                                    Map<Long, StringBuilder> levelStringBuildersMap,
                                    long treeDepth,
                                    Consumer<AvlTreeNode<?, ?>> function) {
        function.accept(node); // visit node

        long level = nodeLevelsMap.get(node);

        if (node.getLeft() != null) {
            walkthrough(node.getLeft(), nodeLevelsMap, levelStringBuildersMap, treeDepth, function);
        } else if (level < treeDepth) {
            addRightOffsetInsteadOfNullNodes(level + 1, levelStringBuildersMap, treeDepth);
        }

        if (node.getRight() != null) {
            walkthrough(node.getRight(), nodeLevelsMap, levelStringBuildersMap, treeDepth, function);
        } else if (level < treeDepth) {
            addRightOffsetInsteadOfNullNodes(level + 1, levelStringBuildersMap, treeDepth);
        }
    }

    private static void initNodeLevelsMapRecursive(Map<AvlTreeNode<?, ?>, Long> nodeLevelsMap, AvlTreeNode<?, ?> node, long level) {
        nodeLevelsMap.put(node, level++);

        if (node.getLeft() != null) {
            initNodeLevelsMapRecursive(nodeLevelsMap, node.getLeft(), level);
        }
        if (node.getRight() != null) {
            initNodeLevelsMapRecursive(nodeLevelsMap, node.getRight(), level);
        }
    }

    private static void initLevelStringBuildersMapWithLeftOffset(Map<Long, StringBuilder> levelStringBuildersMap, long treeDepth) {
        for (long i = 0; i < treeDepth; i++) {
            StringBuilder sb = new StringBuilder();
            long invertedLevel = treeDepth - i;
            long leftOffset = calculateRightOffset(invertedLevel - 1) - 1; // leftOffset for current level == rightOffset for next level - 1 element
            sb.append(multiply(" ", leftOffset * PLACEHOLDER_LENGTH));
            levelStringBuildersMap.put(i, sb);
        }
        levelStringBuildersMap.put(treeDepth, new StringBuilder()); // no leftOffset for last level
    }

    private static void addRightOffsetInsteadOfNullNodes(long levelOfNullNode,
                                                         Map<Long, StringBuilder> levelStringBuildersMap,
                                                         long treeDepth) {
        long childMultiplier = 1;
        for (long i = levelOfNullNode; i <= treeDepth; i++) {
            StringBuilder sb = levelStringBuildersMap.get(i);
            long rightOffset = calculateRightOffset(treeDepth - i) * childMultiplier; // rightOffset for current level * number of possible nodes in following subtrees
            sb.append(multiply(" ", rightOffset * PLACEHOLDER_LENGTH));
            childMultiplier *= 2;
        }
    }

    private static long calculateRightOffset(long invertedLevel) {
        return (long)Math.pow(2, invertedLevel + 1);
    }

    private static String multiply(String string, long times) {
        StringBuilder result = new StringBuilder();
        for (long i = 0; i < times; i++) {
            result.append(string);
        }
        return result.toString();
    }
}
