package model;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Exception.InvalidMinutesException;
import ui.StudySession;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("Enter the course name: ");
        String course = scanner.nextLine();
        System.out.println("Enter duration in minutes: ");
        int duration = scanner.nextInt();
        scanner.nextLine();

        StudySession session = new StudySession(course, duration);
        System.out.println(session);

        boolean running = true;
        while (running) {
            System.out.println("\nEnter command(pause, resume, extend, status, exit)");
            String command = scanner.nextLine();

            switch (command) {
                case "pause":
                    session.pause();
                    System.out.println("Session paused");
                    break;

                case "resume":
                    session.resume();
                    System.out.println("Session resumed");
                    System.out.println("End time adjusted to " + session.getEndTime().format(formatter));
                    break;

                case "extend":
                    System.out.println("Enter extra minutes to extend: ");
                    int minutes = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        session.extend(minutes);
                        System.out.println("Session extended by " + minutes + " minutes");
                        System.out.println("End time adjusted to " + session.getEndTime().format(formatter));
                    } catch (InvalidMinutesException e) {
                        System.out.println("Invalid minutes");
                    }
                    break;

                case "status":
                    System.out.println("Is it done? " + session.done());
                    break;
                
                case "exit":
                    running = false;
                    System.out.println("Exiting the application");
                    break;

                default:
                    System.out.println("Invalid input, try again");
                    break;
            }
        }
        scanner.close();
    }
}
