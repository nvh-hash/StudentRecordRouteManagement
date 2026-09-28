import java.util.List;
import java.util.Scanner;

// University Student Record and Campus Route Management System
// CIT300 - Data Structures and Algorithms
public class Main {

    private static Scanner sc = new Scanner(System.in);

    private static StudentLinkedList studentList = new StudentLinkedList();
    private static StudentBST studentTree = new StudentBST();
    private static StudentHashTable studentTable = new StudentHashTable(31);
    private static ActionStack recentActions = new ActionStack();
    private static RequestQueue requestQueue = new RequestQueue();
    private static CampusGraph campus = new CampusGraph();

    private static int nextRequestId = 1;

    public static void main(String[] args) {
        loadSampleData();

        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice (1-16): ", 1, 16);
            System.out.println();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateStudent(); break;
                case 3: deleteStudent(); break;
                case 4: displayAllStudents(); break;
                case 5: addServiceRequest(); break;
                case 6: processServiceRequest(); break;
                case 7: displayRecentActions(); break;
                case 8: displayStudentsBST(); break;
                case 9: searchStudent(); break;
                case 10: addLocation(); break;
                case 11: removeLocation(); break;
                case 12: addConnection(); break;
                case 13: removeConnection(); break;
                case 14: displayConnections(); break;
                case 15: traverseCampus(); break;
                case 16: System.out.println("Exiting the system. Goodbye!"); break;
            }
        } while (choice != 16);

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n========================================================");
        System.out.println("   UNIVERSITY STUDENT RECORD & CAMPUS ROUTE SYSTEM");
        System.out.println("========================================================");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
        System.out.println("--------------------------------------------------------");
    }

    // ---------------- Student record operations ----------------

    private static void addStudent() {
        System.out.println("--- Add Student Record ---");
        String id = readStudentId("Enter Student ID (e.g. S001): ");

        if (studentTable.contains(id)) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }

        String name = readName("Enter Name: ");
        String programme = readText("Enter Programme: ");
        double marks = readMarks("Enter Marks (0-100): ");

        Student student = new Student(id, name, programme, marks);
        studentList.add(student);
        studentTree.insert(student);
        studentTable.put(student);
        recentActions.push("Added student " + id + " (" + name + ")");

        System.out.println("Student record added successfully.");
    }

    private static void updateStudent() {
        System.out.println("--- Update Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        String id = readStudentId("Enter Student ID to update: ");
        Student student = studentTable.get(id);
        if (student == null) {
            System.out.println("Error: No student found with ID " + id + ".");
            return;
        }

        System.out.println("Current record:");
        printStudentHeader();
        System.out.println(student);
        System.out.println("(Press Enter to keep the current value)");

        // name
        while (true) {
            System.out.print("New Name [" + student.getName() + "]: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) break;
            if (isValidName(input)) {
                student.setName(input);
                break;
            }
            System.out.println("Invalid name. Use letters and spaces only.");
        }

        // programme
        System.out.print("New Programme [" + student.getProgramme() + "]: ");
        String programme = sc.nextLine().trim();
        if (!programme.isEmpty()) {
            student.setProgramme(programme);
        }

        // marks
        while (true) {
            System.out.print("New Marks [" + student.getMarks() + "]: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) break;
            try {
                double marks = Double.parseDouble(input);
                if (marks >= 0 && marks <= 100) {
                    student.setMarks(marks);
                    break;
                }
                System.out.println("Invalid marks. Must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // the same Student object is shared by the list, BST and hash table
        // so updating it here updates it everywhere
        recentActions.push("Updated student " + id);
        System.out.println("Student record updated successfully.");
    }

    private static void deleteStudent() {
        System.out.println("--- Delete Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        String id = readStudentId("Enter Student ID to delete: ");
        Student student = studentTable.get(id);
        if (student == null) {
            System.out.println("Error: No student found with ID " + id + ".");
            return;
        }

        studentList.remove(id);
        studentTree.delete(id);
        studentTable.remove(id);
        recentActions.push("Deleted student " + id + " (" + student.getName() + ")");

        System.out.println("Student " + id + " deleted successfully.");
    }

    private static void displayAllStudents() {
        System.out.println("--- All Student Records (Linked List) ---");
        if (studentList.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        printStudentHeader();
        studentList.display();
        System.out.println("Total records: " + studentList.size());
    }

    private static void displayStudentsBST() {
        System.out.println("--- Students Sorted by ID (BST In-order Traversal) ---");
        if (studentTree.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        printStudentHeader();
        studentTree.displayInOrder();
        System.out.println("Tree height: " + studentTree.height());
    }

    private static void searchStudent() {
        System.out.println("--- Search Student (Hashing) ---");
        String id = readStudentId("Enter Student ID to search: ");
        Student student = studentTable.get(id);

        if (student == null) {
            System.out.println("No student found with ID " + id + ".");
        } else {
            System.out.println("Student found (hash index: " + studentTable.hash(id) + ")");
            printStudentHeader();
            System.out.println(student);
        }
        recentActions.push("Searched for student " + id);
    }

    // ---------------- Queue and stack operations ----------------

    private static void addServiceRequest() {
        System.out.println("--- Add Service Request ---");
        String id = readStudentId("Enter Student ID: ");
        if (!studentTable.contains(id)) {
            System.out.println("Error: No student found with ID " + id + ".");
            return;
        }

        String description = readText("Enter request (e.g. Transcript, ID card): ");
        ServiceRequest request = new ServiceRequest(nextRequestId++, id, description);
        requestQueue.enqueue(request);
        recentActions.push("Added service request #" + request.getRequestId() + " for " + id);

        System.out.println("Request added to queue. Position in queue: " + requestQueue.size());
    }

    private static void processServiceRequest() {
        System.out.println("--- Process Next Service Request ---");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }

        ServiceRequest request = requestQueue.dequeue();
        System.out.println("Processing: " + request);
        recentActions.push("Processed service request #" + request.getRequestId());

        System.out.println("Remaining requests: " + requestQueue.size());
        if (!requestQueue.isEmpty()) {
            requestQueue.display();
        }
    }

    private static void displayRecentActions() {
        System.out.println("--- Recent Actions (Stack - most recent first) ---");
        if (recentActions.isEmpty()) {
            System.out.println("No actions recorded yet.");
            return;
        }
        recentActions.display();
    }

    // ---------------- Graph operations ----------------

    private static void addLocation() {
        System.out.println("--- Add Campus Location ---");
        String name = readText("Enter location name: ");
        if (campus.addLocation(name)) {
            recentActions.push("Added campus location " + name);
            System.out.println("Location '" + name + "' added.");
        } else {
            System.out.println("Error: Location '" + name + "' already exists.");
        }
    }

    private static void removeLocation() {
        System.out.println("--- Remove Campus Location ---");
        if (campus.isEmpty()) {
            System.out.println("No campus locations available.");
            return;
        }
        String name = readText("Enter location name to remove: ");
        if (campus.removeLocation(name)) {
            recentActions.push("Removed campus location " + name);
            System.out.println("Location '" + name + "' and its connections removed.");
        } else {
            System.out.println("Error: Location '" + name + "' does not exist.");
        }
    }

    private static void addConnection() {
        System.out.println("--- Add Campus Connection/Road ---");
        if (campus.size() < 2) {
            System.out.println("At least two locations are needed to add a connection.");
            return;
        }
        String from = readText("Enter first location: ");
        String to = readText("Enter second location: ");

        if (!campus.hasLocation(from)) {
            System.out.println("Error: Location '" + from + "' does not exist.");
        } else if (!campus.hasLocation(to)) {
            System.out.println("Error: Location '" + to + "' does not exist.");
        } else if (from.equalsIgnoreCase(to)) {
            System.out.println("Error: A location cannot be connected to itself.");
        } else if (campus.hasConnection(from, to)) {
            System.out.println("Error: These locations are already connected.");
        } else {
            campus.addConnection(from, to);
            recentActions.push("Added road " + from + " <-> " + to);
            System.out.println("Connection added between '" + from + "' and '" + to + "'.");
        }
    }

    private static void removeConnection() {
        System.out.println("--- Remove Campus Connection/Road ---");
        String from = readText("Enter first location: ");
        String to = readText("Enter second location: ");

        if (!campus.hasLocation(from)) {
            System.out.println("Error: Location '" + from + "' does not exist.");
        } else if (!campus.hasLocation(to)) {
            System.out.println("Error: Location '" + to + "' does not exist.");
        } else if (!campus.hasConnection(from, to)) {
            System.out.println("Error: There is no connection between these locations.");
        } else {
            campus.removeConnection(from, to);
            recentActions.push("Removed road " + from + " <-> " + to);
            System.out.println("Connection removed.");
        }
    }

    private static void displayConnections() {
        System.out.println("--- Campus Network (Adjacency List) ---");
        if (campus.isEmpty()) {
            System.out.println("No campus locations available.");
            return;
        }
        campus.display();

        System.out.print("\nEnter a location to view its neighbours (or press Enter to skip): ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) return;

        List<String> neighbours = campus.getNeighbours(name);
        if (neighbours == null) {
            System.out.println("Error: Location '" + name + "' does not exist.");
        } else if (neighbours.isEmpty()) {
            System.out.println("'" + name + "' has no connected locations.");
        } else {
            System.out.println("Locations connected to '" + campus.findLocation(name) + "': " + String.join(", ", neighbours));
        }
    }

    private static void traverseCampus() {
        System.out.println("--- Traverse Campus Locations ---");
        if (campus.isEmpty()) {
            System.out.println("No campus locations available.");
            return;
        }

        String start = readText("Enter starting location: ");
        if (!campus.hasLocation(start)) {
            System.out.println("Error: Location '" + start + "' does not exist.");
            return;
        }

        System.out.println("1. Breadth First Search (BFS)");
        System.out.println("2. Depth First Search (DFS)");
        int type = readInt("Choose traversal type: ", 1, 2);

        List<String> order;
        if (type == 1) {
            order = campus.bfs(start);
            System.out.println("BFS order: " + String.join(" -> ", order));
        } else {
            order = campus.dfs(start);
            System.out.println("DFS order: " + String.join(" -> ", order));
        }

        if (order.size() < campus.size()) {
            System.out.println("Note: " + (campus.size() - order.size()) + " location(s) are not reachable from '" + start + "'.");
        }
        recentActions.push((type == 1 ? "BFS" : "DFS") + " traversal from " + start);
    }

    // ---------------- Input helpers ----------------

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static double readMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double marks = Double.parseDouble(input);
                if (marks >= 0 && marks <= 100) {
                    return marks;
                }
                System.out.println("Invalid marks. Must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static String readStudentId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String id = sc.nextLine().trim().toUpperCase();
            if (id.matches("[A-Z0-9]{2,10}")) {
                return id;
            }
            System.out.println("Invalid ID. Use 2-10 letters/digits with no spaces.");
        }
    }

    private static String readName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String name = sc.nextLine().trim();
            if (isValidName(name)) {
                return name;
            }
            System.out.println("Invalid name. Use letters and spaces only.");
        }
    }

    private static boolean isValidName(String name) {
        return name.matches("[A-Za-z .]+") && !name.isBlank();
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static void printStudentHeader() {
        System.out.println(String.format("%-10s %-22s %-18s %6s", "ID", "Name", "Programme", "Marks"));
        System.out.println("-----------------------------------------------------------");
    }

    // ---------------- Sample data for testing/demo ----------------

    private static void loadSampleData() {
        Student[] samples = {
            new Student("S005", "Nimal Perera", "Computer Science", 78.5),
            new Student("S002", "Kavindi Silva", "Software Eng", 85.0),
            new Student("S008", "Ruwan Fernando", "Data Science", 64.0),
            new Student("S001", "Ishara Jayasinghe", "Computer Science", 91.0),
            new Student("S004", "Tharindu Bandara", "Cyber Security", 55.5)
        };
        for (Student s : samples) {
            studentList.add(s);
            studentTree.insert(s);
            studentTable.put(s);
        }

        String[] locations = {"Main Gate", "Admin Building", "Library", "Lecture Hall A",
                              "Computer Lab", "Cafeteria", "Sports Ground"};
        for (String loc : locations) {
            campus.addLocation(loc);
        }
        campus.addConnection("Main Gate", "Admin Building");
        campus.addConnection("Main Gate", "Cafeteria");
        campus.addConnection("Admin Building", "Library");
        campus.addConnection("Library", "Lecture Hall A");
        campus.addConnection("Lecture Hall A", "Computer Lab");
        campus.addConnection("Cafeteria", "Sports Ground");
        campus.addConnection("Cafeteria", "Lecture Hall A");

        recentActions.push("Loaded sample data");
    }
}
