// Singly linked list used as the main storage for student records
public class StudentLinkedList {

    private class Node {
        Student data;
        Node next;

        Node(Student data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public StudentLinkedList() {
        head = null;
        size = 0;
    }

    // add a new student to the end of the list
    public void add(Student student) {
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public Student find(String studentId) {
        Node current = head;
        while (current != null) {
            if (current.data.getStudentId().equals(studentId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String studentId) {
        if (head == null) return false;

        if (head.data.getStudentId().equals(studentId)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.getStudentId().equals(studentId)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}
