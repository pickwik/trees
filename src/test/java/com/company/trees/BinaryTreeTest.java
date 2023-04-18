package com.company.trees;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryTreeTest {

    @Test
    void addRoot() {
        int testKey = 0;
        String testValue = "a";

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey, testValue);
        var actualNode = tree.search(testKey);

        assertEquals(testKey, actualNode.getKey());
        assertEquals(1, actualNode.getValues().size());
        assertEquals(testValue, actualNode.getValues().toArray()[0]);
        assertNull(actualNode.getParent());
        assertNull(actualNode.getLeft());
        assertNull(actualNode.getRight());
    }

    @Test
    void addValueToExistingKey() {
        int testKey = 0;
        List<String> testValues = List.of("a", "b");

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey, testValues.get(0));
        tree.add(testKey, testValues.get(1));
        var actualNode = tree.search(testKey);

        assertEquals(testKey, actualNode.getKey());
        assertEquals(2, actualNode.getValues().size());
        assertTrue(actualNode.getValues().containsAll(testValues));
        assertNull(actualNode.getParent());
        assertNull(actualNode.getLeft());
        assertNull(actualNode.getRight());
    }

    @Test
    void addValueLessThanRootWithoutBalancing() {
        int testKey1 = 0;
        int testKey2 = -1;
        String testValue = "a";

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        var parentNode = tree.search(testKey1);
        var childNode = tree.search(testKey2);

        assertSame(childNode, parentNode.getLeft());
        assertSame(parentNode, childNode.getParent());
    }

    @Test
    void addValueMoreThanRootWithoutBalancing() {
        int testKey1 = 0;
        int testKey2 = 1;
        String testValue = "a";

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        var parentNode = tree.search(testKey1);
        var childNode = tree.search(testKey2);

        assertSame(childNode, parentNode.getRight());
        assertSame(parentNode, childNode.getParent());
    }

    @Test
    void balancingRightRightImbalance() {
        int testKey1 = 0;
        int testKey2 = 1;
        int testKey3 = 2;
        String testValue = "a";

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        var rootNode = tree.search(testKey2);
        var leftChildNode = tree.search(testKey1);
        var rightChildNode = tree.search(testKey3);

        assertEquals(testKey2, rootNode.getKey());
        assertEquals(testKey1, leftChildNode.getKey());
        assertEquals(testKey3, rightChildNode.getKey());
        assertNull(rootNode.getParent());
        assertSame(leftChildNode, rootNode.getLeft());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rightChildNode, rootNode.getRight());
        assertSame(rootNode, rightChildNode.getParent());
    }

    @Test
    void balancingRightLeftImbalance() {
        int testKey1 = 0;
        int testKey2 = 2;
        int testKey3 = 1;
        String testValue = "a";

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        var rootNode = tree.search(testKey3);
        var leftChildNode = tree.search(testKey1);
        var rightChildNode = tree.search(testKey2);

        assertEquals(testKey3, rootNode.getKey());
        assertEquals(testKey1, leftChildNode.getKey());
        assertEquals(testKey2, rightChildNode.getKey());
        assertNull(rootNode.getParent());
        assertSame(leftChildNode, rootNode.getLeft());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rightChildNode, rootNode.getRight());
        assertSame(rootNode, rightChildNode.getParent());
    }

    @Test
    void balancingLeftLeftImbalance() {
        int testKey1 = 2;
        int testKey2 = 1;
        int testKey3 = 0;
        String testValue = "a";

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        var rootNode = tree.search(testKey2);
        var leftChildNode = tree.search(testKey3);
        var rightChildNode = tree.search(testKey1);

        assertEquals(testKey2, rootNode.getKey());
        assertEquals(testKey3, leftChildNode.getKey());
        assertEquals(testKey1, rightChildNode.getKey());
        assertNull(rootNode.getParent());
        assertSame(leftChildNode, rootNode.getLeft());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rightChildNode, rootNode.getRight());
        assertSame(rootNode, rightChildNode.getParent());
    }

    @Test
    void balancingLeftRightImbalance() {
        int testKey1 = 2;
        int testKey2 = 0;
        int testKey3 = 1;
        String testValue = "a";

        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        var rootNode = tree.search(testKey3);
        var leftChildNode = tree.search(testKey2);
        var rightChildNode = tree.search(testKey1);

        assertEquals(testKey3, rootNode.getKey());
        assertEquals(testKey2, leftChildNode.getKey());
        assertEquals(testKey1, rightChildNode.getKey());
        assertNull(rootNode.getParent());
        assertSame(leftChildNode, rootNode.getLeft());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rightChildNode, rootNode.getRight());
        assertSame(rootNode, rightChildNode.getParent());
    }
}
