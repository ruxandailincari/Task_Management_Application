package org.example.DataModel;

import java.util.ArrayList;

public final class ComplexTask extends Task {

    private ArrayList<Task> tasks;

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public int estimateDuration() {
        int duration = 0;
        duration = tasks.stream().mapToInt(Task::estimateDuration).sum();
        return duration;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("Complex task:");
        s.append(super.getIdTask()).append(" ").append(super.getStatusTask()).append(" ");
        tasks.forEach(task -> s.append(task.toString()).append(" "));
        return s.toString();
    }
}
