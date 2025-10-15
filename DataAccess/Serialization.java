package org.example.DataAccess;

import org.example.BusinessLogic.TasksManagement;
import org.example.DataModel.ComplexTask;
import org.example.DataModel.Employee;
import org.example.DataModel.Task;
import org.example.BusinessLogic.TasksManagement;

import java.io.*;
import java.util.*;

public class Serialization {

    public void serializeEmployee(List<Employee> newEmployees) throws IOException {
        List<Employee> existingEmployees = new ArrayList<>();
        try {
            existingEmployees = deserializeEmployee();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        existingEmployees.addAll(newEmployees);

        FileOutputStream fileOutputStream = new FileOutputStream("employee.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(existingEmployees);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public List<Employee> deserializeEmployee() throws IOException, ClassNotFoundException {
        File file = new File("employee.txt");
        if (!(file.exists()) || file.length() == 0) {
            return new ArrayList<>();
        }

        FileInputStream fileInputStream = new FileInputStream("employee.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (List<Employee>) objectInputStream.readObject();
    }


        public void serializeTask(List<Task> newTasks) throws IOException {
        List<Task> existingTasks = new ArrayList<>();
        try{
            existingTasks = deserializeTask();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        serializeTask2(newTasks,existingTasks);
        FileOutputStream fileOutputStream = new FileOutputStream("task.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(existingTasks);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void serializeTask2(List<Task> newTasks, List<Task> existingTasks){
        for (Task t2 : newTasks) {
            boolean exists = false;
            Iterator<Task> iterator = existingTasks.iterator();
            while (iterator.hasNext()) {
                Task t1 = iterator.next();
                if (t1.getIdTask() == t2.getIdTask()) {
                    exists = true;
                    iterator.remove();
                    existingTasks.add(t2);
                    break;
                }
                if (t2 instanceof ComplexTask) {
                    ArrayList<Task> subtasks = ((ComplexTask) t2).getTasks();
                    for (Task subtask : subtasks) {
                        if (subtask.getIdTask() == t1.getIdTask()) {
                            iterator.remove();
                        }
                    }
                }
            }
            if (!exists) {
                existingTasks.add(t2);
            }
        }
    }

    public List<Task> deserializeTask() throws IOException, ClassNotFoundException {
        File file = new File("task.txt");
        if(!(file.exists()) || file.length() == 0){
            return new ArrayList<>();
        }
        FileInputStream fileInputStream = new FileInputStream("task.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        List<Task> restoredTask = (List<Task>) objectInputStream.readObject();
        Map<Employee,List<Task>> map = deserializeTasksManagement().getMap();
        Set<Integer> assignedTaskIds = new HashSet<>();
        for (List<Task> tasks : map.values()) {
            for (Task task : tasks) {
                assignedTaskIds.add(task.getIdTask());
            }
        }
        restoredTask.removeIf(task -> assignedTaskIds.contains(task.getIdTask()));
        objectInputStream.close();
        return restoredTask;
    }

    public void serializeTasksManagement(TasksManagement tasksManagement) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("tasksManagement.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(tasksManagement);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public TasksManagement deserializeTasksManagement() throws IOException, ClassNotFoundException {
        File file = new File("tasksManagement.txt");
        if(!(file.exists()) || file.length() == 0){
            return new TasksManagement();
        }

        FileInputStream fileInputStream = new FileInputStream("tasksManagement.txt");
        ObjectInputStream objectInputStream  = new ObjectInputStream(fileInputStream);
        TasksManagement tasksManagement = (TasksManagement) objectInputStream.readObject();
        objectInputStream.close();
        return tasksManagement;
    }
}
