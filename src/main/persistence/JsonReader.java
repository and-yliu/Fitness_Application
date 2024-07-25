package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Exercise;
import model.ExerciseCollection;
import model.ListOfExercise;
import model.Plan;

// A reader that reads the JSON data stored in plan and collection file
// With references to the demo in the link below
// Citiation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: instantiate a JsonReader to read a file from the given source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: return plan from file and return it
    // throws IOException if there is an error when reading data from file
    public Plan readPlan() throws IOException {
        String jsonString = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonString);
        return parsePlan(jsonObject);
    }

    // EFFECTS: return exerciseCollection from file and return it
    // throws IOException if there is an error when reading data from file
    public ExerciseCollection readCollection() throws IOException {
        String jsonString = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonString);
        return parseCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> content.append(s));
        }

        return content.toString();
    }

    // EFFECTS: change the jsonObject to a Plan object
    private Plan parsePlan(JSONObject jsonObject) {
        Plan plan = new Plan();
        JSONArray jsonArray = jsonObject.getJSONArray("Exercises");

        for (Object jsonExercise : jsonArray) {
            JSONObject nextExercise = (JSONObject) jsonExercise;
            addExercise(plan, nextExercise);
        }

        return plan;
    }

    // EFFECTS: change the jsonObject to a ExerciseCollection object
    private ExerciseCollection parseCollection(JSONObject jsonObject) {
        ExerciseCollection collection = new ExerciseCollection();
        JSONArray jsonArray = jsonObject.getJSONArray("Exercises");

        for (Object jsonExercise : jsonArray) {
            JSONObject nextExercise = (JSONObject) jsonExercise;
            addExercise(collection, nextExercise);
        }

        return collection;
    }

    // MODIFIES: workout
    // EFFECTS: parse jsonOBject to exercise and add it to workout
    private void addExercise(ListOfExercise workout, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        String bodyPart = jsonObject.getString("bodyPart");
        int duration = jsonObject.getInt("duration");

        Exercise exercise = new Exercise(name, description, bodyPart, duration);
        workout.addExercise(workout.getExercises().size(), exercise);
    }
}
