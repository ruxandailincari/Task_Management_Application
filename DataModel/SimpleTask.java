package org.example.DataModel;

public final class SimpleTask extends Task {

    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int estimateDuration() {
        return this.endHour - this.startHour;
    }

    public String toString() {
        return "Simple Task id: " + super.getIdTask() + " task status: " + super.getStatusTask() + " start hour: " + this.startHour +
                " end hour: " + this.endHour;
    }
}