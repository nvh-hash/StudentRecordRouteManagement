// Queue (linked implementation) - requests are handled in order of arrival (FIFO)
public class RequestQueue {

    private class Node {
        ServiceRequest data;
        Node next;

        Node(ServiceRequest data) {
            this.data = data;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public RequestQueue() {
        front = rear = null;
        size = 0;
    }

    public void enqueue(ServiceRequest request) {
        Node newNode = new Node(request);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public ServiceRequest dequeue() {
        if (isEmpty()) return null;
        ServiceRequest request = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return request;
    }

    public ServiceRequest peek() {
        if (isEmpty()) return null;
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public void display() {
        Node current = front;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
