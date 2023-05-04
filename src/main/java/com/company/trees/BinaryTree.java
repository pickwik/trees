package com.company.trees;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.trees.Direction.LEFT;
import static com.company.trees.Direction.RIGHT;

public class BinaryTree<K extends Comparable<K>, V> {

    @Getter
    private BinaryTreeNode<K, V> root;


    public BinaryTree() {
        this.root = null;
    }


    public void add(K key, V value) {
        if (key == null) {
            throw new RuntimeException("Key can't be null");
        }

        if (root == null) {
            root = new BinaryTreeNode<>(null, key, value);
        } else {
            addAndBalanceRecursive(root, key, value);
        }
    }

    private void addAndBalanceRecursive(BinaryTreeNode<K, V> node, K key, V value) {
        K nodeKey = node.getKey();
        if (key.compareTo(nodeKey) == 0) {
            node.addValue(value);
        } else {
            if (key.compareTo(nodeKey) < 0) {
                // go left
                if (node.getLeft() != null) {
                    addAndBalanceRecursive(node.getLeft(), key, value);
                } else {
                    node.setLeft(new BinaryTreeNode<>(node, key, value));
                }
            } else if (key.compareTo(nodeKey) > 0) {
                // go right
                if (node.getRight() != null) {
                    addAndBalanceRecursive(node.getRight(), key, value);
                } else {
                    node.setRight(new BinaryTreeNode<>(node, key, value));
                }
            }
            balance(node);
        }
    }


    public void remove(K key) {
        if (root != null) {
            removeByKeyAndBalanceRecursive(root, key);
        }
    }

    public void removeValue(V value) {
        removeValue(value, true);
    }

    public void removeValue(V value, boolean removeFromAll) {
        if (root != null) {
            List<BinaryTreeNode<K, V>> nodesToRemove = searchValue(value);
            if (nodesToRemove.size() > 1 && !removeFromAll) {
                throw new RuntimeException("Multiple nodes found, but 'removeFromAll' flag is false");
            }
            if (!nodesToRemove.isEmpty()) {
                nodesToRemove.forEach(nodeToRemove -> removeByValueAndBalanceRecursive(root, value, nodeToRemove));
            }
        }
    }

    private void removeByKeyAndBalanceRecursive(BinaryTreeNode<K, V> node, K key) {
        K nodeKey = node.getKey();
        if (key.compareTo(nodeKey) == 0) {
            removeNode(node);
        } else {
            if (key.compareTo(nodeKey) < 0) {
                // go left
                if (node.getLeft() != null) {
                    removeByKeyAndBalanceRecursive(node.getLeft(), key);
                }
            } else if (key.compareTo(nodeKey) > 0) {
                // go right
                if (node.getRight() != null) {
                    removeByKeyAndBalanceRecursive(node.getRight(), key);
                }
            }
            balance(node);
        }
    }

    private void removeByValueAndBalanceRecursive(BinaryTreeNode<K,V> node, V value, BinaryTreeNode<K, V> nodeToRemove) {
        if (nodeToRemove == node) {
            node.removeValue(value);
            if (node.getValues().isEmpty()) {
                removeNode(node);
            }
        } else {
            if (node.getLeft() != null) {
                removeByValueAndBalanceRecursive(node.getLeft(), value, nodeToRemove);
            }
            if (node.getRight() != null) {
                removeByValueAndBalanceRecursive(node.getRight(), value, nodeToRemove);
            }
        }
        balance(node);
    }

    private void removeNode(BinaryTreeNode<K, V> node) {
        if (node == null) {
            return;
        }
        boolean isRoot = root == node;
        int numberOfChildNodes = countChildNodes(node);
        switch (numberOfChildNodes) {
            case 0 -> removeNodeWithoutChildNodes(node, isRoot);
            case 1 -> removeNodeWithOneChildNode(node, isRoot);
            case 2 -> removeNodeWithTwoChildNodes(node); // we don't care if node is root as pointers are not modified during this step
        }
    }

    private void removeNodeWithoutChildNodes(BinaryTreeNode<K, V> node, boolean isRoot) {
        if (isRoot) {
            root = null;
        } else {
            BinaryTreeNode<K, V> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
    }

    private void removeNodeWithOneChildNode(BinaryTreeNode<K, V> node, boolean isRoot) {
        BinaryTreeNode<K, V> child;
        if (node.getLeft() != null) {
            child = node.getLeft();
        } else {
            child = node.getRight();
        }

        if (isRoot) {
            root = child;
            child.setParent(null);
        } else {
            BinaryTreeNode<K, V> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
            child.setParent(parent);
        }
    }

    private void removeNodeWithTwoChildNodes(BinaryTreeNode<K, V> node) {
        BinaryTreeNode<K, V> replacingNode = removeSmallestAndBalanceRecursive(node.getRight());
        node.setKey(replacingNode.getKey());
        node.setValues(replacingNode.getValues());
    }

    private BinaryTreeNode<K, V> removeSmallestAndBalanceRecursive(BinaryTreeNode<K, V> node) {
        BinaryTreeNode<K, V> removedNode;
        if (node.getLeft() == null) {
            removeNode(node);
            removedNode = node;
        } else {
            removedNode = removeSmallestAndBalanceRecursive(node.getLeft());
            balance(node);
        }
        return removedNode;
    }


    /**
     * Find node by specified key.
     */
    public BinaryTreeNode<K, V> search(K key) {
        if (root == null) {
            return null;
        }
        return searchByKeyRecursive(root, key);
    }

    /**
     * Find all nodes containing specified value.
     */
    public List<BinaryTreeNode<K, V>> searchValue(V value) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<BinaryTreeNode<K, V>> foundNodes = new ArrayList<>();
        searchByValueRecursive(root, value, foundNodes);
        return foundNodes;
    }

    /**
     * Find node by specified key and value.
     */
    public BinaryTreeNode<K, V> search(K key, V value) {
        BinaryTreeNode<K, V> nodeWithSameKey = search(key);
        if (nodeWithSameKey == null) {
            return null;
        }
        if (nodeWithSameKey.getValues().contains(value)) {
            return nodeWithSameKey;
        }
        return null;
    }

    private BinaryTreeNode<K, V> searchByKeyRecursive(BinaryTreeNode<K, V> node, K key) {
        K nodeKey = node.getKey();
        if (nodeKey.compareTo(key) == 0) {
            return node;
        }
        if (key.compareTo(nodeKey) < 0) {
            // go left
            if (node.getLeft() != null) {
                return searchByKeyRecursive(node.getLeft(), key);
            }
        }
        if (key.compareTo(nodeKey) > 0) {
            // go right
            if (node.getRight() != null) {
                return searchByKeyRecursive(node.getRight(), key);
            }
        }
        return null;
    }

    private void searchByValueRecursive(BinaryTreeNode<K, V> node, V value, List<BinaryTreeNode<K, V>> foundNodes) {
        if (node.hasValue(value)) {
            foundNodes.add(node);
        }
        if (node.getLeft() != null) {
            searchByValueRecursive(node.getLeft(), value, foundNodes);
        }
        if (node.getRight() != null) {
            searchByValueRecursive(node.getRight(), value, foundNodes);
        }
    }


    public void printTree() {
        long treeDepth = calculateDepthRecursive(root);
        new TreePrinter<>(root, treeDepth).print();
    }


    public boolean exists(K key, V value) {
        return search(key, value) != null;
    }


    public long size() {
        if (root == null) {
            return 0;
        }
        return calculateSizeRecursive(root);
    }

    private long calculateSizeRecursive(BinaryTreeNode<K, V> node) {
        long size = 1;
        if (node.getLeft() != null) {
            size += calculateSizeRecursive(node.getLeft());
        }
        if (node.getRight() != null) {
            size += calculateSizeRecursive(node.getRight());
        }
        return size;
    }


    private void balance(BinaryTreeNode<K, V> node) {
        long bf = calculateBalanceFactor(node);
        if (bf > 1) {
            long bfRightChild = calculateBalanceFactor(node.getRight());
            if (bfRightChild < 0) { // bfRightChild == -1
                // r-l
                rotate(node.getRight(), RIGHT);
                rotate(node, LEFT);
            } else {
                // r-r
                rotate(node, LEFT);
            }
        } else if (bf < -1) {
            long bfLeftChild = calculateBalanceFactor(node.getLeft());
            if (bfLeftChild < 0) { // bfLeftChild == -1
                // l-l
                rotate(node, RIGHT);
            } else {
                // l-r
                rotate(node.getLeft(), LEFT);
                rotate(node, RIGHT);
            }
        }
    }

    private long calculateDepthRecursive(BinaryTreeNode<K, V> node) {
        if (node == null) {
            return -1;
        }
        long lDepth = calculateDepthRecursive(node.getLeft());
        long rDepth = calculateDepthRecursive(node.getRight());
        return Math.max(lDepth, rDepth) + 1;
    }

    private long calculateBalanceFactor(BinaryTreeNode<K, V> node) {
        if (node == null) {
            return -1;
        } else {
            return calculateDepthRecursive(node.getRight()) - calculateDepthRecursive(node.getLeft());
        }
    }

    private void rotate(BinaryTreeNode<K, V> subtreeRoot, Direction rotateDirection) {
        BinaryTreeNode<K, V> parent = subtreeRoot.getParent();
        boolean isRoot = parent == null;
        boolean isLeftChild = !isRoot && subtreeRoot == parent.getLeft();
        boolean isRightChild = !isRoot && subtreeRoot == parent.getRight();
        BinaryTreeNode<K, V> newSubtreeRoot;

        switch (rotateDirection) {
            case LEFT -> newSubtreeRoot = leftRotate(subtreeRoot);
            case RIGHT -> newSubtreeRoot = rightRotate(subtreeRoot);
            default -> throw new RuntimeException("Rotating to wrong direction");
        }

        if (isRoot) {
            root = newSubtreeRoot;
            newSubtreeRoot.setParent(null);
        }
        if (isLeftChild) {
            parent.setLeft(newSubtreeRoot);
            newSubtreeRoot.setParent(parent);
        }
        if (isRightChild) {
            parent.setRight(newSubtreeRoot);
            newSubtreeRoot.setParent(parent);
        }
    }

    private BinaryTreeNode<K, V> leftRotate(BinaryTreeNode<K, V> oldSubtreeRoot) {
        BinaryTreeNode<K, V> newSubtreeRoot = oldSubtreeRoot.getRight();
        BinaryTreeNode<K, V> rightChildForOldSubtreeRoot = newSubtreeRoot.getLeft();

        newSubtreeRoot.setLeft(oldSubtreeRoot);
        oldSubtreeRoot.setParent(newSubtreeRoot);

        oldSubtreeRoot.setRight(rightChildForOldSubtreeRoot);
        if (rightChildForOldSubtreeRoot != null) {
            rightChildForOldSubtreeRoot.setParent(oldSubtreeRoot);
        }

        return newSubtreeRoot;
    }

    private BinaryTreeNode<K, V> rightRotate(BinaryTreeNode<K, V> oldSubtreeRoot) {
        BinaryTreeNode<K, V> newSubtreeRoot = oldSubtreeRoot.getLeft();
        BinaryTreeNode<K, V> leftChildForOldSubtreeRoot = newSubtreeRoot.getRight();

        newSubtreeRoot.setRight(oldSubtreeRoot);
        oldSubtreeRoot.setParent(newSubtreeRoot);

        oldSubtreeRoot.setLeft(leftChildForOldSubtreeRoot);
        if (leftChildForOldSubtreeRoot != null) {
            leftChildForOldSubtreeRoot.setParent(oldSubtreeRoot);
        }

        return newSubtreeRoot;
    }

    private int countChildNodes(BinaryTreeNode<K, V> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() != null && node.getRight() != null) {
            return 2;
        } else {
            return 1;
        }
    }
}
