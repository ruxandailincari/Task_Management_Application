package org.example.BusinessLogic;

import org.example.DataModel.ComplexTask;
import org.example.DataModel.Employee;
import org.example.DataModel.Task;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class TasksManagement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Employee, List<Task>> map;

    public TasksManagement(){
        this.map = new HashMap<>();
    }

    public void addEmployee(Employee emp){
        ArrayList<Task> t = new ArrayList<>();
        map.put(emp, t);
    }

    public void assignTaskToEmployee(int idEmployee, Task task){
        map.keySet().stream()
                .filter(e -> e.getIdEmployee() == idEmployee)
                .findFirst()
                .ifPresent(e -> map.get(e).add(task));
    }

    public int calculateEmployeeWorkDuration(int idEmployee){
        return map.keySet().stream()
                .filter(e -> idEmployee == e.getIdEmployee())
                .flatMap(e -> map.get(e).stream())
                .filter(t -> t.getStatusTask().equalsIgnoreCase("COMPLETED"))
                        .mapToInt(Task::estimateDuration)
                        .sum();
    }

    public void modifyTaskStatus(int idEmployee, int idTask){
        map.keySet().stream()
                .filter(e -> e.getIdEmployee() == idEmployee)
                .flatMap(e -> map.get(e).stream())
                .filter(t -> t.getIdTask()==idTask && t.getStatusTask().equalsIgnoreCase("Uncompleted"))
                .forEach( t ->
                { if(t instanceof ComplexTask){
                    modifyComplexTaskStatus((ComplexTask) t);
                }
                else {
                    t.setStatusTask("Completed");
                }});
    }

    public void modifyComplexTaskStatus(ComplexTask task){
        task.getTasks().forEach( subtask -> {
                    if(subtask instanceof ComplexTask){
                        modifyComplexTaskStatus((ComplexTask) subtask);
                    }
                    subtask.setStatusTask("Completed");
                });
        task.setStatusTask("Completed");
    }

    public Set<Employee> getEmployees(){
        return map.keySet();
    }

    public List<Task> getTasksForEmployee(Employee e){
        return map.get(e);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();

        map.keySet().forEach(e -> {
            s.append(e.toString()).append(" ");
            List<Task> tasks = getTasksForEmployee(e);
            tasks.forEach(t -> s.append(t.toString()).append(" "));
            s.append(calculateEmployeeWorkDuration(e.getIdEmployee())).append(" ");
        });

        return s.toString();
    }

    public Map<Employee, List<Task>> getMap(){
        return this.map;
    }


}
