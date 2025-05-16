/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.api.de.cadastro;


public class Person {
    private String name;
    private int age;
    
    public Person(){
        
    }
    public Person(String name, int age) {
        this.name = name;
        this.age= age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName( String name) {
        this.name = name;
    }
    
    public Integer getAge () {
        return age;
    }
    
    public void setage(Integer age) {
        this.age=age;
    }
    
    @Override
    public String toString() {
        return "Person{"+
                "name= '" +name + "\'" +
                ", age =" + age + 
                '}';
                
    }
}
