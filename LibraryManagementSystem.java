import java.util.Scanner;

// Node class for AVL Tree
class AVLNode {
    String title;
    int height;
    AVLNode left, right;

    public AVLNode(String title) {
        this.title = title;
        this.height = 1;
    }
}

// AVL Tree implementation
class AVLTree {
    AVLNode root;

    int height(AVLNode N) {
        return (N == null) ? 0 : N.height;
    }

    int getBalance(AVLNode N) {
        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    AVLNode insert(AVLNode node, String title) {
        if (node == null) return new AVLNode(title);
        if (title.compareTo(node.title) < 0)
            node.left = insert(node.left, title);
        else if (title.compareTo(node.title) > 0)
            node.right = insert(node.right, title);
        else return node; // Duplicates not allowed

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && title.compareTo(node.left.title) < 0)
            return rotateRight(node);
        if (balance < -1 && title.compareTo(node.right.title) > 0)
            return rotateLeft(node);
        if (balance > 1 && title.compareTo(node.left.title) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && title.compareTo(node.right.title) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    void inOrder(AVLNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.title);
            inOrder(node.right);
        }
    }

    boolean binarySearch(AVLNode node, String title) {
        if (node == null) return false;
        if (node.title.equals(title)) return true;
        return (title.compareTo(node.title) < 0)
            ? binarySearch(node.left, title)
            : binarySearch(node.right, title);
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVLTree library = new AVLTree();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nLibrary Book Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    library.root = library.insert(library.root, title);
                    System.out.println("Book added successfully!");
                    break;
                case 2:
                    System.out.println("\nBooks in Library:");
                    library.inOrder(library.root);
                    break;
                case 3:
                    System.out.print("Enter book title to search: ");
                    String searchTitle = scanner.nextLine();
                    boolean found = library.binarySearch(library.root, searchTitle);
                    System.out.println(found ? "Book found!" : "Book not found!");
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
        scanner.close();
    }
}