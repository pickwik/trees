package com.company.trees.avl;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AvlTreeNode<K, V> {

    private AvlTreeNode<K, V> parent;
    private K key;
    private Set<V> values;

    private AvlTreeNode<K, V> right;
    private AvlTreeNode<K, V> left;


    public AvlTreeNode(AvlTreeNode<K, V> parent, K key, V value) {
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
