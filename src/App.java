import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App { 

    private static Map<String, List<Integer>> studentGrades = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

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
        System.out.println("2. Add Grade");
        System.out.println("3. Update Grade");
        System.out.println("4. Calculate Average Grade");
        System.out.println("5. Display all students and grades");
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
                addGrade();
                break;
            case 3:
                updateGrade();
                break;
            case 4:
                calculateAverageGrade();
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

    private static void addGrade() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (!studentGrades.containsKey(name)) {
            System.out.println("Student not found.");
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

        studentGrades.get(name).add(grade);
        System.out.println("Grade added successfully.");
    }

    private static void updateGrade() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (!studentGrades.containsKey(name)) {
            System.out.println("Student not found.");
            return;
        }

        List<Integer> grades = studentGrades.get(name);
        if (grades.isEmpty()) {
            System.out.println("No grades found for this student.");
            return;
        }

        System.out.print("Enter the index of the grade to update (0-" + (grades.size() - 1) + "): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid index input.");
            scanner.next();
            return;
        }
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 0 || index >= grades.size()) {
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

        grades.set(index, newGrade);
        System.out.println("Grade updated successfully.");
    }

    private static void calculateAverageGrade() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (!studentGrades.containsKey(name)) {
            System.out.println("Student not found.");
            return;
        }

        List<Integer> grades = studentGrades.get(name);
        if (grades.isEmpty()) {
            System.out.println("No grades found for this student.");
            return;
        }

        double sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        double average = sum / grades.size();
        System.out.println("Average grade for " + name + ": " + average);
    }

    private static void displayAllGrades() {
        if (studentGrades.isEmpty()) {
            System.out.println("No students or grades found.");
            return;
        }
        for (Map.Entry<String, List<Integer>> entry : studentGrades.entrySet()) {
            String name = entry.getKey();
            List<Integer> grades = entry.getValue();
            double average = 0;
            if (!grades.isEmpty()) {
                double sum = 0;
                for (int grade : grades) {
                    sum += grade;
                }
                average = sum / grades.size();
            }
            System.out.println(name + ": " + grades + ", Average: " + average);
        }
    }
}