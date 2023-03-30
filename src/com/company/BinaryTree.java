package com.company;

public class BinaryTree<T extends Comparable<T>> {

    private BinaryTreeNode<T> root;


    public BinaryTree() {
        this.root = null;
    }


    public void add(T value) {
        if (root == null) {
            root = new BinaryTreeNode<>(null, value, 0, 0);
        } else {
            addRecursive(root, value);
        }
    }

    private long addRecursive(BinaryTreeNode<T> node, T value) {
        T nodeValue = node.getValue();
        if (nodeValue.compareTo(value) == 0) {
            // handle duplicates later
        }
        if (nodeValue.compareTo(value) > 0) {
            // go left
            if (node.getLeft() != null) {
                long underlyingDepth = addRecursive(node.getLeft(), value);
                node.updateDepthIfGreater(underlyingDepth + 1);
            } else {
                node.setLeft(new BinaryTreeNode<>(node, value, 0));
                node.updateDepthIfGreater(1);
            }
        }
        if (nodeValue.compareTo(value) < 0) {
            // go right
            if (node.getRight() != null) {
                long underlyingDepth = addRecursive(node.getRight(), value);
                node.updateDepthIfGreater(underlyingDepth + 1);
            } else {
                node.setRight(new BinaryTreeNode<>(node, value, 0));
                node.updateDepthIfGreater(1);
            }
        }
        return node.getDepth();
    }


    public void remove(T value) {
        BinaryTreeNode<T> nodeToRemove = search(value);
        removeNode(nodeToRemove);
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
        updateNodeLevels(child, node.getLevel());
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
        new TreePrinter<>(root).print();
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

    /**
     * Updates level values starting from provided node
     */
    private void updateNodeLevels(BinaryTreeNode<T> node, long levelValue) {
        node.setLevel(levelValue++);
        if (node.getLeft() != null) {
            updateNodeLevels(node.getLeft(), levelValue);
        }
        if (node.getRight() != null) {
            updateNodeLevels(node.getRight(), levelValue);
        }
    }

    private BinaryTreeNode<T> findSmallestValueRecursive(BinaryTreeNode<T> node) {
        if (node.getLeft() != null) {
            return findSmallestValueRecursive(node.getLeft());
        }
        return node;
    }
}
