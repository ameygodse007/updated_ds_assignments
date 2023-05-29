import java.util.ArrayList;
import java.util.List;

class Process {
    private int id;
    private boolean isCoordinator;
    private boolean isActive;

    public Process(int id) {
        this.id = id;
        this.isCoordinator = false;
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    public void setCoordinator(boolean coordinator) {
        isCoordinator = coordinator;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

class BullyAlgorithm {
    private List<Process> processes;
    private int coordinatorId;

    public BullyAlgorithm() {
        processes = new ArrayList<>();
        coordinatorId = -1;
    }

    public void add(int processId) {
        Process newProcess = new Process(processId);
        processes.add(newProcess);
        System.out.println("Process " + processId + " added.");
    }

    public void down(int processId) {
        for (Process process : processes) {
            if (process.getId() == processId) {
                process.setActive(false);
                if (process.isCoordinator()) {
                    process.setCoordinator(false);
                    coordinatorId = -1;
                    System.out.println("Process " + processId + " was the coordinator and is now down.");
                    election();
                } else {
                    System.out.println("Process " + processId + " is down.");
                }
                return;
            }
        }
        System.out.println("Process " + processId + " not found.");
    }

    public void mess(int processId, String message) {
        for (Process process : processes) {
            if (process.getId() == processId) {
                if (process.isActive()) {
                    System.out.println("Message '" + message + "' sent to process " + processId);
                    return;
                } else {
                    System.out.println("Process " + processId + " is currently down. Cannot send message.");
                    return;
                }
            }
        }
        System.out.println("Process " + processId + " not found.");
    }

    private void election() {
        for (Process process : processes) {
            if (process.isActive() && process.getId() > coordinatorId) {
                // Assume higher ID process as the coordinator candidate
                coordinatorId = process.getId();
            }
        }

        for (Process process : processes) {
            if (process.isActive() && process.getId() != coordinatorId) {
                System.out.println(
                        "Election message sent from process " + coordinatorId + " to process " + process.getId());
                // Simulating message exchange between processes
                process.setCoordinator(false);
                // Send OK message to coordinator
                System.out.println("OK message sent from process " + process.getId() + " to process " + coordinatorId);
            }
        }

        for (Process process : processes) {
            if (process.getId() == coordinatorId) {
                process.setCoordinator(true);
                System.out.println("Process " + coordinatorId + " elected as the new coordinator.");
                break;
            }
        }
    }
}

public class Bully_chatgpt {
    public static void main(String[] args) {
        BullyAlgorithm bully = new BullyAlgorithm();

        // Adding processes
        bully.add(1);
        bully.add(2);
        bully.add(3);
        bully.add(4);

        // Sending messages
        bully.mess(2, "Hello");
        bully.mess(3, "Hi");

        // Taking down
    }
}