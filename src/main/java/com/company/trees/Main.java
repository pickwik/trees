package com.company.trees;

import com.company.trees.avl.BinaryTree;
import com.company.trees.util.TreePrinter;

public class Main {

    public static void main(String[] args) {
        BinaryTree<Integer,String> binaryTree = new BinaryTree<>();
        binaryTree.add(4,"4");
        binaryTree.add(4,"1");
        System.out.println("added 4");
        TreePrinter.print(binaryTree);
        binaryTree.add(6,"4");
        System.out.println("added 6");
        TreePrinter.print(binaryTree);
        binaryTree.add(5,"4");
        System.out.println("added 5");
        TreePrinter.print(binaryTree);
        binaryTree.add(7,"4");
        System.out.println("added 7");
        TreePrinter.print(binaryTree);
        binaryTree.add(2,"4");
        System.out.println("added 2");
        TreePrinter.print(binaryTree);
        binaryTree.add(3,"4");
        System.out.println("added 3");
        TreePrinter.print(binaryTree);
        binaryTree.add(1,"4");
        System.out.println("added 1");
        TreePrinter.print(binaryTree);
        binaryTree.add(15,"4");
        System.out.println("added 15");
        TreePrinter.print(binaryTree);
        binaryTree.add(8,"4");
        System.out.println("added 8");
        TreePrinter.print(binaryTree);
        binaryTree.add(17,"4");
        System.out.println("added 17");
        TreePrinter.print(binaryTree);
        binaryTree.add(-2,"4");
        binaryTree.add(-2,"1");
        System.out.println("added -2");
        TreePrinter.print(binaryTree);
        binaryTree.add(-5,"4");
        System.out.println("added -5");
        TreePrinter.print(binaryTree);

        binaryTree.remove(6);
        System.out.println("removed 6");
        TreePrinter.print(binaryTree);
        binaryTree.remove(5);
        System.out.println("removed 5");
        TreePrinter.print(binaryTree);
        binaryTree.remove(7);
        System.out.println("removed 7");
        TreePrinter.print(binaryTree);
        binaryTree.remove(17);
        System.out.println("removed 17");
        TreePrinter.print(binaryTree);
        try {
            binaryTree.removeValue("4", false);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        binaryTree.removeValue("4");
        System.out.println("removed all with value 4");
        TreePrinter.print(binaryTree);
    }

}