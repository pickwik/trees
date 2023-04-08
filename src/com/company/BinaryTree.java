package com.company;

import static com.company.Direction.LEFT;
import static com.company.Direction.NONE;
import static com.company.Direction.RIGHT;

public class BinaryTree<T extends Comparable<T>> {

    private BinaryTreeNode<T> root;


    public BinaryTree() {
        this.root = null;
    }


    public void add(T value) {
        if (root == null) {
            root = new BinaryTreeNode<>(null, value, 0);
        } else {
            addRecursive(root, value, new DirectionHolder(NONE));
        }
        updateNodeLevelsRecursive(root, 0);
    }

    private void addRecursive(BinaryTreeNode<T> node, T value, DirectionHolder addedLeafDirection) {
        T nodeValue = node.getValue();
        if (value.compareTo(nodeValue) == 0) {
            node.setNumberOfDuplicates(node.getNumberOfDuplicates() + 1);
        } else {
            if (value.compareTo(nodeValue) < 0) {
                // go left
                if (node.getLeft() != null) {
                    addRecursive(node.getLeft(), value, addedLeafDirection);
                } else {
                    node.setLeft(new BinaryTreeNode<>(node, value));
                    addedLeafDirection.setDirection(LEFT);
                }
            } else if (value.compareTo(nodeValue) > 0) {
                // go right
                if (node.getRight() != null) {
                    addRecursive(node.getRight(), value, addedLeafDirection);
                } else {
                    node.setRight(new BinaryTreeNode<>(node, value));
                    addedLeafDirection.setDirection(RIGHT);
                }
            }
            balance(node, addedLeafDirection.getDirection());
        }
    }

    private void balance(BinaryTreeNode<T> node, Direction addedLeafDirection) {
        if (addedLeafDirection == null || NONE.equals(addedLeafDirection)) {
            throw new RuntimeException("Wrong method usage");
        }

        long bf = calculateBalanceFactor(node);
        if (bf > 1) {
            if (LEFT.equals(addedLeafDirection)) {
                // r-l
                rotate(node.getRight(), RIGHT);
                rotate(node, LEFT);
            } else {
                // r-r
                rotate(node, LEFT);
            }
        } else if (bf < -1) {
            if (LEFT.equals(addedLeafDirection)) {
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
        BinaryTreeNode<T> nodeToRemove = search(value);
        long numberOfDuplicates = nodeToRemove.getNumberOfDuplicates();
        if (numberOfDuplicates > 0) {
            nodeToRemove.setNumberOfDuplicates(numberOfDuplicates - 1);
        } else {
            removeNode(nodeToRemove);
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
        updateNodeLevelsRecursive(child, node.getLevel());
    }

    private void removeNodeWithTwoChildNodes(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> replacingNode = findSmallestValueRecursive(node.getRight());
        node.setValue(replacingNode.getValue());
        removeNode(replacingNode);
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
        if (nodeValue.compareTo(value) > 0) {
            // go left
            if (node.getLeft() != null) {
                return searchRecursive(node.getLeft(), value);
            } else {
                return null;
            }
        }
        if (nodeValue.compareTo(value) < 0) {
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

    /**
     * Updates level values starting from provided node
     */
    private void updateNodeLevelsRecursive(BinaryTreeNode<T> node, long levelValue) {
        node.setLevel(levelValue++);
        if (node.getLeft() != null) {
            updateNodeLevelsRecursive(node.getLeft(), levelValue);
        }
        if (node.getRight() != null) {
            updateNodeLevelsRecursive(node.getRight(), levelValue);
        }
    }

    private BinaryTreeNode<T> findSmallestValueRecursive(BinaryTreeNode<T> node) {
        if (node.getLeft() != null) {
            return findSmallestValueRecursive(node.getLeft());
        }
        return node;
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
