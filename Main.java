package org.example;

import org.example.BusinessLogic.TasksManagement;
import org.example.BusinessLogic.Utility;
import org.example.DataAccess.Serialization;
import org.example.DataModel.ComplexTask;
import org.example.DataModel.Employee;
import org.example.DataModel.SimpleTask;
import org.example.DataModel.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Serialization serialization = new Serialization();
        Task task1 = new SimpleTask(1,"Uncompleted",12,24);
        Task task2 = new SimpleTask(2,"Uncompleted",15,24);
        Task task3 = new SimpleTask(3,"Uncompleted",8,18);
        Task task4 = new ComplexTask(4,"Uncompleted");
        Task task5 = new ComplexTask(5,"Uncompleted");
        Task task6 = new SimpleTask(6,"Uncompleted",8,21);
        Task task7 = new SimpleTask(7,"Uncompleted",8,21);
        ((ComplexTask) task4).addTask(task1);
        ((ComplexTask)task4).addTask(task2);
        ((ComplexTask)task5).addTask(task3);
        ((ComplexTask)task5).addTask(task4);
        System.out.println("Task 5:" + task5.estimateDuration());
        System.out.println("Task 1:" + task1.estimateDuration());
        try{
            List<Task> tasks = new ArrayList<>();
            tasks.add(task1);
            tasks.add(task2);
            tasks.add(task3);
            tasks.add(task4);
            serialization.serializeTask(tasks);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try{
            serialization.deserializeTask();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }

        Employee e1 = new Employee(1,"Raluca");
        Employee e2 = new Employee(2,"Daria");
        try{
            List<Employee> e = new ArrayList<>();
            e.add(e1);
            e.add(e2);
            serialization.serializeEmployee(e);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try{
            serialization.deserializeEmployee();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }

        TasksManagement management = new TasksManagement();
        management.addEmployee(e1);
        management.addEmployee(e2);

        //management.assignTaskToEmployee(1,task1);
        management.assignTaskToEmployee(1,task4);
        management.assignTaskToEmployee(2,task2);
        management.assignTaskToEmployee(2,task7);
        management.assignTaskToEmployee(2,task7);
        management.assignTaskToEmployee(2,task7);
        //management.printStatusTask(1,4);
        //management.printStatusTask(1,2);
        System.out.println("Raluca lucreaza:" + management.calculateEmployeeWorkDuration(1));
        management.modifyTaskStatus(1,4);
        System.out.println("Raluca lucreaza:" + management.calculateEmployeeWorkDuration(1));
        //management.printStatusTask(1,4);
        //management.printStatusTask(1,2);
        //management.printStatusTask(1,2);
        /*List<Task> l = management.getTasksForEmployee(e1);
        for(Task i : l){
            management.printStatusTask(1,i.getIdTask());
        }*/

        try {
            serialization.serializeTasksManagement(management);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            serialization.deserializeTasksManagement();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        Utility u = new Utility(management);
        System.out.println(u.calculateTasks());
        u.filterEmployees();
    }
}