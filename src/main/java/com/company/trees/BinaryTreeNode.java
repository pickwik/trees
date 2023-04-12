package com.company.trees;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BinaryTreeNode<K, V> {

    private BinaryTreeNode<K, V> parent;
    @EqualsAndHashCode.Include
    private K key;
    @EqualsAndHashCode.Include
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
