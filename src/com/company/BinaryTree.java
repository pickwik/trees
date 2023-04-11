package com.company;

import static com.company.Direction.LEFT;
import static com.company.Direction.RIGHT;

public class BinaryTree<T extends Comparable<T>> {

    private BinaryTreeNode<T> root;


    public BinaryTree() {
        this.root = null;
    }


    public void add(T value) {
        if (root == null) {
            root = new BinaryTreeNode<>(null, value);
        } else {
            addAndBalanceRecursive(root, value);
        }
    }

    private void addAndBalanceRecursive(BinaryTreeNode<T> node, T value) {
        T nodeValue = node.getValue();
        if (value.compareTo(nodeValue) == 0) {
            node.setNumberOfDuplicates(node.getNumberOfDuplicates() + 1);
        } else {
            if (value.compareTo(nodeValue) < 0) {
                // go left
                if (node.getLeft() != null) {
                    addAndBalanceRecursive(node.getLeft(), value);
                } else {
                    node.setLeft(new BinaryTreeNode<>(node, value));
                }
            } else if (value.compareTo(nodeValue) > 0) {
                // go right
                if (node.getRight() != null) {
                    addAndBalanceRecursive(node.getRight(), value);
                } else {
                    node.setRight(new BinaryTreeNode<>(node, value));
                }
            }
            balance(node);
        }
    }

    private void balance(BinaryTreeNode<T> node) {
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


    public void remove(T value) {
        if (root != null) {
            removeAndBalanceRecursive(root, value);
        }
    }

    private void removeAndBalanceRecursive(BinaryTreeNode<T> node, T value) {
        T nodeValue = node.getValue();
        if (value.compareTo(nodeValue) == 0) {
            long numberOfDuplicates = node.getNumberOfDuplicates();
            if (numberOfDuplicates > 0) {
                node.setNumberOfDuplicates(numberOfDuplicates - 1); // todo: fix unnecessary balancing after return
            } else {
                removeNode(node);
            }
        } else {
            if (value.compareTo(nodeValue) < 0) {
                // go left
                if (node.getLeft() != null) {
                    removeAndBalanceRecursive(node.getLeft(), value);
                }
            } else if (value.compareTo(nodeValue) > 0) {
                // go right
                if (node.getRight() != null) {
                    removeAndBalanceRecursive(node.getRight(), value);
                }
            }
            balance(node);
        }
    }

    private void removeNode(BinaryTreeNode<T> node) {
        if (node == null) {
            return;
        }
        int numberOfChildNodes = countChildNodes(node);
        switch (numberOfChildNodes) {
            case 0 -> removeNodeWithoutChildNodes(node);
            case 1 -> removeNodeWithOneChildNode(node);
            case 2 -> removeNodeWithTwoChildNodes(node);
        }
    }

    private void removeNodeWithoutChildNodes(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> parent = node.getParent();
        if (parent.getLeft() == node) {
            parent.setLeft(null);
        } else {
            parent.setRight(null);
        }
    }

    private void removeNodeWithOneChildNode(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> parent = node.getParent();
        BinaryTreeNode<T> child;
        if (node.getLeft() != null) {
            child = node.getLeft();
        } else {
            child = node.getRight();
        }
        if (parent.getLeft() == node) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
        child.setParent(parent);
    }

    private void removeNodeWithTwoChildNodes(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> replacingNode = removeSmallestAndBalanceRecursive(node.getRight());
        node.setValue(replacingNode.getValue());
        node.setNumberOfDuplicates(replacingNode.getNumberOfDuplicates());
    }

    private BinaryTreeNode<T> removeSmallestAndBalanceRecursive(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> removedNode;
        if (node.getLeft() == null) {
            removeNode(node);
            removedNode = node;
        } else {
            removedNode = removeSmallestAndBalanceRecursive(node.getLeft());
            balance(node);
        }
        return removedNode;
    }

    public BinaryTreeNode<T> search(T value) {
        if (root == null) {
            return null;
        }
        return searchRecursive(root, value);
    }

    private BinaryTreeNode<T> searchRecursive(BinaryTreeNode<T> node, T value) {
        T nodeValue = node.getValue();
        if (nodeValue.compareTo(value) == 0) {
            return node;
        }
        if (value.compareTo(nodeValue) < 0) {
            // go left
            if (node.getLeft() != null) {
                return searchRecursive(node.getLeft(), value);
            } else {
                return null;
            }
        }
        if (value.compareTo(nodeValue) > 0) {
            // go right
            if (node.getRight() != null) {
                return searchRecursive(node.getRight(), value);
            } else {
                return null;
            }
        }
        return null;
    }


    public void printTree() {
        long treeDepth = calculateDepthRecursive(root);
        new TreePrinter<>(root, treeDepth).print();
    }


    private int countChildNodes(BinaryTreeNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() != null && node.getRight() != null) {
            return 2;
        } else {
            return 1;
        }
    }

    private long calculateDepthRecursive(BinaryTreeNode<T> node) {
        if (node == null) {
            return -1;
        }
        long lDepth = calculateDepthRecursive(node.getLeft());
        long rDepth = calculateDepthRecursive(node.getRight());
        return Math.max(lDepth, rDepth) + 1;
    }


    private long calculateBalanceFactor(BinaryTreeNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return calculateDepthRecursive(node.getRight()) - calculateDepthRecursive(node.getLeft());
        }
    }

    private void rotate(BinaryTreeNode<T> subtreeRoot, Direction rotateDirection) {
        BinaryTreeNode<T> parent = subtreeRoot.getParent();
        boolean isRoot = parent == null;
        boolean isLeftChild = !isRoot && subtreeRoot == parent.getLeft();
        boolean isRightChild = !isRoot && subtreeRoot == parent.getRight();
        BinaryTreeNode<T> newSubtreeRoot;

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

    private BinaryTreeNode<T> leftRotate(BinaryTreeNode<T> oldSubtreeRoot) {
        BinaryTreeNode<T> newSubtreeRoot = oldSubtreeRoot.getRight();
        BinaryTreeNode<T> rightChildForOldSubtreeRoot = newSubtreeRoot.getLeft();

        newSubtreeRoot.setLeft(oldSubtreeRoot);
        oldSubtreeRoot.setParent(newSubtreeRoot);

        oldSubtreeRoot.setRight(rightChildForOldSubtreeRoot);
        if (rightChildForOldSubtreeRoot != null) {
            rightChildForOldSubtreeRoot.setParent(oldSubtreeRoot);
        }

        return newSubtreeRoot;
    }

    private BinaryTreeNode<T> rightRotate(BinaryTreeNode<T> oldSubtreeRoot) {
        BinaryTreeNode<T> newSubtreeRoot = oldSubtreeRoot.getLeft();
        BinaryTreeNode<T> leftChildForOldSubtreeRoot = newSubtreeRoot.getRight();

        newSubtreeRoot.setRight(oldSubtreeRoot);
        oldSubtreeRoot.setParent(newSubtreeRoot);

        oldSubtreeRoot.setLeft(leftChildForOldSubtreeRoot);
        if (leftChildForOldSubtreeRoot != null) {
            leftChildForOldSubtreeRoot.setParent(oldSubtreeRoot);
        }

        return newSubtreeRoot;
    }
}
