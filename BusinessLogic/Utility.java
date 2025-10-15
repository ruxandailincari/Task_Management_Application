package org.example.BusinessLogic;

import org.example.DataModel.Employee;
import org.example.DataModel.Task;

import java.util.*;
import java.util.stream.Collectors;

public class Utility {
    private TasksManagement tasksManagement;

    public Utility(TasksManagement tasksManagement) {
        this.tasksManagement = tasksManagement;
    }


    public ArrayList<String> filterEmployees(){
        return tasksManagement.getEmployees().stream()
                .filter(e -> tasksManagement.calculateEmployeeWorkDuration(e.getIdEmployee()) > 40)
                .sorted(Comparator.comparingInt(e -> tasksManagement.calculateEmployeeWorkDuration(e.getIdEmployee())))
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public Map<String, Map<String,Integer>> calculateTasks(){
        return tasksManagement.getEmployees().stream()
                .collect(Collectors.toMap(Employee::getName,
                        e -> {
                            Map<String, Integer> cnt = new HashMap<>();
                            cnt.put("Completed", 0);
                            cnt.put("Uncompleted", 0);
                            tasksManagement.getTasksForEmployee(e).forEach( t->
                                    cnt.put(t.getStatusTask(), cnt.get(t.getStatusTask()) + 1));
                            return cnt;
                        }));
    }
}
