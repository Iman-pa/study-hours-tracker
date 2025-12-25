package ui;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import Exception.InvalidMinutesException;
import model.StudySession;
import model.StudyTracker;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudyTracker tracker = new StudyTracker();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        

        boolean running = true;
        while (running) {

            System.out.println("\nEnter command(create, pause, resume, extend, status, total, exit)");
            String command = scanner.nextLine();

            switch (command) {
                case "create":
                    System.out.println("Enter the course name: ");
                    String course = scanner.nextLine();
                    System.out.println("Enter duration in minutes: ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();
                    StudySession session = new StudySession(course, duration);
                    System.out.println(session);
                    tracker.addSession(session);
                    System.out.println("Session created");
                    break;
                case "pause":
                    List<StudySession> activeSessions = tracker.filterByStatus(false);
                    if (activeSessions.isEmpty()) {
                        System.out.println("No available active sessions, create one first");
                        break;
                    }
                    for (int i = 0; i < activeSessions.size(); i++) {
                        System.out.println((i+1) + ") " + activeSessions.get(i));
                    }
                    System.out.println("Choose the number of the session to pause: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice < 1 || choice > activeSessions.size()) {
                        System.out.println("Invalid input try again");
                        break;
                    }
                    activeSessions.get(choice - 1).pause();
                    System.out.println("Session paused");
                    break;

                case "resume":
                    List<StudySession> availableSessions = tracker.filterByStatus(false);
                    for (int i = 0; i < availableSessions.size(); i++) {
                        System.out.println((i+1) + ") " + availableSessions.get(i));
                    }
                    System.out.println("Choose the number of the session to resume: ");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    if (number < 1 || number > availableSessions.size()) {
                        System.out.println("Invalid input try again");
                        break;
                    }
                    availableSessions.get(number - 1).resume();
                    System.out.println("Session resumed");
                    System.out.println("End time adjusted to " + availableSessions.get(number-1).getEndTime().format(formatter));
                    break;

                case "extend":
                    List<StudySession> availableSessionsE = tracker.filterByStatus(false);
                    if (availableSessionsE.isEmpty()) {
                        System.out.println("No available active sessions, create one first");
                        break;
                    }
                    for (int i = 0; i < availableSessionsE.size(); i++) {
                        System.out.println((i+1) + ") " + availableSessionsE.get(i));
                    }
                    System.out.println("Choose the number of the session to extend: ");
                    int numberE = scanner.nextInt();
                    scanner.nextLine();
                    if (numberE < 1 || numberE > availableSessionsE.size()) {
                        System.out.println("Invalid input try again");
                        break;
                    }
                    System.out.println("Enter extra minutes to extend: ");
                    int minutes = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        availableSessionsE.get(numberE-1).extend(minutes);
                        System.out.println("Session extended by " + minutes + " minutes");
                        System.out.println("End time adjusted to " + availableSessionsE.get(numberE-1).getEndTime().format(formatter));
                    } catch (InvalidMinutesException e) {
                        System.out.println("Invalid minutes");
                    }
                    break;

                case "status":
                    System.out.println("Enter 1 for completed and 2 for active sessions: ");
                    int x = scanner.nextInt();
                    scanner.nextLine();
                    if (x == 1) {
                        System.out.println("COMPLETED SESSIONS: ");
                        for (StudySession s : tracker.filterByStatus(true)) {
                            System.out.println(s);
                        }
                    } else if (x == 2) {
                        System.out.println("ACTIVE SESSIONS: ");
                        for (StudySession s : tracker.filterByStatus(false)) {
                            System.out.println(s);
                        }
                    } else {
                        System.out.println("Invalid input, try agian");
                    }
                    break;
                
                case "exit":
                    running = false;
                    System.out.println("Exiting the application");
                    break;

                case "total": 
                    System.out.println("Total time spent for sessions is: ");
                    System.out.println(tracker.totalStudy());
                    break;

                default:
                    System.out.println("Invalid input, try again");
                    break;
            }
        }
        scanner.close();
    }
}
