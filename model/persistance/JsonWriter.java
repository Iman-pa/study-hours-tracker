package model.persistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.StudyTracker;

public class JsonWriter {

    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    //EFFECTS: sets the destination 
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //EFFECTS: throws exception if it can't open the file, otherwise it opens the file
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //EFFECTS: writes json representation of tracker in to file
    public void write(StudyTracker tracker) {
        JSONObject json = tracker.toJson();
        saveToFile(json.toString(TAB));
    } 

    //EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }

    //EFFECTS: closes the writer 
    public void close() {
        writer.close();
    }

}
