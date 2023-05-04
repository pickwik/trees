package com.company.trees.avl;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class BinaryTreeNode<K, V> {

    private BinaryTreeNode<K, V> parent;
    private K key;
    private Set<V> values;

    private BinaryTreeNode<K, V> right;
    private BinaryTreeNode<K, V> left;


    public BinaryTreeNode(BinaryTreeNode<K, V> parent, K key, V value) {
        this.parent = parent;
        this.key = key;
        values = new HashSet<>();
        values.add(value);
    }

    public void addValue(V value) {
        values.add(value);
    }

    public void removeValue(V value) {
        values.remove(value);
    }

    public boolean hasValue(V value) {
        return values.contains(value);
    }
}
