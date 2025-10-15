package org.example.GraphicalUserInterface;

import org.example.BusinessLogic.ManagementEntry;
import org.example.BusinessLogic.TaskCount;
import org.example.BusinessLogic.TasksManagement;
import org.example.DataAccess.Serialization;
import org.example.DataModel.Employee;
import org.example.DataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class View extends JFrame{
    private JPanel contentPane;
    private JPanel contentPane2;
    private JPanel contentPane3;
    private JPanel contentPane4;
    private JPanel contentPane5;
    private JPanel employeePanel;
    private JPanel tasksPanel;
    private JPanel tasksPanel1;
    private JPanel tasksPanel2;
    private JPanel buttonPanel;
    private JPanel managementPanel;
    private JTextField employeeID;
    private JTextField employeeName;
    private JTextField taskID;
    private JTextField taskID2;
    private JTextField taskStatus;
    private JTextField startHour;
    private JTextField endHour;
    private JButton addEmployeeButton;
    private JButton clearEmployeeFieldsButton;
    private JButton addTaskButton;
    private JButton okButton;
    private JButton clearTaskFieldsButton;
    private JButton addToComplexTaskButton;
    private JButton assignTaskButton;
    private JButton modifyStatusButton;
    private JButton modifyStatusButton2;
    private JButton filterButton;
    private JButton calculusButton;
    private JComboBox<Employee> employees;
    private JComboBox<String> taskStatusSelecter;
    private JComboBox<Task> tasks;
    private JFrame frame2;
    private JFrame frame3;
    private JFrame frame4;
    private JFrame frame5;
    private JList<ManagementEntry> management;
    private DefaultListModel<ManagementEntry> managementModel;
    private JList<TaskCount> employeeWorkHours;
    private DefaultListModel<TaskCount> employeeWorkHoursModel;
    private JList<String> filteredEmployeesList;
    private DefaultListModel<String> filteredEmployeesModel;
    private JList<String> empList;
    private DefaultListModel<String> empListModel;
    private JList<TaskCount> empUncompletedList;
    private DefaultListModel<TaskCount> empUncompletedListModel;
    private JList<TaskCount> empCompletedList;
    private DefaultListModel<TaskCount> empCompletedModel;
    private JScrollPane managementScroll;
    private JScrollPane employeeWorkScroll;
    private JScrollPane filteredEmployeesScroll;
    private JScrollPane empListScroll;
    private JScrollPane empUncompletedScroll;
    private JScrollPane empCompletedScroll;

    Serialization serialization = new Serialization();

    Controller controller = new Controller(this);

    public View(String name) {
        super(name);
        this.prepareGUI();
        this.loadEmployees();
        this.loadTasks();
        this.loadManagement();
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = serialization.deserializeTask();
            for (Task task : tasks) {
                this.addTasksComboBox(task);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return tasks;
    }

    public void prepareGUI() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(700, 700);
        this.setResizable(true);
        this.setLocation(350, 0);
        ImageIcon icon = new ImageIcon("C:\\Users\\Ruxanda\\Desktop\\an2\\tp\\tema1\\img.jpg");
        this.setIconImage(icon.getImage());
        this.contentPane = new JPanel(new GridBagLayout());
        this.prepareEmployeePanel();
        this.prepareTasksPanel();
        this.prepareManagementPanel();
        this.prepareButtonPanel();
        this.setContentPane(this.contentPane);
    }

    public void prepareEmployeePanel() {
        this.employeePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c0 = setConstraints(0,0,1,1.0,1.0);
        GridBagConstraints c1 = setConstraints(0, 0, 1, 1.0,1.0);
        GridBagConstraints c2 = setConstraints(0, 1, 1,1.0,1.0);
        GridBagConstraints c3 = setConstraints(1, 0, 1,1.0,1.0);
        GridBagConstraints c4 = setConstraints(1, 1, 1, 1.0,1.0);
        GridBagConstraints c5 = setConstraints(0, 2, 1, 1.0,1.0);
        GridBagConstraints c6 = setConstraints(1, 2, 1, 1.0,1.0);
        GridBagConstraints c7 = setConstraints(0, 3, 2, 1.0,1.0);
        this.employeePanel.add(new JLabel("employee ID:"), c1);
        this.employeeID = new JTextField();
        this.employeePanel.add(this.employeeID, c2);
        this.employeePanel.add(new JLabel("employee name:"), c3);
        this.employeeName = new JTextField();
        this.employeePanel.add(this.employeeName, c4);
        this.addEmployeeButton = new JButton("Add employee");
        this.addEmployeeButton.setActionCommand("ADD EMPLOYEE");
        this.addEmployeeButton.addActionListener(this.controller);
        this.employeePanel.add(this.addEmployeeButton, c6);
        this.clearEmployeeFieldsButton = new JButton("Clear");
        this.clearEmployeeFieldsButton.setActionCommand("CLEAR EMPLOYEE");
        this.clearEmployeeFieldsButton.addActionListener(this.controller);
        this.employeePanel.add(this.clearEmployeeFieldsButton, c5);
        this.employees = new JComboBox<>();
        this.employeePanel.add(this.employees, c7);
        this.contentPane.add(this.employeePanel, c0);
    }

    public GridBagConstraints setConstraints(int gridx, int gridy, int gridWidth, double weightx, double weighy) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridWidth;
        c.weightx = weightx;
        c.weighty = weighy;
        c.insets = new Insets(5, 5, 5, 5);
        return c;
    }

    public JTextField getEmployeeIDTextField() {
        return employeeID;
    }

    public List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = serialization.deserializeEmployee();
            for (Employee emp : employees) {
                this.addEmployeesComboBox(emp);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return employees;
    }

    public JTextField getEmployeeNameTextField() {
        return employeeName;
    }

    public void addEmployeesComboBox(Employee e) {
        this.employees.addItem(e);
    }

    public JComboBox<Employee> getSelectedEmployee() {
        return this.employees;
    }

    public void clearEmployeeFields() {
        this.employeeName.setText("");
        this.employeeID.setText("");
    }

    public void prepareTasksPanel() {
        GridBagConstraints c0 = setConstraints(0,1,1,1.0,1.0);
        GridBagConstraints c1 = setConstraints(0, 0, 1, 1.0,1.0);
        GridBagConstraints c2 = setConstraints(0, 1, 1, 1.0,1.0);
        GridBagConstraints c3 = setConstraints(0,2,1, 1.0,1.0);
        GridBagConstraints c4 = setConstraints(0,3,1, 1.0,1.0);
        this.tasksPanel = new JPanel(new GridBagLayout());
        this.tasksPanel1 = new JPanel(new GridBagLayout());
        this.tasksPanel2 = new JPanel(new GridBagLayout());
        this.tasks = new JComboBox<>();
        prepareTasksPanel1();
        prepareTasksPanel2();
        this.addToComplexTaskButton = new JButton("Add to complex task");
        this.addToComplexTaskButton.setActionCommand("ADD TO TASK");
        this.addToComplexTaskButton.addActionListener(this.controller);
        this.tasksPanel.add(this.tasksPanel1, c1);
        this.tasksPanel.add(this.tasksPanel2,c2);
        this.tasksPanel.add(this.tasks, c3);
        this.tasksPanel.add(this.addToComplexTaskButton,c4);
        this.contentPane.add(this.tasksPanel, c0);
    }

    public void prepareTasksPanel1() {
        GridBagConstraints c1 = setConstraints(0, 0, 1, 1.0,1.0);
        GridBagConstraints c2 = setConstraints(0, 1, 1, 1.0,1.0);
        GridBagConstraints c3 = setConstraints(1, 0, 1, 1.0,1.0);
        GridBagConstraints c4 = setConstraints(1, 1, 1, 1.0,1.0);
        GridBagConstraints c5 = setConstraints(2, 0, 1, 1.0,1.0);
        GridBagConstraints c6 = setConstraints(2, 1, 1, 1.0,1.0);
        this.tasksPanel1.add(new JLabel("task ID:"), c1);
        this.taskID = new JTextField();
        this.tasksPanel1.add(this.taskID, c2);
        this.tasksPanel1.add(new JLabel("task status:"), c3);
        this.taskStatus = new JTextField();
        this.tasksPanel1.add(this.taskStatus, c4);
        this.tasksPanel1.add(new JLabel("task type:"), c5);
        this.taskStatusSelecter = new JComboBox<>(new String[]{"Simple", "Complex"});
        this.tasksPanel1.add(this.taskStatusSelecter, c6);
    }

    public void prepareTasksPanel2(){
        GridBagConstraints c1 = setConstraints(2, 0, 1, 1.0,1.0);
        GridBagConstraints c2 = setConstraints(0, 0, 1, 1.0,1.0);
        this.addTaskButton = new JButton("Add task");
        this.addTaskButton.addActionListener(e -> {
            String selectedTaskType = (String) taskStatusSelecter.getSelectedItem();
            if ("Simple".equals(selectedTaskType)) {
                openFrame2();
            } else {
                this.controller.actionPerformed(new java.awt.event.ActionEvent(this.addTaskButton, ActionEvent.ACTION_PERFORMED, "ADD COMPLEX TASK"));
            }
        });
        this.tasksPanel2.add(this.addTaskButton, c1);
        this.clearTaskFieldsButton = new JButton("Clear");
        this.clearTaskFieldsButton.setActionCommand("CLEAR TASK");
        this.clearTaskFieldsButton.addActionListener(this.controller);
        this.tasksPanel2.add(this.clearTaskFieldsButton, c2);
    }

    public JComboBox<Task> getSelectedTask() {
        return this.tasks;
    }

    public void openFrame2() {
        this.frame2 = new JFrame();
        createFrame(frame2);
        this.contentPane2 = new JPanel(new GridBagLayout());
        prepareFrame2();
        frame2.setContentPane(this.contentPane2);
    }

    public void prepareFrame2() {
        GridBagConstraints c1 = setConstraints(0, 0, 1, 1.0,1.0);
        GridBagConstraints c2 = setConstraints(1, 0, 1, 1.0,1.0);
        GridBagConstraints c3 = setConstraints(0, 1, 1, 1.0,1.0);
        GridBagConstraints c4 = setConstraints(1, 1, 1, 1.0,1.0);
        GridBagConstraints c5 = setConstraints(0, 2, 2, 1.0,1.0);
        contentPane2.add(new JLabel("Start hour:"), c1);
        contentPane2.add(new JLabel("End hour:"), c2);
        this.startHour = new JTextField();
        contentPane2.add(this.startHour, c3);
        this.endHour = new JTextField();
        contentPane2.add(this.endHour, c4);
        this.okButton = new JButton("OK");
        this.okButton.setActionCommand("ADD TASK");
        this.okButton.addActionListener(this.controller);
        this.okButton.addActionListener(e -> closeFrame2());
        contentPane2.add(this.okButton, c5);
    }

    public void closeFrame2() {
        if (this.frame2 != null) {
            this.frame2.dispose();
        }
    }

    public void clearTaskFields() {
        this.taskID.setText("");
        this.taskStatus.setText("");
    }

    public JTextField getTaskIDTextField() {
        return this.taskID;
    }

    public JTextField getTaskStatusTextField() {
        return this.taskStatus;
    }

    public JTextField getStartHourTextField() {
        return this.startHour;
    }

    public JTextField getEndHourTextField() {
        return this.endHour;
    }

    public void repeatTask() {
        JOptionPane.showMessageDialog(new JFrame(), "A task with this ID already exists!");
    }

    public void addTasksComboBox(Task task) {
        this.tasks.addItem(task);
    }

    public void removeTasksComboBox(Task task){
        this.tasks.removeItem(task);
    }

    public void removeTasksComboBoxAt(int i){
        if(i == -1){
            this.tasks.removeItemAt(0);
            return;
        }
        this.tasks.removeItemAt(i);
    }

    public List<Task> getComboBoxTasks(){
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < tasks.getItemCount(); i++) {
            taskList.add(tasks.getItemAt(i));
        }
        return taskList;
    }

    public TasksManagement loadManagement(){
        TasksManagement management = new TasksManagement();
        Map<Employee,List<Task>> m = new HashMap<>();
        try {
            management = serialization.deserializeTasksManagement();
            m = serialization.deserializeTasksManagement().getMap();
            for(Map.Entry<Employee,List<Task>> entry : m.entrySet()){
                managementModel.addElement(new ManagementEntry(entry.getKey(),entry.getValue()));
                employeeWorkHoursModel.addElement(new TaskCount( entry.getKey().toString(),management.calculateEmployeeWorkDuration(entry.getKey().getIdEmployee())));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return management;
    }

    public void prepareManagementPanel(){
        this.managementPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c0 = setConstraints(0,2,1,1.0,1.0);
        c0.gridheight = 2;
        GridBagConstraints c1 = setConstraints(0,0,2,1.0,1.0);
        GridBagConstraints c2 = setConstraints(2,0,1,1.0,1.0);
        this.management = new JList<>();
        this.employeeWorkHours = new JList<>();
        this.managementModel = new DefaultListModel<>();
        this.employeeWorkHoursModel = new DefaultListModel<>();
        this.management.setModel(managementModel);
        this.employeeWorkHours.setModel(employeeWorkHoursModel);
        this.managementScroll = new JScrollPane(management);
        this.employeeWorkScroll = new JScrollPane(employeeWorkHours);
        this.managementPanel.add(managementScroll,c1);
        this.managementPanel.add(employeeWorkScroll,c2);
        this.managementPanel.setMinimumSize(new Dimension(200,200));
        this.contentPane.add(this.managementPanel,c0);
    }

    public void updateEmployeeTaskList(Employee emp,List<Task> tasks, TasksManagement m){
        for (int i = 0; i < managementModel.getSize(); i++) {
            ManagementEntry entry = managementModel.getElementAt(i);
            if (entry.getEmployee().equals(emp)) {
                managementModel.removeElementAt(i);
                employeeWorkHoursModel.removeElementAt(i);
                break;
            }
        }
        managementModel.addElement(new ManagementEntry(emp,tasks));
        employeeWorkHoursModel.addElement(new TaskCount(emp.toString(),m.calculateEmployeeWorkDuration(emp.getIdEmployee())));
    }

    public void prepareButtonPanel(){
        GridBagConstraints c0 = setConstraints(0,4,1,1.0,1.0);
        GridBagConstraints c1 = setConstraints(1,0,1,1.0,1.0);
        GridBagConstraints c2 = setConstraints(0,0,1,1.0,1.0);
        GridBagConstraints c3 = setConstraints(2,0,1,1.0,1.0);
        GridBagConstraints c4 = setConstraints(3,0,1,1.0,1.0);
        this.buttonPanel = new JPanel(new GridBagLayout());
        this.assignTaskButton = new JButton("Assign task");
        this.assignTaskButton.setActionCommand("ASSIGN TASK");
        this.assignTaskButton.addActionListener(this.controller);
        this.modifyStatusButton = new JButton("Modify status");
        this.modifyStatusButton.addActionListener(e-> openFrame3());
        this.filterButton = new JButton("Filter employees");
        this.filterButton.setActionCommand("FILTER");
        this.filterButton.addActionListener(this.controller);
        this.calculusButton = new JButton("Calculate tasks");
        this.calculusButton.setActionCommand("CALC");
        this.calculusButton.addActionListener(this.controller);
        this.buttonPanel.add(this.assignTaskButton,c1);
        this.buttonPanel.add(this.modifyStatusButton,c2);
        this.buttonPanel.add(this.filterButton,c3);
        this.buttonPanel.add(this.calculusButton,c4);
        this.contentPane.add(buttonPanel,c0);
    }

    public void prepareFrame5(List<String> emp, List<Integer> uncompletedTask, List<Integer> completedTask){
        frame5 = new JFrame();
        createFrame(frame5);
        this.contentPane5 = new JPanel(new GridBagLayout());
        GridBagConstraints c0 = setConstraints(0,0,1,1.0,1.0);
        GridBagConstraints c1 = setConstraints(1,0,1,1.0,1.0);
        GridBagConstraints c2 = setConstraints(2,0,1,1.0,1.0);
        this.empList = new JList<>();
        this.empListModel = new DefaultListModel<>();
        this.empUncompletedList = new JList<>();
        this.empUncompletedListModel = new DefaultListModel<>();
        this.empCompletedList = new JList<>();
        this.empCompletedModel = new DefaultListModel<>();
        for(String s: emp){
            empListModel.addElement(s);
        }
        for(Integer i : uncompletedTask){
            empUncompletedListModel.addElement(new TaskCount("Uncompleted",i));
        }
        for(Integer i : completedTask){
            empCompletedModel.addElement(new TaskCount("Completed " ,i ));
        }
        this.empList.setModel(empListModel);
        this.empUncompletedList.setModel(empUncompletedListModel);
        this.empCompletedList.setModel(empCompletedModel);
        this.empListScroll = new JScrollPane(empList);
        this.empUncompletedScroll = new JScrollPane(empUncompletedList);
        this.empCompletedScroll = new JScrollPane(empCompletedList);
        contentPane5.add(empListScroll,c0);
        contentPane5.add(empUncompletedScroll,c1);
        contentPane5.add(empCompletedScroll,c2);
        frame5.setContentPane(this.contentPane5);
    }

    public void prepareFrame4(List<String> filteredList){
        this.frame4 = new JFrame();
        createFrame(frame4);
        this.contentPane4 = new JPanel(new GridBagLayout());
        GridBagConstraints c0 = setConstraints(0,0,1,1.0,1.0);
        this.filteredEmployeesList = new JList<>();
        this.filteredEmployeesModel = new DefaultListModel<>();
        for(String s: filteredList){
            filteredEmployeesModel.addElement(s);
            System.out.println(s);
        }
        this.filteredEmployeesList.setModel(filteredEmployeesModel);
        this.filteredEmployeesScroll = new JScrollPane(filteredEmployeesList);
        contentPane4.add(filteredEmployeesScroll,c0);
        frame4.setContentPane(this.contentPane4);
    }

    public void openFrame3(){
        this.frame3 = new JFrame();
        createFrame(frame3);
        this.contentPane3 = new JPanel(new GridBagLayout());
        prepareFrame3();
        frame3.setContentPane(this.contentPane3);
    }

    public void createFrame(JFrame frame) {
        frame.setSize(500, 500);
        frame.setResizable(true);
        frame.setLocation(350, 0);
        ImageIcon icon = new ImageIcon("C:\\Users\\Ruxanda\\Desktop\\an2\\tp\\tema1\\img.jpg");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void prepareFrame3(){
        GridBagConstraints c1 = setConstraints(0,0,1,1.0,1.0);
        GridBagConstraints c2 = setConstraints(0,1,1,1.0,1.0);
        GridBagConstraints c3 = setConstraints(0,2,1,1.0,1.0);
        contentPane3.add(new JLabel("Task ID:"), c1);
        this.taskID2 = new JTextField();
        contentPane3.add(this.taskID2,c2);
        this.modifyStatusButton2 = new JButton("OK");
        this.modifyStatusButton2.setActionCommand("MODIFY TASK");
        this.modifyStatusButton2.addActionListener(this.controller);
        this.modifyStatusButton2.addActionListener(e -> closeFrame3());
        contentPane3.add(this.modifyStatusButton2,c3);
    }

    public void closeFrame3(){
        if (this.frame3 != null) {
            this.frame3.dispose();
        }
    }

    public JTextField getTaskID2TextField(){
        return this.taskID2;
    }

    public static void main(String[] args) {
        JFrame frame = new View("Task Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
