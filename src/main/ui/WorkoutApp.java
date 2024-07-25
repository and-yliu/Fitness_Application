package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import exception.InvalidInputException;
import model.Exercise;
import model.ExerciseCollection;
import model.Plan;
import persistence.JsonReader;
import persistence.JsonWriter;

//Workout application
public class WorkoutApp {
    private static final String JSON_FILE_PLAN = "./data/plan.json";
    private static final String JSON_FILE_COLLECTION = "./data/collection.json";
    private ExerciseCollection exerciseCollection;
    private Plan plan;
    private JsonWriter jsonWriterPlan;
    private JsonReader jsonReaderPlan;
    private JsonWriter jsonWriterCol;
    private JsonReader jsonReaderCol;
    private boolean appState;
    private Scanner scanner;

    // EFFECTS: constructor a workout console ui application
    public WorkoutApp() {
        scanner = new Scanner(System.in);
        jsonWriterPlan = new JsonWriter(JSON_FILE_PLAN);
        jsonReaderPlan = new JsonReader(JSON_FILE_PLAN);
        jsonWriterCol = new JsonWriter(JSON_FILE_COLLECTION);
        jsonReaderCol = new JsonReader(JSON_FILE_COLLECTION);
        exerciseCollection = new ExerciseCollection();
        plan = new Plan();
        exerciseCollection.addDefaultExercise();
        startApp();
    }

    // MODIFIES: this
    // EFFECTS: start the app and create a exerciseCollection and a exericse plan
    private void startApp() {
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
        System.out.println("s - save current workout plan");
        System.out.println("l - load saved workout plan");
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
        } else if (input.equalsIgnoreCase("s")) {
            saveSchedule();
        } else if (input.equalsIgnoreCase("l")) {
            loadSchedule();
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

    // EFFECTS: print all possible exercises and ask user interact with them
    private void showExercises() {
        ArrayList<Exercise> exercises = exerciseCollection.getExercises();
        System.out.println("\nHere is the all the workouts in your collection");
        printExerciseList(exercises);

        boolean showDetail = true;
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
            Exercise exercise = exercises.get(index);
            showExercisesDetail(exercise);
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

        for (Exercise exercise : exerciseCollection.getExercises()) {
            if (exercise.getBodyPart().equals(bodyPart)) {
                exercises.add(exercise);
            }
        }
        return exercises;
    }

    // EFFECTS: display this menu when user is displayed with
    // a sorted collection of exercise by body part
    private void sortedMenu(ArrayList<Exercise> sortedList) {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\nUse the index number to see each exercise in detail.");
            System.out.println("Or 'q' to return to main menu");
            try {
                keepGoing = menu(scanner.nextLine(), sortedList);
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
            System.out.println("\n");
            printExerciseList(exercises);
        }

        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\n'r' - remove an exercise at an index");
            System.out.println("'a' - add an exercise at an index");
            System.out.println("'q' to return to main menu");
            try {
                keepGoing = editPlan(scanner.nextLine(), exercises);
            } catch (InvalidInputException e) {
                System.out.println("\nInvalid Input! Please try again!\n");
            }
        }
    }

    // MODIFIES: this AND exercises
    // EFFECTS: edit the plan by adding or removing exercise base on the response on
    // the user or quit back to main menu
    private boolean editPlan(String nextLine, ArrayList<Exercise> exercises) throws InvalidInputException {
        if (nextLine.equalsIgnoreCase("r")) {
            System.out.println("\nEnter the index of the exercise to remove: ");
            exercises.remove(Integer.parseInt(scanner.nextLine()) - 1);

            System.out.println("\nRemove Successful! Here is the current schedule: ");
            printExerciseList(exercises);

            return true;

        } else if (nextLine.equalsIgnoreCase("a")) {
            addExercise(exercises);
            return true;

        } else if (nextLine.equalsIgnoreCase("q")) {
            return false;

        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this AND addExerciseList
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

        for (Exercise exercise : exercises) {
            System.out.println(index + 1 + ". " + exercise.getName() + ": " + exercise.getDuration() + " sec");
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
    public void showExercisesDetail(Exercise exercise) {
        System.out.println("\nName: " + exercise.getName());
        System.out.println("Description: " + exercise.getDescription());
        System.out.println("Body: " + exercise.getBodyPart());
        System.out.println("Duration: " + exercise.getDuration());
    }

    // EFFECTS: saves both the plan and exerciseCollection to their files
    private void saveSchedule() {
        try {
            jsonWriterPlan.open();
            ;
            jsonWriterPlan.write(plan);
            jsonWriterPlan.close();

            jsonWriterCol.open();
            jsonWriterCol.write(exerciseCollection);
            jsonWriterCol.close();

            System.out.println("Saved plan to file");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    // MODIFIES: this
    // EFFECTS: load plan and exerciseCollection from the files
    private void loadSchedule() {
        try {
            plan = jsonReaderPlan.readPlan();
            exerciseCollection = jsonReaderCol.readCollection();
            System.out.println("Loaded saved plan from file");
        } catch (IOException e) {
            System.out.println("Unable to read from file");
        }
    }

}