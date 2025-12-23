import java.util.Scanner;

// Student Class
class Student {
    int rollNo;
    String name;
    int age;
    String department;
    float cgpa;
    boolean isActive;
    
    Student() {
        this.isActive = false;
    }
    
    Student(int rollNo, String name, int age, String department, float cgpa) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.department = department;
        this.cgpa = cgpa;
        this.isActive = true;
    }
    
    void display() {
        System.out.printf("%-8d | %-20s | %-4d | %-15s | %.2f\n", 
                         rollNo, name, age, department, cgpa);
    }
}

// Node for Linked List
class Node {
    int data;
    Node next;
    
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// Stack Implementation
class Stack {
    private int[] arr;
    private int top;
    private int maxSize;
    
    Stack(int size) {
        maxSize = size;
        arr = new int[maxSize];
        top = -1;
    }
    
    void push(int value) {
        if (top >= maxSize - 1) {
            System.out.println("Stack is full");
            return;
        }
        arr[++top] = value;
    }
    
    int pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return -1;
        }
        return arr[top--];
    }
    
    boolean isEmpty() {
        return top == -1;
    }
}

// Queue Implementation
class Queue {
    private int[] arr;
    private int front, rear, count, maxSize;
    
    Queue(int size) {
        maxSize = size;
        arr = new int[maxSize];
        front = 0;
        rear = -1;
        count = 0;
    }
    
    void enqueue(int value) {
        if (count >= maxSize) {
            System.out.println("Queue is full");
            return;
        }
        rear = (rear + 1) % maxSize;
        arr[rear] = value;
        count++;
    }
    
    int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        int value = arr[front];
        front = (front + 1) % maxSize;
        count--;
        return value;
    }
    
    boolean isEmpty() {
        return count == 0;
    }
    
    int size() {
        return count;
    }
}

// Hash Table Implementation
class HashTable {
    private Node[] table;
    private int size;
    
    HashTable(int size) {
        this.size = size;
        table = new Node[size];
    }
    
    private int hashFunction(int key) {
        return key % size;
    }
    
    void insert(int rollNo, int index) {
        int hashIndex = hashFunction(rollNo);
        Node newNode = new Node(index);
        
        if (table[hashIndex] == null) {
            table[hashIndex] = newNode;
        } else {
            Node temp = table[hashIndex];
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }
    
    int search(int rollNo) {
        int hashIndex = hashFunction(rollNo);
        Node temp = table[hashIndex];
        
        while (temp != null) {
            if (temp.data >= 0) {
                return temp.data;
            }
            temp = temp.next;
        }
        return -1;
    }
    
    void remove(int rollNo) {
        int hashIndex = hashFunction(rollNo);
        table[hashIndex] = null;
    }
}

// Student Management System
class StudentManagementSystem {
    private Student[] students;
    private int totalStudents;
    private int maxStudents;
    private HashTable hashTable;
    private Stack undoStack;
    private Queue admissionQueue;
    
    StudentManagementSystem(int maxSize) {
        maxStudents = maxSize;
        students = new Student[maxStudents];
        totalStudents = 0;
        hashTable = new HashTable(maxSize);
        undoStack = new Stack(50);
        admissionQueue = new Queue(50);
        
        for (int i = 0; i < maxStudents; i++) {
            students[i] = new Student();
        }
    }
    
    // Add Student
    void addStudent(int rollNo, String name, int age, String dept, float cgpa) {
        if (totalStudents >= maxStudents) {
            System.out.println("Maximum student limit reached");
            return;
        }
        
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].isActive && students[i].rollNo == rollNo) {
                System.out.println("Student with Roll No " + rollNo + " already exists");
                return;
            }
        }
        
        students[totalStudents] = new Student(rollNo, name, age, dept, cgpa);
        hashTable.insert(rollNo, totalStudents);
        undoStack.push(totalStudents);
        totalStudents++;
        
        System.out.println("Student added successfully");
    }
    
    // Delete Student
    void deleteStudent(int rollNo) {
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].isActive && students[i].rollNo == rollNo) {
                students[i].isActive = false;
                hashTable.remove(rollNo);
                System.out.println("Student deleted successfully");
                return;
            }
        }
        System.out.println("Student not found");
    }
    
    // Search by Roll No using Hash Table
    void searchByRollNo(int rollNo) {
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].isActive && students[i].rollNo == rollNo) {
                System.out.println("\nStudent Found:");
                System.out.println("Roll No    : " + students[i].rollNo);
                System.out.println("Name       : " + students[i].name);
                System.out.println("Age        : " + students[i].age);
                System.out.println("Department : " + students[i].department);
                System.out.println("CGPA       : " + students[i].cgpa);
                return;
            }
        }
        System.out.println("Student not found");
    }
    
    // Linear Search by Name
    void searchByName(String name) {
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].isActive && students[i].name.equalsIgnoreCase(name)) {
                System.out.println("\nStudent Found:");
                System.out.println("Roll No    : " + students[i].rollNo);
                System.out.println("Name       : " + students[i].name);
                System.out.println("Age        : " + students[i].age);
                System.out.println("Department : " + students[i].department);
                System.out.println("CGPA       : " + students[i].cgpa);
                return;
            }
        }
        System.out.println("Student not found");
    }
    
    // Display All Students
    void displayAll() {
        int activeCount = 0;
        
        System.out.println("\nAll Students:");
        System.out.printf("%-8s | %-20s | %-4s | %-15s | CGPA\n", 
                         "Roll No", "Name", "Age", "Department");
        System.out.println("------------------------------------------------------------------------");
        
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].isActive) {
                students[i].display();
                activeCount++;
            }
        }
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Total Students: " + activeCount);
    }
    
    // Bubble Sort by Roll No
    void sortByRollNo() {
        for (int i = 0; i < totalStudents - 1; i++) {
            for (int j = 0; j < totalStudents - i - 1; j++) {
                if (students[j].isActive && students[j + 1].isActive && 
                    students[j].rollNo > students[j + 1].rollNo) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        System.out.println("Students sorted by Roll No");
    }
    
    // Bubble Sort by Name
    void sortByName() {
        for (int i = 0; i < totalStudents - 1; i++) {
            for (int j = 0; j < totalStudents - i - 1; j++) {
                if (students[j].isActive && students[j + 1].isActive && 
                    students[j].name.compareTo(students[j + 1].name) > 0) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        System.out.println("Students sorted by Name");
    }
    
    // Sort by CGPA
    void sortByCGPA() {
        for (int i = 0; i < totalStudents - 1; i++) {
            for (int j = 0; j < totalStudents - i - 1; j++) {
                if (students[j].isActive && students[j + 1].isActive && 
                    students[j].cgpa < students[j + 1].cgpa) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        System.out.println("Students sorted by CGPA");
    }
    
    // Undo Last Addition
    void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("No operations to undo");
            return;
        }
        
        int lastIndex = undoStack.pop();
        if (lastIndex >= 0 && lastIndex < totalStudents) {
            students[lastIndex].isActive = false;
            System.out.println("Last operation undone");
        }
    }
    
    // Admission Queue Operations
    void addToQueue(int rollNo) {
        admissionQueue.enqueue(rollNo);
        System.out.println("Roll No " + rollNo + " added to admission queue");
    }
    
    void processQueue() {
        if (admissionQueue.isEmpty()) {
            System.out.println("Admission queue is empty");
            return;
        }
        
        int rollNo = admissionQueue.dequeue();
        System.out.println("Processing admission for Roll No: " + rollNo);
    }
    
    void displayQueue() {
        if (admissionQueue.isEmpty()) {
            System.out.println("Admission queue is empty");
        } else {
            System.out.println("Students in queue: " + admissionQueue.size());
        }
    }
    
    // Display by Department
    void displayByDepartment(String dept) {
        int count = 0;
        
        System.out.println("\nStudents in " + dept + " Department:");
        System.out.printf("%-8s | %-20s | %-4s | CGPA\n", "Roll No", "Name", "Age");
        System.out.println("--------------------------------------------------------");
        
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].isActive && students[i].department.equalsIgnoreCase(dept)) {
                System.out.printf("%-8d | %-20s | %-4d | %.2f\n",
                                students[i].rollNo, students[i].name, 
                                students[i].age, students[i].cgpa);
                count++;
            }
        }
        
        System.out.println("--------------------------------------------------------");
        if (count == 0) {
            System.out.println("No students found in " + dept + " department");
        } else {
            System.out.println("Total: " + count + " students");
        }
    }
    
    // Show Statistics
    void showStatistics() {
        int activeCount = 0;
        float totalCGPA = 0;
        float maxCGPA = 0;
        float minCGPA = 10.0f;
        
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].isActive) {
                activeCount++;
                totalCGPA += students[i].cgpa;
                if (students[i].cgpa > maxCGPA) maxCGPA = students[i].cgpa;
                if (students[i].cgpa < minCGPA) minCGPA = students[i].cgpa;
            }
        }
        
        System.out.println("\nSystem Statistics:");
        System.out.println("Total Students     : " + activeCount);
        if (activeCount > 0) {
            System.out.printf("Average CGPA       : %.2f\n", (totalCGPA / activeCount));
            System.out.printf("Highest CGPA       : %.2f\n", maxCGPA);
            System.out.printf("Lowest CGPA        : %.2f\n", minCGPA);
        }
        System.out.println("Queue Size         : " + admissionQueue.size());
    }
}

// Main Class
public class StudentManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem(100);
        
        // Add sample data
        System.out.println("Loading sample data...\n");
        sms.addStudent(101, "Alice Johnson", 20, "CSE", 8.5f);
        sms.addStudent(102, "Bob Smith", 21, "ECE", 7.8f);
        sms.addStudent(103, "Charlie Brown", 19, "CSE", 9.2f);
        sms.addStudent(104, "Diana Prince", 22, "MECH", 8.9f);
        sms.addStudent(105, "Eve Adams", 20, "CSE", 7.5f);
        
        int choice;
        
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   STUDENT MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println(" 1.  Add Student");
            System.out.println(" 2.  Delete Student");
            System.out.println(" 3.  Search by Roll No");
            System.out.println(" 4.  Search by Name");
            System.out.println(" 5.  Display All Students");
            System.out.println(" 6.  Sort Students");
            System.out.println(" 7.  Undo Last Operation");
            System.out.println(" 8.  Add to Admission Queue");
            System.out.println(" 9.  Process Admission Queue");
            System.out.println(" 10. Display Queue Status");
            System.out.println(" 11. Display by Department");
            System.out.println(" 12. Show Statistics");
            System.out.println(" 13. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.println("\nAdd New Student");
                    System.out.print("Enter Roll No: ");
                    int rollNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter CGPA: ");
                    float cgpa = sc.nextFloat();
                    sms.addStudent(rollNo, name, age, dept, cgpa);
                    break;
                    
                case 2:
                    System.out.println("\nDelete Student");
                    System.out.print("Enter Roll No: ");
                    rollNo = sc.nextInt();
                    sms.deleteStudent(rollNo);
                    break;
                    
                case 3:
                    System.out.println("\nSearch by Roll No");
                    System.out.print("Enter Roll No: ");
                    rollNo = sc.nextInt();
                    sms.searchByRollNo(rollNo);
                    break;
                    
                case 4:
                    System.out.println("\nSearch by Name");
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    name = sc.nextLine();
                    sms.searchByName(name);
                    break;
                    
                case 5:
                    sms.displayAll();
                    break;
                    
                case 6:
                    System.out.println("\nSort Students");
                    System.out.println("1. By Roll No");
                    System.out.println("2. By Name");
                    System.out.println("3. By CGPA");
                    System.out.print("Enter choice: ");
                    int sortChoice = sc.nextInt();
                    if (sortChoice == 1) sms.sortByRollNo();
                    else if (sortChoice == 2) sms.sortByName();
                    else if (sortChoice == 3) sms.sortByCGPA();
                    else System.out.println("Invalid choice");
                    break;
                    
                case 7:
                    sms.undo();
                    break;
                    
                case 8:
                    System.out.println("\nAdd to Admission Queue");
                    System.out.print("Enter Roll No: ");
                    rollNo = sc.nextInt();
                    sms.addToQueue(rollNo);
                    break;
                    
                case 9:
                    sms.processQueue();
                    break;
                    
                case 10:
                    sms.displayQueue();
                    break;
                    
                case 11:
                    System.out.println("\nDisplay by Department");
                    sc.nextLine();
                    System.out.print("Enter Department: ");
                    dept = sc.nextLine();
                    sms.displayByDepartment(dept);
                    break;
                    
                case 12:
                    sms.showStatistics();
                    break;
                    
                case 13:
                    System.out.println("\nThank you for using Student Management System");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}