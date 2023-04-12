package com.company;

public class Main {

    public static void main(String[] args) {
        BinaryTree<Integer,String> binaryTree = new BinaryTree<>();
        binaryTree.add(4,"4");
        binaryTree.add(4,"1");
        System.out.println("added 4");
        binaryTree.printTree();
        binaryTree.add(6,"4");
        System.out.println("added 6");
        binaryTree.printTree();
        binaryTree.add(5,"4");
        System.out.println("added 5");
        binaryTree.printTree();
        binaryTree.add(7,"4");
        System.out.println("added 7");
        binaryTree.printTree();
        binaryTree.add(2,"4");
        System.out.println("added 2");
        binaryTree.printTree();
        binaryTree.add(3,"4");
        System.out.println("added 3");
        binaryTree.printTree();
        binaryTree.add(1,"4");
        System.out.println("added 1");
        binaryTree.printTree();
        binaryTree.add(15,"4");
        System.out.println("added 15");
        binaryTree.printTree();
        binaryTree.add(8,"4");
        System.out.println("added 8");
        binaryTree.printTree();
        binaryTree.add(17,"4");
        System.out.println("added 17");
        binaryTree.printTree();
        binaryTree.add(-2,"4");
        binaryTree.add(-2,"1");
        System.out.println("added -2");
        binaryTree.printTree();
        binaryTree.add(-5,"4");
        System.out.println("added -5");
        binaryTree.printTree();

        binaryTree.remove(6);
        System.out.println("removed 6");
        binaryTree.printTree();
        binaryTree.remove(5);
        System.out.println("removed 5");
        binaryTree.printTree();
        binaryTree.remove(7);
        System.out.println("removed 7");
        binaryTree.printTree();
        binaryTree.remove(17);
        System.out.println("removed 17");
        binaryTree.printTree();
        try {
            binaryTree.remove("4", false);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        binaryTree.remove("4");
        System.out.println("removed all with value 4");
        binaryTree.printTree();
    }

}