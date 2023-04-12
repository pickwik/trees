package com.company;

public class Main {

    public static void main(String[] args) {
        BinaryTree<Integer,String> binaryTree = new BinaryTree<>();
        binaryTree.add(4,"4");
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
        System.out.println("added -2");
        binaryTree.printTree();
        binaryTree.add(-5,"4");
        System.out.println("added -5");
        binaryTree.printTree();

        /*binaryTree.add(12);
        binaryTree.add(13);
        binaryTree.add(14);
        binaryTree.add(44);
        binaryTree.add(55);
        binaryTree.add(66);
        binaryTree.add(15);
        binaryTree.add(16);
        binaryTree.add(17);
        binaryTree.add(1);
        binaryTree.add(2);
        binaryTree.add(3);
        binaryTree.add(7);*/

        //binaryTree.printTree();
        binaryTree.remove("6");
        System.out.println("removed 6");
        binaryTree.printTree();
        binaryTree.remove("5");
        System.out.println("removed 5");
        binaryTree.printTree();
        binaryTree.remove("7");
        System.out.println("removed 7");
        binaryTree.printTree();
        binaryTree.remove("17");
        System.out.println("removed 17");
        binaryTree.printTree();
    }

}