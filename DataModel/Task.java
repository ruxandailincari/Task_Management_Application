package org.example.DataModel;
import java.io.Serial;
import java.io.Serializable;

public abstract sealed class Task implements Serializable permits SimpleTask, ComplexTask{

    private static final long serialVersionUID = 1;
    private int idTask;
    private String statusTask;

    public Task(int idTask,String statusTask){
        this.idTask = idTask;
        this.statusTask = statusTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public abstract int estimateDuration();

    public abstract String toString();
}

