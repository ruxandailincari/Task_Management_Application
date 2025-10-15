package org.example.BusinessLogic;

import org.example.DataModel.Employee;
import org.example.DataModel.Task;

import java.util.List;

public class ManagementEntry extends TasksManagement{
    private Employee employee;
    private List<Task> tasks;

    public ManagementEntry(Employee employee, List<Task> tasks) {
        this.employee = employee;
        this.tasks = tasks;
    }

    public Employee getEmployee() {
        return employee;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Employee:" ).append(employee.toString()).append("Tasks: ");
        for(Task task : tasks){
            s.append(task.toString()).append("; ");
        }
        return s.toString();
    }
}
