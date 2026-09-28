// Binary Search Tree ordered by Student ID
public class StudentBST {

    private class Node {
        Student data;
        Node left, right;

        Node(Student data) {
            this.data = data;
        }
    }

    private Node root;

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private Node insertRec(Node node, Student student) {
        if (node == null) {
            return new Node(student);
        }
        int cmp = student.getStudentId().compareTo(node.data.getStudentId());
        if (cmp < 0) {
            node.left = insertRec(node.left, student);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, student);
        }
        return node;
    }

    public Student search(String studentId) {
        Node current = root;
        while (current != null) {
            int cmp = studentId.compareTo(current.data.getStudentId());
            if (cmp == 0) return current.data;
            current = (cmp < 0) ? current.left : current.right;
        }
        return null;
    }

    public void delete(String studentId) {
        root = deleteRec(root, studentId);
    }

    private Node deleteRec(Node node, String studentId) {
        if (node == null) return null;

        int cmp = studentId.compareTo(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteRec(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, studentId);
        } else {
            // node with 0 or 1 child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // node with 2 children - replace with inorder successor
            Node successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getStudentId());
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // inorder traversal prints students sorted by ID
    public void displayInOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(Node node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.println(node.data);
            inOrderRec(node.right);
        }
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    public boolean isEmpty() {
        return root == null;
    }
}
