package model;

import java.util.ArrayList;
import java.util.List;

//class that keeps all the sessions and lists them 
public class StudyTracker {
    private List<StudySession> sessions;

    //EFFECTS: constructs an empty list of study sessions
    public StudyTracker() {
        sessions = new ArrayList<>();
    }

    //EFFECTS: Adds a session to the list
    public void addSession(StudySession ss) {
        sessions.add(ss);
    }

    //EFFECTS:Removes a session from the list
    public void removeSession(StudySession ss) {
        sessions.remove(ss);
    }

    //EFFECTS: return all the sessions
    public List<StudySession> listSessions() {
        return sessions;
    }

    //EFFECTS: returns a list of sessions based on the status
    public List<StudySession> filterByStatus(boolean achievedStatus) {
        List<StudySession> filteredSessions = new ArrayList<>();
        for (StudySession s : sessions) {
            if (s.done() == achievedStatus) {
                filteredSessions.add(s);
            }
        }
        return filteredSessions;
    }

    //EFFECTS: return the total duration spent to study
    public int totalStudy() {
        int totalTime = 0;
        for (StudySession s : sessions) {
            totalTime += s.getDuration();
        }
        return totalTime;
    }


}
