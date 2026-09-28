// Stack (linked implementation) to keep a history of recent actions
public class ActionStack {

    private class Node {
        String action;
        Node next;

        Node(String action) {
            this.action = action;
        }
    }

    private Node top;
    private int size;

    public ActionStack() {
        top = null;
        size = 0;
    }

    public void push(String action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public String pop() {
        if (isEmpty()) return null;
        String action = top.action;
        top = top.next;
        size--;
        return action;
    }

    public String peek() {
        if (isEmpty()) return null;
        return top.action;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    // most recent action is shown first
    public void display() {
        Node current = top;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.action);
            current = current.next;
            count++;
        }
    }
}
