// Hash table using separate chaining for fast student ID lookup
public class StudentHashTable {

    private class Entry {
        String key;
        Student value;
        Entry next;

        Entry(String key, Student value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry[] table;
    private int capacity;
    private int size;

    public StudentHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    public int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % capacity;
        }
        return hash;
    }

    public void put(Student student) {
        String key = student.getStudentId();
        int index = hash(key);
        Entry current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = student; // update existing
                return;
            }
            current = current.next;
        }

        // insert at the start of the chain
        Entry newEntry = new Entry(key, student);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
    }

    public Student get(String key) {
        int index = hash(key);
        Entry current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String key) {
        int index = hash(key);
        Entry current = table[index];
        Entry prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }
}
