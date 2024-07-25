package persistence;

import java.io.*;

import org.json.JSONObject;

import model.ListOfExercise;

// A writer that writes the JSON representation of plan and collection to the files
// With references to the demo in the link below
// Citiation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter printWriter;
    private String path;

    // EFFECTS: instantiate a writer to write in a file through the given path
    public JsonWriter(String path) {
        this.path = path;
    }

    // MODIFIES: this
    // EFFECTS: open the file to writer, throw FileNotFoundException when file
    // can't be open for writing
    public void open() throws FileNotFoundException {
        File file = new File(path);
        printWriter = new PrintWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: write JSON representation of the workout to the file
    public void write(ListOfExercise workout) {
        JSONObject jsonWorkout = workout.toJsonObject();
        printWriter.print(jsonWorkout.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        printWriter.close();
    }

}
