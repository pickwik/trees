package com.company.trees;

import com.company.trees.avl.AvlTree;
import com.company.trees.util.TreePrinter;

public class Main {

    public static void main(String[] args) {
        AvlTree<Integer,String> avlTree = new AvlTree<>();
        avlTree.add(4,"4");
        avlTree.add(4,"1");
        System.out.println("added 4");
        TreePrinter.print(avlTree);
        avlTree.add(6,"4");
        System.out.println("added 6");
        TreePrinter.print(avlTree);
        avlTree.add(5,"4");
        System.out.println("added 5");
        TreePrinter.print(avlTree);
        avlTree.add(7,"4");
        System.out.println("added 7");
        TreePrinter.print(avlTree);
        avlTree.add(2,"4");
        System.out.println("added 2");
        TreePrinter.print(avlTree);
        avlTree.add(3,"4");
        System.out.println("added 3");
        TreePrinter.print(avlTree);
        avlTree.add(1,"4");
        System.out.println("added 1");
        TreePrinter.print(avlTree);
        avlTree.add(15,"4");
        System.out.println("added 15");
        TreePrinter.print(avlTree);
        avlTree.add(8,"4");
        System.out.println("added 8");
        TreePrinter.print(avlTree);
        avlTree.add(17,"4");
        System.out.println("added 17");
        TreePrinter.print(avlTree);
        avlTree.add(-2,"4");
        avlTree.add(-2,"1");
        System.out.println("added -2");
        TreePrinter.print(avlTree);
        avlTree.add(-5,"4");
        System.out.println("added -5");
        TreePrinter.print(avlTree);

        avlTree.remove(6);
        System.out.println("removed 6");
        TreePrinter.print(avlTree);
        avlTree.remove(5);
        System.out.println("removed 5");
        TreePrinter.print(avlTree);
        avlTree.remove(7);
        System.out.println("removed 7");
        TreePrinter.print(avlTree);
        avlTree.remove(17);
        System.out.println("removed 17");
        TreePrinter.print(avlTree);
        try {
            avlTree.removeValue("4", false);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        avlTree.removeValue("4");
        System.out.println("removed all with value 4");
        TreePrinter.print(avlTree);
    }

}