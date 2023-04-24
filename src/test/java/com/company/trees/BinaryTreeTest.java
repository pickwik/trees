package com.company.trees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryTreeTest {

    @Test
    void addOneNode() {
        // given
        int testKey = 0;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey, testValue);

        // then
        var actualNode = tree.getRoot();
        assertNotNull(actualNode);
        assertEquals(testKey, actualNode.getKey());
        assertNotNull(actualNode.getValues());
        assertEquals(1, actualNode.getValues().size());
        assertEquals(testValue, actualNode.getValues().toArray()[0]);
        assertNull(actualNode.getParent());
        assertNull(actualNode.getLeft());
        assertNull(actualNode.getRight());
    }

    @Test
    void addNodeWithNullKeyNegative() {
        // given
        Integer testKey = null;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        Executable add = () -> tree.add(testKey, testValue);

        // then
        var actualException = assertThrows(RuntimeException.class, add);
        assertEquals("Key can't be null", actualException.getMessage());
    }

    @Test
    void addValueToExistingKey() {
        // given
        int testKey = 0;
        List<String> testValues = List.of("a", "b");
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey, testValues.get(0));
        tree.add(testKey, testValues.get(1));

        // then
        var actualNode = tree.getRoot();
        assertNotNull(actualNode);
        assertEquals(testKey, actualNode.getKey());
        assertNotNull(actualNode.getValues());
        assertEquals(2, actualNode.getValues().size());
        assertTrue(actualNode.getValues().containsAll(testValues));
        assertNull(actualNode.getParent());
        assertNull(actualNode.getLeft());
        assertNull(actualNode.getRight());
    }

    @Test
    void addValueLessThanRootWithoutRotations() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // then
        var parentNode = tree.getRoot();
        assertNotNull(parentNode);
        var childNode = parentNode.getLeft();
        assertNotNull(childNode);

        assertNull(parentNode.getParent());
        assertNull(parentNode.getRight());
        assertSame(parentNode, childNode.getParent());

        assertEquals(testKey1, parentNode.getKey());
        assertEquals(testKey2, childNode.getKey());
    }

    @Test
    void addValueMoreThanRootWithoutRotations() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // then
        var parentNode = tree.getRoot();
        assertNotNull(parentNode);
        var childNode = parentNode.getRight();
        assertNotNull(childNode);

        assertNull(parentNode.getParent());
        assertNull(parentNode.getLeft());
        assertSame(parentNode, childNode.getParent());

        assertEquals(testKey1, parentNode.getKey());
        assertEquals(testKey2, childNode.getKey());
    }

    @Test
    void balancingRightRightImbalanceWhenAddingThreeNodes() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        int testKey3 = 2;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);

        //then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var leftChildNode = rootNode.getLeft();
        assertNotNull(leftChildNode);
        var rightChildNode = rootNode.getRight();
        assertNotNull(rightChildNode);

        assertNull(rootNode.getParent());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rootNode, rightChildNode.getParent());

        assertEquals(testKey2, rootNode.getKey());
        assertEquals(testKey1, leftChildNode.getKey());
        assertEquals(testKey3, rightChildNode.getKey());
    }

    @Test
    void balancingRightLeftImbalanceWhenAddingThreeNodes() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        int testKey3 = 2;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey1, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey2, testValue);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var leftChildNode = rootNode.getLeft();
        assertNotNull(leftChildNode);
        var rightChildNode = rootNode.getRight();
        assertNotNull(rightChildNode);

        assertNull(rootNode.getParent());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rootNode, rightChildNode.getParent());

        assertEquals(testKey2, rootNode.getKey());
        assertEquals(testKey1, leftChildNode.getKey());
        assertEquals(testKey3, rightChildNode.getKey());
    }

    @Test
    void balancingLeftLeftImbalanceWhenAddingThreeNodes() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        int testKey3 = 2;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey3, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey1, testValue);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var leftChildNode = rootNode.getLeft();
        assertNotNull(leftChildNode);
        var rightChildNode = rootNode.getRight();
        assertNotNull(rightChildNode);

        assertNull(rootNode.getParent());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rootNode, rightChildNode.getParent());

        assertEquals(testKey2, rootNode.getKey());
        assertEquals(testKey1, leftChildNode.getKey());
        assertEquals(testKey3, rightChildNode.getKey());
    }

    @Test
    void balancingLeftRightImbalanceWhenAddingThreeNodes() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        int testKey3 = 2;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        tree.add(testKey3, testValue);
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var leftChildNode = rootNode.getLeft();
        assertNotNull(leftChildNode);
        var rightChildNode = rootNode.getRight();
        assertNotNull(rightChildNode);

        assertNull(rootNode.getParent());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rootNode, rightChildNode.getParent());

        assertEquals(testKey2, rootNode.getKey());
        assertEquals(testKey1, leftChildNode.getKey());
        assertEquals(testKey3, rightChildNode.getKey());
    }


    @Test
    void removeRootFromTreeWhenRootHasNoChild() {
        // given
        int testKey = 0;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey, testValue);

        // when
        tree.remove(testKey);

        // then
        assertNull(tree.getRoot());
    }

    @Test
    void removeRootFromTreeWithTwoNodesWhenRootHasLeftChild() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // when
        tree.remove(testKey1);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);

        assertNull(rootNode.getParent());
        assertNull(rootNode.getLeft());
        assertNull(rootNode.getRight());

        assertEquals(testKey2, rootNode.getKey());
    }

    @Test
    void removeRootFromTreeWithTwoNodesWhenRootHasRightChild() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        String testValue = "a";
        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // when
        tree.remove(testKey1);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);

        assertNull(rootNode.getParent());
        assertNull(rootNode.getLeft());
        assertNull(rootNode.getRight());

        assertEquals(testKey2, rootNode.getKey());
    }


    @Test
    void getSizeOfEmptyTree() {
        // given
        BinaryTree<Integer, String> tree = new BinaryTree<>();

        // when
        var actualSize = tree.size();

        // then
        assertEquals(0, actualSize);
    }

    @Test
    void getSizeOfTreeWithRootNode() {
        // given
        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(0, "a");

        // when
        var actualSize = tree.size();

        // then
        assertEquals(1, actualSize);
    }

    @Test
    void getSizeOfTreeWithRootNodeAndLeftChild() {
        // given
        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(0, "a");
        tree.add(-1, "a");

        // when
        var actualSize = tree.size();

        // then
        assertEquals(2, actualSize);
    }

    @Test
    void getSizeOfTreeWithRootNodeAndRightChild() {
        // given
        BinaryTree<Integer, String> tree = new BinaryTree<>();
        tree.add(0, "a");
        tree.add(1, "a");

        // when
        var actualSize = tree.size();

        // then
        assertEquals(2, actualSize);
    }
}
