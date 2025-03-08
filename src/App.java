import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static Map<String, List<Assignment>> studentGrades = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    static class Assignment {
        String type;
        int grade;
        double weight;

        public Assignment(String type, int grade, double weight) {
            this.type = type;
            this.grade = grade;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + type + ": " + grade + ", weight: " + weight + ")";
        }
    }

    private static final Map<String, Double> ASSIGNMENT_WEIGHTS = new HashMap<>() {{
        put("Homework", 0.15);
        put("Exams", 0.2);
        put("Reading", 0.1);
        put("Groupwork", 0.15);
        put("Projects", 0.4);
    }};

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoice();
            processChoice(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("\nStudent Grade Book Menu:");
        System.out.println("1. Add Student");
        System.out.println("2. Add Assignment");
        System.out.println("3. Update Assignment");
        System.out.println("4. Calculate Weighted Average Grade");
        System.out.println("5. Display all students and assignments");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void processChoice(int choice) {
        scanner.nextLine();

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                addAssignment();
                break;
            case 3:
                updateAssignment();
                break;
            case 4:
                calculateWeightedAverageGrade();
                break;
            case 5:
                displayAllGrades();
                break;
            case 6:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (studentGrades.containsKey(name)) {
            System.out.println("Student already exists.");
        } else {
            studentGrades.put(name, new ArrayList<>());
            System.out.println("Student added successfully.");
        }
    }

    private static void addAssignment() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (!studentGrades.containsKey(name)) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Select assignment type:");
        for (String type : ASSIGNMENT_WEIGHTS.keySet()) {
            System.out.println("- " + type);
        }
        System.out.print("Enter assignment type: ");
        String type = scanner.nextLine();

        if (!ASSIGNMENT_WEIGHTS.containsKey(type)) {
            System.out.println("Invalid assignment type.");
            return;
        }

        System.out.print("Enter grade: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid grade input.");
            scanner.next();
            return;
        }
        int grade = scanner.nextInt();
        scanner.nextLine();

        double weight = ASSIGNMENT_WEIGHTS.get(type);

        studentGrades.get(name).add(new Assignment(type, grade, weight));
        System.out.println("Assignment added successfully.");
    }

    private static void updateAssignment() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (!studentGrades.containsKey(name)) {
            System.out.println("Student not found.");
            return;
        }

        List<Assignment> assignments = studentGrades.get(name);
        if (assignments.isEmpty()) {
            System.out.println("No assignments found for this student.");
            return;
        }

        System.out.print("Enter the index of the assignment to update (0-" + (assignments.size() - 1) + "): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid index input.");
            scanner.next();
            return;
        }
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 0 || index >= assignments.size()) {
            System.out.println("Invalid index.");
            return;
        }

        System.out.print("Enter the new grade: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid grade input.");
            scanner.next();
            return;
        }
        int newGrade = scanner.nextInt();
        scanner.nextLine();

        Assignment updatedAssignment = assignments.get(index);
        updatedAssignment.grade = newGrade;
        System.out.println("Assignment updated successfully.");
    }

    private static void calculateWeightedAverageGrade() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (!studentGrades.containsKey(name)) {
            System.out.println("Student not found.");
            return;
        }

        List<Assignment> assignments = studentGrades.get(name);
        if (assignments.isEmpty()) {
            System.out.println("No assignments found for this student.");
            return;
        }

        double weightedSum = 0;
        double totalWeight = 0;
        for (Assignment assignment : assignments) {
            weightedSum += assignment.grade * assignment.weight;
            totalWeight += assignment.weight;
        }

        if (totalWeight == 0) {
            System.out.println("Total weight is zero, cannot calculate average.");
            return;
        }

        double weightedAverage = weightedSum / totalWeight;
        System.out.println("Weighted average grade for " + name + ": " + weightedAverage);
    }

    private static void displayAllGrades() {
        if (studentGrades.isEmpty()) {
            System.out.println("No students or assignments found.");
            return;
        }
        for (Map.Entry<String, List<Assignment>> entry : studentGrades.entrySet()) {
            String name = entry.getKey();
            List<Assignment> assignments = entry.getValue();
            double weightedAverage = 0;
            double weightedSum = 0;
            double totalWeight = 0;

            for(Assignment a : assignments){
                weightedSum += a.grade * a.weight;
                totalWeight += a.weight;
            }

            if(totalWeight > 0){
                weightedAverage = weightedSum / totalWeight;
            }

            System.out.println(name + ": " + assignments + ", Weighted Average: " + weightedAverage);
        }
    }
}