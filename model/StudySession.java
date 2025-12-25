package model;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import Exception.InvalidMinutesException;

public class StudySession {
    private String course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;
    private LocalDateTime pauseStart;
    private boolean paused;
    private DateTimeFormatter formatter;


    //REQUIRES: duration >= 0
    //EFFECTS: sets the course, duration, start time and calculates the end time.
    public StudySession(String course, int duration) {
        this.course = course;
        this.duration = duration;
        startTime = LocalDateTime.now();
        endTime = startTime.plusMinutes(duration);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    }

    public String getCourse() {
        return course;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return duration;
    }

    //EFFECTS: if it's not paused, then it pauses the session
    public void pause() {
        if (!paused) {
            pauseStart = LocalDateTime.now();
            paused = true;
        }
    }

    //EFFECTS: if it's paused, then it resumes the session
    public void resume() {
        if (paused) {
            long pausedDuration = Duration.between(pauseStart, LocalDateTime.now()).toSeconds();
            this.endTime = endTime.plusSeconds(pausedDuration);
            paused = false;
        }
    }

    //EFFECTS: if minutes less than 0 throws InvalidMinutesException, otherwise extends the end time by minutes and adds the minutes to duration
    public void extend(int minutes) throws InvalidMinutesException {
        if (minutes < 0) {
            throw new InvalidMinutesException();
        }
        endTime = endTime.plusMinutes(minutes);
        duration += minutes;
    }

    //EFFECTS: returns true if the end time is passed and false otherwise
    public boolean done() {
        if (LocalDateTime.now().isAfter(endTime)) {
            return true;
        }
        return false;
    }

    //EFFECTS: changes the format to a nice string
    @Override
    public String toString() {
        return "Course: " + course + ", Start: " + startTime.format(formatter) +
               ", End: " + endTime.format(formatter) + ", Duration (min): " + duration;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("course", course);
        json.put("startTime", startTime.toString());
        json.put("endTime", endTime.toString());
        json.put("duration", duration);
        json.put("paused", paused);

        return json;
    }

    public void setStartTime(LocalDateTime start) {
        this.startTime = start;
    }

    public void setEndTime(LocalDateTime end) {
        this.endTime = end;
    }

}
