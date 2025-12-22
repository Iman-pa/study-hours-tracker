package ui;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Exception.InvalidMinutesException;

public class StudySession {
    private String course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;
    private LocalDateTime pauseStart;
    private boolean paused;
    private DateTimeFormatter formatter;


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

    public void pause() {
        if (!paused) {
            pauseStart = LocalDateTime.now();
            paused = true;
        }
    }

    public void resume() {
        if (paused) {
            long pausedDuration = Duration.between(pauseStart, LocalDateTime.now()).toSeconds();
            this.endTime = endTime.plusSeconds(pausedDuration);
            paused = false;
        }
    }

    public void extend(int minutes) throws InvalidMinutesException {
        if (minutes < 0) {
            throw new InvalidMinutesException();
        }
        endTime = endTime.plusMinutes(minutes);
        duration += minutes;
    }

    public boolean done() {
        if (LocalDateTime.now().isAfter(endTime)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Course: " + course + ", Start: " + startTime.format(formatter) +
               ", End: " + endTime.format(formatter) + ", Duration (min): " + duration;
    }

}
