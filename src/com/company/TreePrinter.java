package com.company;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TreePrinter<T> {

    private final BinaryTreeNode<T> root;
    private final long placeholderLength = 2; // hardcoded for now
    private final Map<Long, StringBuilder> levelsMap;


    public TreePrinter(BinaryTreeNode<T> root) {
        this.root = root;
        levelsMap = initLevelsMapWithMargins();
    }


    public void print() {
        walkthrough(root, node -> {
            long level = node.getLevel();
            StringBuilder thisLevelStringBuilder = levelsMap.get(level);
            thisLevelStringBuilder.append(String.format("%02d", node.getValue()))
                    .append(multiply(" ", calculatePadding(root.getDepth() - level) * placeholderLength));
        });
        levelsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingLong(l -> l)))
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue().toString()));
    }

    private void walkthrough(BinaryTreeNode<T> node, Consumer<BinaryTreeNode<T>> function) {
        function.accept(node); // visit node

        if (node.getLeft() != null) {
            walkthrough(node.getLeft(), function);
        } else if (node.getLevel() < root.getDepth()) {
            addPaddingsInsteadOfNullNodes(node.getLevel() + 1);
        }

        if (node.getRight() != null) {
            walkthrough(node.getRight(), function);
        } else if (node.getLevel() < root.getDepth()) {
            addPaddingsInsteadOfNullNodes(node.getLevel() + 1);
        }
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

    private void addPaddingsInsteadOfNullNodes(long levelOfNullNode) {
        long treeDepth = root.getDepth();
        long childMultiplier = 1;
        for (long i = levelOfNullNode; i <= treeDepth; i++) {
            StringBuilder sb = levelsMap.get(i);
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
