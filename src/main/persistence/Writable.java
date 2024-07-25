package persistence;

import org.json.JSONObject;

// With references to the demo in the link below
//Citiation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {

    // EFFECTS: return this as a JSON object
    JSONObject toJsonObject();
}
