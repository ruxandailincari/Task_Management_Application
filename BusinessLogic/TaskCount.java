package org.example.BusinessLogic;

public class TaskCount {
    private String taskStatus;
    private int taskNumber;

    public TaskCount(String taskStatus, int taskNumber){
        this.taskStatus = taskStatus;
        this.taskNumber = taskNumber;
    }

    public String toString(){
        return this.taskStatus + " " + this.taskNumber;
    }
}
