package org.example.DataModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private int idEmployee;
    private String name;

    public Employee(int idEmployee, String name){
        this.idEmployee = idEmployee;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getIdEmployee(){
        return this.idEmployee;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setIdEmployee(int idEmployee){
        this.idEmployee = idEmployee;
    }

    public String toString(){
        return "Employee id: " + this.idEmployee + " name: " + this.name;
    }

    public int hashCode(){
        return Objects.hash(idEmployee, name);
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Employee emp)){
            return false;
        }
        return this.idEmployee == emp.getIdEmployee() && this.name.equals(emp.getName());
    }

}


