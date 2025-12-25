package model.persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.StudySession;
import model.StudyTracker;

public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public StudyTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parseStudyTracker(json);
    }

    private String readFile(String souce) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    private StudyTracker parseStudyTracker(JSONObject jsonObject) {
        StudyTracker tracker = new StudyTracker();
        addSessions(tracker, jsonObject);
        return tracker;
    }

    private void addSessions(StudyTracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sessions");
        for (Object json : jsonArray) {
            JSONObject nextSession = (JSONObject) json;
            addSession(tracker, nextSession);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses a single StudySession from JSON object and adds it to tracker
    private void addSession(StudyTracker tracker, JSONObject jsonObject) {
        String course = jsonObject.getString("course");
        int duration = jsonObject.getInt("duration");
        String start = jsonObject.getString("startTime");
        String end = jsonObject.getString("endTime");
        boolean paused = jsonObject.getBoolean("paused");

        StudySession session = new StudySession(course, duration);
        session.resume(); // to reset endTime if needed

        // Overwrite start and end times
        session.setStartTime(LocalDateTime.parse(start));
        session.setEndTime(LocalDateTime.parse(end));

        // set paused status if needed
        if (paused) {
            session.pause();
        }

        tracker.addSession(session);
    }

}
