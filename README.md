# University Student Record and Campus Route Management System

**Module:** CIT300 - Data Structures and Algorithms
**Assessment:** Graded Practical Assignment 1 (Week 10)
**Institution:** Sri Lanka Technology Campus (SLTC)

## Group Members

| Name | Student ID | Assigned Responsibility | Individual Contribution |
|------|------------|-------------------------|-------------------------|
| [Member 1 Name] | [ID] | Linked list and student record management | Implemented `Student` and `StudentLinkedList`, add/update/delete/display operations |
| [Member 2 Name] | [ID] | Stack and queue | Implemented `ActionStack`, `RequestQueue`, `ServiceRequest` and the related menu options |
| [Member 3 Name] | [ID] | BST and hashing | Implemented `StudentBST` and `StudentHashTable`, sorted display and ID search |
| [Member 4 Name] | [ID] | Graph and traversal | Implemented `CampusGraph` with adjacency list, location/road operations, BFS and DFS |
| All members | - | Integration and testing | Menu (`Main`), input validation, testing, debugging, documentation |

## Project Description

A Java console application that manages university student records and models the campus as a graph of locations connected by roads. It demonstrates linked lists, stacks, queues, binary search trees, hashing and graphs.

## Data Structures Used

| Data Structure | Class | Purpose |
|----------------|-------|---------|
| Singly Linked List | `StudentLinkedList` | Main storage of student records |
| Stack | `ActionStack` | History of recent actions (most recent first) |
| Queue | `RequestQueue` | Student service requests processed in order of arrival (FIFO) |
| Binary Search Tree | `StudentBST` | Organises students by Student ID, in-order traversal gives sorted output |
| Hash Table (separate chaining) | `StudentHashTable` | Fast search by Student ID |
| Graph (adjacency list) | `CampusGraph` | Campus locations (vertices) and roads (undirected edges), BFS and DFS |

The linked list, stack, queue, BST and hash table are implemented from scratch. The same `Student` object is shared between the linked list, BST and hash table, so an update is reflected in all three.

## Project Structure

```
StudentRecordSystem/
├── README.md
└── src/
    ├── Main.java
    ├── Student.java
    ├── StudentLinkedList.java
    ├── ActionStack.java
    ├── ServiceRequest.java
    ├── RequestQueue.java
    ├── StudentBST.java
    ├── StudentHashTable.java
    └── CampusGraph.java
```

## How to Run

Requires JDK 8 or later.

```
cd src
javac *.java
java Main
```

## Menu

```
 1. Add Student Record
 2. Update Student Record
 3. Delete Student Record
 4. Display All Records using Linked List
 5. Add Service Request to Queue
 6. Process Next Service Request
 7. Display Recent Actions using Stack
 8. Display Students using BST
 9. Search Student using Hashing
10. Add Campus Location
11. Remove Campus Location
12. Add Campus Connection/Road
13. Remove Campus Connection/Road
14. Display Campus Connections
15. Traverse Campus Locations using BFS or DFS
16. Exit
```

## Validation and Error Handling

- Invalid menu choices and non-numeric input
- Duplicate student IDs and invalid ID format
- Names containing numbers or symbols
- Marks outside the range 0 - 100
- Missing student records (update, delete, search, service request)
- Processing a request when the queue is empty
- Duplicate campus locations, missing locations
- Duplicate roads, self-connections and removing a road that does not exist
- Traversal reports locations that are not reachable from the starting point

## Sample Data

Some sample students and campus locations are loaded when the program starts so the features can be demonstrated straight away:

- Students: S001, S002, S004, S005, S008
- Locations: Main Gate, Admin Building, Library, Lecture Hall A, Computer Lab, Cafeteria, Sports Ground
