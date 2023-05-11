package com.company.trees.avl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvlTreeTest {

    @Test
    void addOneNode() {
        // given
        int testKey = 0;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
        AvlTree<Integer, String> tree = new AvlTree<>();

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
    void balancingRightRightImbalanceWhenNewSubtreeRootHasLeftChild() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        int testKey3 = 2;
        int testKey4 = 1;
        int testKey5 = 3;
        int testKey6 = 4;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();

        // when
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);
        tree.add(testKey5, testValue);
        tree.add(testKey6, testValue);

        // then
        var root = tree.getRoot();
        assertNotNull(root);
        var leftOfRoot = root.getLeft();
        assertNotNull(leftOfRoot);
        var rightOfRoot = root.getRight();
        assertNotNull(rightOfRoot);
        var leftOfLeftOfRoot = leftOfRoot.getLeft();
        assertNotNull(leftOfLeftOfRoot);
        var rightOfLeftOfRoot = leftOfRoot.getRight();
        assertNotNull(rightOfLeftOfRoot);
        var rightOfRightOfRoot = rightOfRoot.getRight();
        assertNotNull(rightOfRightOfRoot);

        assertNull(root.getParent());
        assertSame(root, leftOfRoot.getParent());
        assertSame(root, rightOfRoot.getParent());
        assertSame(leftOfRoot, leftOfLeftOfRoot.getParent());
        assertSame(leftOfRoot, rightOfLeftOfRoot.getParent());
        assertSame(rightOfRoot, rightOfRightOfRoot.getParent());

        assertEquals(testKey3, root.getKey());
        assertEquals(testKey1, leftOfRoot.getKey());
        assertEquals(testKey5, rightOfRoot.getKey());
        assertEquals(testKey2, leftOfLeftOfRoot.getKey());
        assertEquals(testKey4, rightOfLeftOfRoot.getKey());
        assertEquals(testKey6, rightOfRightOfRoot.getKey());
    }

    @Test
    void balancingLeftLeftImbalanceWhenNewSubtreeRootHasRightChild() {
        // given
        int testKey1 = 0;
        int testKey2 = -2;
        int testKey3 = 1;
        int testKey4 = -3;
        int testKey5 = -1;
        int testKey6 = -4;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();

        // when
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);
        tree.add(testKey5, testValue);
        tree.add(testKey6, testValue);

        // then
        var root = tree.getRoot();
        assertNotNull(root);
        var leftOfRoot = root.getLeft();
        assertNotNull(leftOfRoot);
        var rightOfRoot = root.getRight();
        assertNotNull(rightOfRoot);
        var leftOfLeftOfRoot = leftOfRoot.getLeft();
        assertNotNull(leftOfLeftOfRoot);
        var leftOfRightOfRoot = rightOfRoot.getLeft();
        assertNotNull(leftOfRightOfRoot);
        var rightOfRightOfRoot = rightOfRoot.getRight();
        assertNotNull(rightOfRightOfRoot);

        assertNull(root.getParent());
        assertSame(root, leftOfRoot.getParent());
        assertSame(root, rightOfRoot.getParent());
        assertSame(leftOfRoot, leftOfLeftOfRoot.getParent());
        assertSame(rightOfRoot, leftOfRightOfRoot.getParent());
        assertSame(rightOfRoot, rightOfRightOfRoot.getParent());

        assertEquals(testKey2, root.getKey());
        assertEquals(testKey4, leftOfRoot.getKey());
        assertEquals(testKey1, rightOfRoot.getKey());
        assertEquals(testKey6, leftOfLeftOfRoot.getKey());
        assertEquals(testKey5, leftOfRightOfRoot.getKey());
        assertEquals(testKey3, rightOfRightOfRoot.getKey());
    }


    @Test
    void searchByKeyInEmptyTree() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();

        // when
        var foundNode = tree.search(0);

        // then
        assertNull(foundNode);
    }

    @Test
    void searchRootByKey() {
        // given
        int testKey = 0;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey, testValue);

        // when
        var foundNode = tree.search(testKey);

        // then
        assertNotNull(foundNode);
        assertEquals(testKey, foundNode.getKey());
    }

    @Test
    void searchByKeyNodeInLeftSubtree() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // when
        var foundNode = tree.search(testKey2);

        // then
        assertNotNull(foundNode);
        assertEquals(testKey2, foundNode.getKey());
    }

    @Test
    void searchByKeyNodeInRightSubtree() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // when
        var foundNode = tree.search(testKey2);

        // then
        assertNotNull(foundNode);
        assertEquals(testKey2, foundNode.getKey());
    }

    @Test
    void searchByValueInEmptyTree() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();

        // when
        var foundNodes = tree.searchValue("a");

        // then
        assertEquals(Collections.emptyList(), foundNodes);
    }

    @Test
    void searchByValueRootWithLeftAndRightChild() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        int testKey3 = 1;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);

        // when
        var foundNodes = tree.searchValue("a");

        // then
        assertNotNull(foundNodes);
        assertEquals(3, foundNodes.size());

        var rootNode = foundNodes.get(0);
        assertNotNull(rootNode);
        var leftChildNode = rootNode.getLeft();
        assertNotNull(leftChildNode);
        var rightChildNode = rootNode.getRight();
        assertNotNull(rightChildNode);

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, leftChildNode.getKey());
        assertEquals(testKey3, rightChildNode.getKey());

        assertNotNull(rootNode.getValues());
        assertNotNull(leftChildNode.getValues());
        assertNotNull(rightChildNode.getValues());

        assertEquals(1, rootNode.getValues().size());
        assertEquals(1, leftChildNode.getValues().size());
        assertEquals(1, rightChildNode.getValues().size());

        assertTrue(rootNode.getValues().contains(testValue));
        assertTrue(leftChildNode.getValues().contains(testValue));
        assertTrue(rightChildNode.getValues().contains(testValue));
    }

    @Test
    void searchByKeyAndValueInEmptyTree() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();

        // when
        var foundNode = tree.search(0, "a");

        // then
        assertNull(foundNode);
    }

    @Test
    void searchByKeyAndValueWhenKeyNotFound() {
        // given
        int testKey = 0;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey, testValue);

        // when
        var foundNode = tree.search(1, "a");

        // then
        assertNull(foundNode);
    }

    @Test
    void searchByKeyAndValueWhenValueNotFound() {
        // given
        int testKey = 0;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey, testValue);

        // when
        var foundNode = tree.search(0, "b");

        // then
        assertNull(foundNode);
    }

    @Test
    void searchByKeyAndValue() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        List<String> testValues = List.of("a", "b");
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValues.get(0));
        tree.add(testKey1, testValues.get(1));
        tree.add(testKey2, testValues.get(0));

        // when
        var foundNode = tree.search(testKey1, testValues.get(0));

        // then
        assertNotNull(foundNode);
        assertEquals(testKey1, foundNode.getKey());
        assertNotNull(foundNode.getValues());
        assertEquals(2, foundNode.getValues().size());
        assertTrue(foundNode.getValues().containsAll(testValues));
    }


    @Test
    void removeRootFromTreeWhenRootHasNoChild() {
        // given
        int testKey = 0;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
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
        AvlTree<Integer, String> tree = new AvlTree<>();
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
        AvlTree<Integer, String> tree = new AvlTree<>();
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
    void removeLeftLeafFromTreeWithTwoNodes() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // when
        tree.remove(testKey2);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);

        assertNull(rootNode.getParent());
        assertNull(rootNode.getLeft());
        assertNull(rootNode.getRight());

        assertEquals(testKey1, rootNode.getKey());
    }

    @Test
    void removeRightLeafFromTreeWithTwoNodes() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);

        // when
        tree.remove(testKey2);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);

        assertNull(rootNode.getParent());
        assertNull(rootNode.getLeft());
        assertNull(rootNode.getRight());

        assertEquals(testKey1, rootNode.getKey());
    }

    @Test
    void removeLeftChildOfRootWhenChildHasLeftChild() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        int testKey3 = -1;
        int testKey4 = -2;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);

        // when
        tree.remove(testKey3);

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

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey4, leftChildNode.getKey());
        assertEquals(testKey2, rightChildNode.getKey());
    }

    @Test
    void removeLeftChildOfRootWhenChildHasRightChild() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        int testKey3 = -2;
        int testKey4 = -1;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);

        // when
        tree.remove(testKey3);

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

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey4, leftChildNode.getKey());
        assertEquals(testKey2, rightChildNode.getKey());
    }

    @Test
    void removeRightChildOfRootWhenChildHasLeftChild() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        int testKey3 = 2;
        int testKey4 = 1;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);

        // when
        tree.remove(testKey3);

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

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, leftChildNode.getKey());
        assertEquals(testKey4, rightChildNode.getKey());
    }

    @Test
    void removeRightChildOfRootWhenChildHasRightChild() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        int testKey3 = 1;
        int testKey4 = 2;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);

        // when
        tree.remove(testKey3);

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

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, leftChildNode.getKey());
        assertEquals(testKey4, rightChildNode.getKey());
    }

    @Test
    void removeNodeWithTwoChildNodesWhenDirectChildIsSmallest() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        int testKey3 = 2;
        int testKey4 = 1;
        int testKey5 = 3;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);
        tree.add(testKey5, testValue);

        // when
        tree.remove(testKey3);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var leftChildNode = rootNode.getLeft();
        assertNotNull(leftChildNode);
        var rightChildNode = rootNode.getRight();
        assertNotNull(rightChildNode);
        var leftChildOfRightNode = rightChildNode.getLeft();
        assertNotNull(leftChildOfRightNode);

        assertNull(rootNode.getParent());
        assertSame(rootNode, leftChildNode.getParent());
        assertSame(rootNode, rightChildNode.getParent());
        assertSame(rightChildNode, leftChildOfRightNode.getParent());

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, leftChildNode.getKey());
        assertEquals(testKey5, rightChildNode.getKey());
        assertEquals(testKey4, leftChildOfRightNode.getKey());
    }

    @Test
    void removeNodeWithTwoChildNodesWhenDirectChildIsNotSmallest() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        int testKey3 = 3;
        int testKey4 = -2;
        int testKey5 = 2;
        int testKey6 = 5;
        int testKey7 = 4;
        String testValue = "a";
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValue);
        tree.add(testKey2, testValue);
        tree.add(testKey3, testValue);
        tree.add(testKey4, testValue);
        tree.add(testKey5, testValue);
        tree.add(testKey6, testValue);
        tree.add(testKey7, testValue);

        // when
        tree.remove(testKey3);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var leftChildOfRoot = rootNode.getLeft();
        assertNotNull(leftChildOfRoot);
        var rightChildOfRoot = rootNode.getRight();
        assertNotNull(rightChildOfRoot);
        var leftChildOfLeftNode = leftChildOfRoot.getLeft();
        assertNotNull(leftChildOfLeftNode);
        var leftChildOfRightNode = rightChildOfRoot.getLeft();
        assertNotNull(leftChildOfRightNode);
        var rightChildOfRightNode = rightChildOfRoot.getRight();
        assertNotNull(rightChildOfRightNode);

        assertNull(rootNode.getParent());
        assertSame(rootNode, leftChildOfRoot.getParent());
        assertSame(rootNode, rightChildOfRoot.getParent());
        assertSame(leftChildOfRoot, leftChildOfLeftNode.getParent());
        assertSame(rightChildOfRoot, leftChildOfRightNode.getParent());
        assertSame(rightChildOfRoot, rightChildOfRightNode.getParent());

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, leftChildOfRoot.getKey());
        assertEquals(testKey7, rightChildOfRoot.getKey());
        assertEquals(testKey4, leftChildOfLeftNode.getKey());
        assertEquals(testKey5, leftChildOfRightNode.getKey());
        assertEquals(testKey6, rightChildOfRightNode.getKey());
    }

    @Test
    void removeValueFromOnlyOneNodeNegative() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        List<String> testValues = List.of("a", "b");
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValues.get(0));
        tree.add(testKey1, testValues.get(1));
        tree.add(testKey2, testValues.get(0));

        // when
        Executable removeValue = () -> tree.removeValue(testValues.get(0), false);

        // then
        var actualException = assertThrows(RuntimeException.class, removeValue);
        assertEquals("Multiple nodes found, but 'removeFromAll' flag is false", actualException.getMessage());
    }

    @Test
    void removeValueFromOnlyRootNodeWithoutNodeRemoval() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        List<String> testValues = List.of("a", "b");
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValues.get(0));
        tree.add(testKey1, testValues.get(1));
        tree.add(testKey2, testValues.get(0));

        // when
        tree.removeValue(testValues.get(1), false);

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var childNode = rootNode.getRight();
        assertNotNull(childNode);

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, childNode.getKey());
        assertNotNull(rootNode.getValues());
        assertNotNull(childNode.getValues());
        assertEquals(1, rootNode.getValues().size());
        assertEquals(1, childNode.getValues().size());
        assertTrue(rootNode.getValues().contains(testValues.get(0)));
        assertTrue(childNode.getValues().contains(testValues.get(0)));
    }

    @Test
    void removeValueFromNodesWithNodeRemoval() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        List<String> testValues = List.of("a", "b");
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValues.get(0));
        tree.add(testKey2, testValues.get(0));
        tree.add(testKey2, testValues.get(1));

        // when
        tree.removeValue(testValues.get(0));

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);

        assertEquals(testKey2, rootNode.getKey());
        assertNotNull(rootNode.getValues());
        assertEquals(1, rootNode.getValues().size());
        assertTrue(rootNode.getValues().contains(testValues.get(1)));
    }

    @Test
    void removeValueInLeftSubtree() {
        // given
        int testKey1 = 0;
        int testKey2 = -1;
        List<String> testValues = List.of("a", "b");
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValues.get(0));
        tree.add(testKey2, testValues.get(0));
        tree.add(testKey2, testValues.get(1));

        // when
        tree.removeValue(testValues.get(1));

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var leftChildNode = rootNode.getLeft();
        assertNotNull(leftChildNode);

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, leftChildNode.getKey());
        assertNotNull(rootNode.getValues());
        assertNotNull(leftChildNode.getValues());
        assertEquals(1, rootNode.getValues().size());
        assertEquals(1, leftChildNode.getValues().size());
        assertTrue(rootNode.getValues().contains(testValues.get(0)));
        assertTrue(leftChildNode.getValues().contains(testValues.get(0)));
    }

    @Test
    void removeValueInRightSubtree() {
        // given
        int testKey1 = 0;
        int testKey2 = 1;
        List<String> testValues = List.of("a", "b");
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(testKey1, testValues.get(0));
        tree.add(testKey2, testValues.get(0));
        tree.add(testKey2, testValues.get(1));

        // when
        tree.removeValue(testValues.get(1));

        // then
        var rootNode = tree.getRoot();
        assertNotNull(rootNode);
        var rightChildNode = rootNode.getRight();
        assertNotNull(rightChildNode);

        assertEquals(testKey1, rootNode.getKey());
        assertEquals(testKey2, rightChildNode.getKey());
        assertNotNull(rootNode.getValues());
        assertNotNull(rightChildNode.getValues());
        assertEquals(1, rootNode.getValues().size());
        assertEquals(1, rightChildNode.getValues().size());
        assertTrue(rootNode.getValues().contains(testValues.get(0)));
        assertTrue(rightChildNode.getValues().contains(testValues.get(0)));
    }


    @Test
    void getSizeOfEmptyTree() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();

        // when
        var actualSize = tree.size();

        // then
        assertEquals(0, actualSize);
    }

    @Test
    void getSizeOfTreeWithRootNode() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(0, "a");

        // when
        var actualSize = tree.size();

        // then
        assertEquals(1, actualSize);
    }

    @Test
    void getSizeOfTreeWithRootNodeAndLeftChild() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();
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
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(0, "a");
        tree.add(1, "a");

        // when
        var actualSize = tree.size();

        // then
        assertEquals(2, actualSize);
    }


    @Test
    void checkNodeNotExists() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(0, "a");

        // when
        var isNodeExists = tree.exists(1, "a");

        // then
        assertFalse(isNodeExists);
    }

    @Test
    void checkNodeExists() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(0, "a");

        // when
        var isNodeExists = tree.exists(0, "a");

        // then
        assertTrue(isNodeExists);
    }

    @Test
    void getDepth() {
        // given
        AvlTree<Integer, String> tree = new AvlTree<>();
        tree.add(0, "a");

        // when
        var actualDepth = tree.getDepth();

        // then
        assertEquals(0, actualDepth);
    }
}
