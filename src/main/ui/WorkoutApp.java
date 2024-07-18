package ui;

import java.util.ArrayList;
import java.util.Scanner;

import exception.InvalidInputException;
import model.Exercise;
import model.ExerciseCollection;
import model.Plan;

//Workout application
public class WorkoutApp {
    private ExerciseCollection exerciseCollection;
    private Plan plan;
    private boolean appState;
    private boolean showDetail;
    private Scanner scanner = new Scanner(System.in);

    // EFFECTS: constructor a workout app
    public WorkoutApp() {
        startApp();
    }

    // MODIFIES: this
    // EFFECTS: start the app and create a exerciseCollection and a exericse plan
    private void startApp() {
        exerciseCollection = new ExerciseCollection();
        plan = new Plan();
        appState = true;
        String input;

        while (appState) {
            mainMenu();
            input = scanner.nextLine();
            try {
                command(input);
            } catch (InvalidInputException e) {
                System.out.println("\nInvalid Input! Please try again!\n");
            }
        }

        System.out.println("\nThank you for using the Workout app!!!");
    }

    // EFFECTS: print the main menu of this app
    private void mainMenu() {
        System.out.println("\nWelcome to Workout Planner! Please select the following: ");
        System.out.println("p - see current plan");
        System.out.println("w - see all possible workouts");
        System.out.println("a - add to workout collection");
        System.out.println("q - quit app");
    }

    // MODIFIES: this
    // EFFECTS: perform different actions base on the user input from the main menu
    private void command(String input) throws InvalidInputException {
        if (input.equalsIgnoreCase("p")) {
            showPlan();
        } else if (input.equalsIgnoreCase("w")) {
            showExercises();
        } else if (input.equalsIgnoreCase("a")) {
            addWorkout();
        } else if (input.equalsIgnoreCase("q")) {
            appState = false;
        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: add a workout to the front of workout collection
    private void addWorkout() {
        System.out.println("\nInput the name of the workout you want add: ");
        String name = scanner.nextLine();

        System.out.println("\nInput the description of workout: ");
        String description = scanner.nextLine();

        boolean wrongInput = true;
        String bodyPart = "";
        while (wrongInput) {
            System.out.println("\nWhich part of body does it exercise: ");
            printBodyPart();
            try {
                bodyPart = whichBodyPart(scanner.nextLine());
                wrongInput = false;
            } catch (InvalidInputException e) {
                System.out.println("\nInvalid Input! Please try again!");
            }
        }

        System.out.println("\nInput the duration in seconds: ");
        int duration = Integer.parseInt(scanner.nextLine());

        Exercise newExer = new Exercise(name, description, bodyPart, duration);
        exerciseCollection.addExercise(0, newExer);

        System.out.println("\nInsertion Complete!");
    }

    // EFFECTS: print all possible body parts related to the exercises
    private void printBodyPart() {
        System.out.println("a - Abs");
        System.out.println("ar - Arm");
        System.out.println("l - Leg");
    }

    // EFFECTS: return the full name of body part based on str
    private String whichBodyPart(String str) throws InvalidInputException {
        String bodyPart = "";
        if (str.equalsIgnoreCase("a")) {
            bodyPart = "Abs";
        } else if (str.equalsIgnoreCase("ar")) {
            bodyPart = "Arm";
        } else if (str.equalsIgnoreCase("l")) {
            bodyPart = "Leg";
        } else {
            throw new InvalidInputException();
        }
        return bodyPart;
    }

    // EFFECTS: print all possible exercises and ask user interact with the
    // exercise;
    private void showExercises() {
        ArrayList<Exercise> exercises = exerciseCollection.getExercises();
        System.out.println("\nHere is the all the workouts in your collection");
        printExerciseList(exercises);

        showDetail = true;
        while (showDetail) {
            System.out.println("\nUse the index number to see each exercise in detail");
            System.out.println("'s' to sort the exercise by body part");
            System.out.println("Or 'q' to return to main menu");
            try {
                showDetail = menu(scanner.nextLine(), exercises);
            } catch (InvalidInputException e) {
                System.out.println("Invalid input! Please try again!");
            }
        }
    }

    // EFFECTS: Complete actions with the collection of exercises base on user input
    // return true if user continue want to interact with the collection and false
    // if not.
    private boolean menu(String nextLine, ArrayList<Exercise> exercises) throws InvalidInputException {
        if (nextLine.equalsIgnoreCase("q")) {
            return false;
        } else if (nextLine.equalsIgnoreCase("s")) {
            initiateSort();
            return false;
        } else if (isNumber(nextLine) && Integer.parseInt(nextLine) <= exercises.size()) {
            int index = Integer.parseInt(nextLine) - 1;
            Exercise e = exercises.get(index);
            showExercisesDetail(e);
            return true;
        } else {
            throw new InvalidInputException();
        }
    }

    // EFFECT: initial and run the sorting process
    private void initiateSort() {
        boolean wrongInput = true;
        ArrayList<Exercise> sortedList = new ArrayList<>();

        while (wrongInput) {
            System.out.println("\nWhich body part do you want to sort it by?");
            printBodyPart();
            try {
                sortedList = sort(scanner.nextLine());
                wrongInput = false;
            } catch (InvalidInputException e) {
                System.out.println("Invalid Input! Please try again!");
            }
        }

        System.out.println("\nHere is the sorted array");
        printExerciseList(sortedList);
        sortedMenu(sortedList);
    }

    // EFFECTS: Sort the exercise collection by body parts from the use input
    private ArrayList<Exercise> sort(String nextLine) throws InvalidInputException {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String bodyPart = whichBodyPart(nextLine);

        for (Exercise e : exerciseCollection.getExercises()) {
            if (e.getBodyPart().equals(bodyPart)) {
                exercises.add(e);
            }
        }
        return exercises;
    }

    // EFFECTS: display this menu when user is displayed with
    // a sorted collection of exercise by body part
    private void sortedMenu(ArrayList<Exercise> sortedList) {
        showDetail = true;
        while (showDetail) {
            System.out.println("\nUse the index number to see each exercise in detail.");
            System.out.println("Or 'q' to return to main menu");
            try {
                showDetail = menu(scanner.nextLine(), sortedList);
            } catch (InvalidInputException e) {
                System.out.println("Invalid Input! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: display current exercise plan and initial user input
    private void showPlan() {
        ArrayList<Exercise> exercises = plan.getExercises();
        if (exercises.isEmpty()) {
            System.out.println("\nYour plan is currently empty.");
        } else {
            printExerciseList(exercises);
        }

        showDetail = true;
        while (showDetail) {
            System.out.println("\n'r' - remove an exercise at an index");
            System.out.println("'a' - add an exercise at an index");
            System.out.println("'q' to return to main menu");
            try {
                editPlan(scanner.nextLine(), exercises);
            } catch (InvalidInputException e) {
                System.out.println("\nInvalid Input! Please try again!\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: edit the plan by adding or removing exercise base on the response on
    // the user or quit back to main menu
    private void editPlan(String nextLine, ArrayList<Exercise> exercises) throws InvalidInputException {
        if (nextLine.equalsIgnoreCase("r")) {
            System.out.println("\nEnter the index of the exercise to remove: ");
            exercises.remove(Integer.parseInt(scanner.nextLine()) - 1);

            System.out.println("\nRemove Successful! Here is the current schedule: ");
            printExerciseList(exercises);
        } else if (nextLine.equalsIgnoreCase("a")) {
            addExercise(exercises);
        } else if (nextLine.equalsIgnoreCase("q")) {
            showDetail = false;
        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: add a exercise from the exerciseCollection to addExerciseList
    private void addExercise(ArrayList<Exercise> addExerciseList) {
        ArrayList<Exercise> collection = exerciseCollection.getExercises();
        System.out.println("\nHere is all the exercise to choose from:");
        printExerciseList(collection);

        System.out.println("\nEnter the index of the exercise from the collection: ");
        int fromIndex = Integer.parseInt(scanner.nextLine()) - 1;
        System.out.println("\nEnter the index of the exercise to add to the plan: ");
        int toIndex = Integer.parseInt(scanner.nextLine()) - 1;

        addExerciseList.add(toIndex, collection.get(fromIndex));
        System.out.println("\nAdd Successful! Here is the current schedule: ");
        printExerciseList(addExerciseList);
    }

    // EFFECTS: print the given list of exercise on the terminal
    private void printExerciseList(ArrayList<Exercise> exercises) {
        int index = 0;
        if (exercises.isEmpty()) {
            System.out.println("Empty List!");
        }

        for (Exercise e : exercises) {
            System.out.println(index + 1 + ". " + e.getName() + ": " + e.getDuration() + " sec");
            index++;
        }
    }

    // EFFECTS: return true if the string is a number and false otherwise
    private boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // EFFECTS: print the details of the exercise to the terminal
    public void showExercisesDetail(Exercise e) {
        System.out.println("\nName: " + e.getName());
        System.out.println("Description: " + e.getDescription());
        System.out.println("Body: " + e.getBodyPart());
        System.out.println("Duration: " + e.getDuration());
    }
}
