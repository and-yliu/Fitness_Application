package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Exercise;
import model.ExerciseDatabase;
import model.Plan;

public class WorkoutApp {
    private ExerciseDatabase exerciseChoice;
    private Plan plan;
    private boolean appState;
    private boolean showDetail;
    private Scanner scanner = new Scanner(System.in);

    public WorkoutApp() {
        startApp();
    }

    private void startApp() {
        exerciseChoice = new ExerciseDatabase();
        plan = new Plan();
        appState = true;
        String input;

        while(appState){
            mainMenu();
            input = scanner.nextLine();
            command(input);
        }
    }

    private void mainMenu() {
        System.out.println("Welcome to Workout Planner! Please select the following: ");
        System.out.println("p - see current plan");
        System.out.println("w - see all possible workouts");
        System.out.println("a - add to workout collection");
        System.out.println("q - quit app");
    }

    private void command(String input) {
        if (input.equalsIgnoreCase("p")) {
            showPlan();
        } else if (input.equalsIgnoreCase("w")) {
            showExercises();
        } else if (input.equalsIgnoreCase("a")) {
            addWorkout();
        } else {
            appState = false;
        }
    }

    private void addWorkout() {
        System.out.println("Input the name of the workout you want add: ");
        String name = scanner.nextLine();

        System.out.println("Input the description of workout: ");
        String description = scanner.nextLine();

        System.out.println("Which part of body does it exercise: ");
        printBodyPart();
        String bodyPart = scanner.nextLine();

        System.out.println("Input the duration in seconds: ");
        int duration = Integer.parseInt(scanner.nextLine());

        Exercise newExer = new Exercise(name, description, bodyPart, duration);
        exerciseChoice.addExercise(0, newExer);

        System.out.println("Insertion Complete!");
    }

    private void printBodyPart() {
        System.out.println("a - Abs");
        System.out.println("ar - Arm");
        System.out.println("l - Leg");
    }

    private void showExercises() {
        ArrayList<Exercise> exercises = exerciseChoice.getExercises();
        printExerciseList(exercises);

        showDetail = true;
        while(showDetail){
            System.out.println("Use the index number to see each exercise in detail.");
            System.out.println("Or 'q' to return to main menu\n");
            menu(scanner.nextLine(), exercises);
        }
    }

    private void menu(String nextLine, ArrayList<Exercise> exercises) {
        if(nextLine.equalsIgnoreCase("q")){
            showDetail = false;
        }else{
            int index = Integer.parseInt(nextLine) - 1;
            Exercise e = exercises.get(index);
            e.showExercises();
        }
    }

    private void showPlan() {
        ArrayList<Exercise> exercises = plan.getExercises();
        if (exercises.isEmpty()) {
            System.out.println("Your plan is currently empty.");
        } else {
            printExerciseList(exercises);
        }

        showDetail = true;
        while(showDetail){
            System.out.println("'r' - remove an exercise at an index");
            System.out.println("'a' - add an exercise at an index");
            System.out.println("'q' to return to main menu");
            editPlan(scanner.nextLine(), exercises);
        }
    }

    private void editPlan(String nextLine, ArrayList<Exercise> exercises) {
        if(nextLine.equalsIgnoreCase("r")){
            System.out.println("Enter the index of the exercise to remove: ");
            exercises.remove(Integer.parseInt(scanner.nextLine()) - 1);

            System.out.println("Remove Successful! Here is the current schedule: ");
            printExerciseList(exercises);
        }else if(nextLine.equalsIgnoreCase("a")){
            ArrayList<Exercise> collection = exerciseChoice.getExercises();
            printExerciseList(collection);

            System.out.println("Enter the index of the exercise from the collection: ");
            int fromIndex = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.println("Enter the index of the exercise to add to the plan: ");
            int toIndex = Integer.parseInt(scanner.nextLine()) - 1;

            exercises.add(toIndex, collection.get(fromIndex));
            System.out.println("\nAdd Successful! Here is the current schedule: ");
            printExerciseList(exercises);
        }else{
            showDetail = false;
        }
    }

    private void printExerciseList(ArrayList<Exercise> exercises){
        int index = 0;
        for (Exercise e : exercises) {
            System.out.println(index+1 + ". " + e.getName() + ": " + e.getDuration() + " sec");
            index++;
        }
        System.out.println("\n");
    }
}
