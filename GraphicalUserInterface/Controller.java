package org.example.GraphicalUserInterface;

import org.example.BusinessLogic.TasksManagement;
import org.example.BusinessLogic.Utility;
import org.example.DataAccess.Serialization;
import org.example.DataModel.ComplexTask;
import org.example.DataModel.Employee;
import org.example.DataModel.SimpleTask;
import org.example.DataModel.Task;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.KeyStore;
import java.util.*;

public class Controller implements ActionListener {
    private View view;
    private List<Employee> employees;
    private List<Task> tasks;
    private Serialization serialization = new Serialization();
    private TasksManagement management;
    private Utility utility;

    public Controller(View v) {
        this.view = v;
        employees = view.loadEmployees();
        tasks = view.loadTasks();
        management = view.loadManagement();
        utility = new Utility(management);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (Objects.equals(command, "ADD EMPLOYEE")) {
            addEmployeeAction();
        }
        if (Objects.equals(command, "CLEAR EMPLOYEE")) {
            view.clearEmployeeFields();
        }
        if (Objects.equals(command, "CLEAR TASK")) {
            view.clearTaskFields();
        }
        if (Objects.equals(command, "ADD TASK")) {
            addSimpleTaskAction();
        }
        if (Objects.equals(command, "ADD COMPLEX TASK")) {
            addComplexTaskAction();
        }
        if (Objects.equals(command, "ADD TO TASK")) {
            addToComplexTaskAction();
        }
        if (Objects.equals(command, "ASSIGN TASK")) {
            assignTask();
        }
        if (Objects.equals(command, "MODIFY TASK")) {
            modifyTask();
        }
        if (Objects.equals(command, "FILTER")) {
            filterManagement();
        }
        if (Objects.equals(command, "CALC")) {
            calculateTasks();
        }
    }

    public void addEmployeeAction() {
        if (view.getEmployeeIDTextField().getText().isEmpty() || Integer.parseInt(view.getEmployeeIDTextField().getText()) < 0) {
            JOptionPane.showMessageDialog(new JFrame(), "Employee ID must be a positive integer!");
            return;
        }
        int employeeID = Integer.parseInt(view.getEmployeeIDTextField().getText());
        if (view.getEmployeeNameTextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid employee name!");
            return;
        }
        String employeeName = String.valueOf(view.getEmployeeNameTextField().getText());
        Employee employee = new Employee(employeeID, employeeName);
        for (Employee emp : employees) {
            if (emp.getIdEmployee() == employeeID) {
                JOptionPane.showMessageDialog(new JFrame(), "An employee with this ID already exists!");
                return;
            }
        }
        employees.clear();
        employees.add(employee);
        try {
            serialization.serializeEmployee(employees);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        management.addEmployee(employee);
        view.addEmployeesComboBox(employee);
    }

    public void addSimpleTaskAction() {
        if (view.getTaskIDTextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Task ID must be a positive integer!");
            return;
        }
        int taskID = Integer.parseInt(view.getTaskIDTextField().getText());
        if (assignedTaskId(taskID)) {
            return;
        }
        if (view.getTaskStatusTextField().getText().isEmpty() || !view.getTaskStatusTextField().getText().equals("Uncompleted")) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid task status! A task must be Uncompleted!");
            return;
        }
        String taskStatus = view.getTaskStatusTextField().getText();
        if (view.getStartHourTextField().getText().isEmpty() || view.getEndHourTextField().getText().isEmpty() || Integer.parseInt(view.getStartHourTextField().getText()) <= 0 || Integer.parseInt(view.getStartHourTextField().getText()) > 24 || Integer.parseInt(view.getStartHourTextField().getText()) > Integer.parseInt(view.getEndHourTextField().getText()) || Integer.parseInt(view.getEndHourTextField().getText()) <= 0 || Integer.parseInt(view.getEndHourTextField().getText()) > 24) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid task hours!");
            return;
        }
        int startHour = Integer.parseInt(view.getStartHourTextField().getText());
        int endHour = Integer.parseInt(view.getEndHourTextField().getText());
        Task task = new SimpleTask(taskID, taskStatus, startHour, endHour);
        for (Task t : tasks) {
            if (t.getIdTask() == taskID) {
                view.repeatTask();
                return;
            }
        }
        tasks.clear();
        tasks.add(task);
        saveTasks();
        view.addTasksComboBox(task);
    }

    public void addComplexTaskAction() {
        if (view.getTaskIDTextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Task ID must be a positive integer!");
            return;
        }
        int taskID = Integer.parseInt(view.getTaskIDTextField().getText());
        if (assignedTaskId(taskID)) {
            return;
        }
        if (view.getTaskStatusTextField().getText().isEmpty() || !view.getTaskStatusTextField().getText().equals("Uncompleted")) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid task status! A task must be Uncompleted!");
            return;
        }
        String taskStatus = view.getTaskStatusTextField().getText();
        Task task = new ComplexTask(taskID, taskStatus);
        for (Task t : tasks) {
            if (t.getIdTask() == taskID) {
                view.repeatTask();
                return;
            }
        }
        tasks.clear();
        tasks.add(task);
        saveTasks();
        view.addTasksComboBox(task);
    }

    public void addToComplexTaskAction() {
        int taskID = Integer.parseInt(view.getTaskIDTextField().getText());
        Task selectedTask = (Task) view.getSelectedTask().getSelectedItem();
        for (Task task : tasks) {
            if (task instanceof ComplexTask && task.getIdTask() == taskID) {
                view.removeTasksComboBoxAt(tasks.indexOf(task));
                ((ComplexTask) task).addTask(selectedTask);
                view.removeTasksComboBox(selectedTask);
                if (((ComplexTask) task).getTasks().size() != 1) {
                    view.addTasksComboBox(task);
                }
                tasks.clear();
                List<Task> updatedTasks = view.getComboBoxTasks();
                tasks.addAll(updatedTasks);
                saveTasks();
                return;
            }
        }
        JOptionPane.showMessageDialog(new JFrame(), "Complex Task not found!");
    }

    public void saveTasks() {
        try {
            serialization.serializeTask(tasks);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void assignTask() {
        Employee emp = (Employee) view.getSelectedEmployee().getSelectedItem();
        Task task = (Task) view.getSelectedTask().getSelectedItem();
        int empId = emp.getIdEmployee();
        Map<Employee, List<Task>> managementMap = management.getMap();
        if (managementMap.containsKey(emp)) {
            management.assignTaskToEmployee(empId, task);
        } else {
            List<Task> newTaskList = new ArrayList<>();
            newTaskList.add(task);
            managementMap.put(emp, newTaskList);
        }
        List<Task> l = view.getComboBoxTasks();
        view.removeTasksComboBox(task);
        tasks.clear();
        List<Task> updatedTasks = view.getComboBoxTasks();
        tasks.addAll(updatedTasks);
        saveTasks();
        view.updateEmployeeTaskList(emp, managementMap.get(emp), management);
        try {
            serialization.serializeTasksManagement(management);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean assignedTaskId(int taskID) {
        Set<Integer> assignedTaskIds = new HashSet<>();
        for (List<Task> tasks : management.getMap().values()) {
            for (Task task : tasks) {
                if (task instanceof ComplexTask) {
                    assignedComplexTaskId((ComplexTask) task, assignedTaskIds);
                }
                assignedTaskIds.add(task.getIdTask());
            }
        }
        if (assignedTaskIds.contains(taskID)) {
            view.repeatTask();
            return true;
        }
        return false;
    }

    public void assignedComplexTaskId(ComplexTask task, Set<Integer> set) {
        for (Task subtask : task.getTasks()) {
            if (subtask instanceof ComplexTask) {
                assignedComplexTaskId((ComplexTask) subtask, set);
            }
            set.add(subtask.getIdTask());
        }
        set.add(task.getIdTask());
    }

    public boolean existingTask(int taskID) {
        for (Task t : tasks) {
            if (t.getIdTask() == taskID) {
                return true;
            }
        }
        return false;
    }

    public void modifyTask() {
        if (view.getTaskID2TextField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Task ID must be a positive integer!");
            return;
        }
        int taskID = Integer.parseInt(view.getTaskID2TextField().getText());
        if (!assignedTaskId2(taskID) || existingTask(taskID)) {
            JOptionPane.showMessageDialog(new JFrame(), "This task is not in management!");
            return;
        }
        Employee emp = (Employee) view.getSelectedEmployee().getSelectedItem();
        management.modifyTaskStatus(emp.getIdEmployee(), taskID);
        view.updateEmployeeTaskList(emp, management.getMap().get(emp), management);
        System.out.println(management.calculateEmployeeWorkDuration(emp.getIdEmployee()));
        try {
            serialization.serializeTasksManagement(management);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean assignedTaskId2(int taskID) {
        Set<Integer> assignedTaskIds = new HashSet<>();
        for (List<Task> tasks : management.getMap().values()) {
            for (Task task : tasks) {
                assignedTaskIds.add(task.getIdTask());
            }
        }
        return assignedTaskIds.contains(taskID);
    }

    public void filterManagement() {
        List<String> filteredList = utility.filterEmployees();
        view.prepareFrame4(filteredList);
    }

    public void calculateTasks() {
        Map<String, Map<String, Integer>> rez = utility.calculateTasks();
        ArrayList<String> emp = new ArrayList<>();
        ArrayList<Integer> uncompletedTask = new ArrayList<>();
        ArrayList<Integer> completedTask = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : rez.entrySet()) {
            emp.add(entry.getKey());
            Map<String, Integer> map = entry.getValue();
            for (Map.Entry<String, Integer> entry2 : map.entrySet()) {
                if (entry2.getKey().equals("Uncompleted")) {
                    uncompletedTask.add(entry2.getValue());
                }
                if (entry2.getKey().equals("Completed")) {
                    completedTask.add(entry2.getValue());
                }
            }
        }
        view.prepareFrame5(emp, uncompletedTask,completedTask);
    }
}
