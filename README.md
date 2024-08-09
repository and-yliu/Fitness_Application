
# My Personal Project

## A Fitness Planner

This application allows users to create their own workout plans. It includes many variety of exercises and users can pick which ones they want to do for differents. 

**Each exercise includes**:
- name
- description
- body part
- duration

Anyone with a habit of working out or a interest in starting to workout can use this application. This project is an interest to me because I feel sometimes I exercise too little. I want to have something that can help me with my fitness plan.

## User Stories:
- I want to be able to add an new exercise to the workout collection and specify the name, description, exercise which part of the body, and duration. 
- I want to be able to see a list of all possible exercise in my schedule.
- I want to be able to select a exercise and view its description in details.
- I want to be able to sort the exercises that exercise the same part of the body together.
- I want to be able to save my current exercises schedule and collection if I choose so.
- I want to be able to reload my saved exercise schedule and collection to continue make edits if I choose so.

## Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by going into plan, and selecting an exercise in the collection. You can add it to your plan by clicking the "Add Seleted Exercise from Collection to Plan" button. 
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by going into plan, and selecting an exercise in your plan, you can remove the exercise from your plan by clicking the "Remove Seleted Exercise from Plan" button.
- You can locate my visual component by going into plan and clicking the start exercise button, an image will pop up.
- You can save the state of my application by using the Save button from the home page
- You can reload the state of my application by using the Load button from the home page

## Phase 4: Task 2
Added sit-ups and squats to plan and removed rest from plan:

Thu Aug 08 23:06:23 PDT 2024
Exercise added to plan: Sit-Ups

Thu Aug 08 23:06:25 PDT 2024
Exercise added to plan: Squats

Thu Aug 08 23:06:28 PDT 2024
Exercise removed from plan: Rest

## Phase 4: Task 3
In my model package, I think I had nice hierarchy by extracting the common functionality from Plan and ExerciseCollection into an abstract class called ListOfExercise. But the classes in my ui package seems to be having associations all over the place. If I have more time, I might be able to extract an hierarchy from those GUI classes since they shares some similar functionality. They would benefits from sharing some protected variables as well to reduce coupling. Maybe I could also make more uses of the ListOfExercise abstract class in the GUI class.