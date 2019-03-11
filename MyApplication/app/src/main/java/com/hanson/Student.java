package com.hanson;

/*
    * This class will declare the fields and constructor to implement
    * the student array in the view activity
 */
public class Student {

    int id ; // id of the student
    String name; // name of the student
    boolean enrolled; // whether user is entere into array or not

    /*
        * default constructor
     */
    public Student(){
        id = 0;
        name = "";
        enrolled = true;
    }

    /*
        * Parameterized constructor to initialize the values
     */
    public Student(String a, int b){
        name = a;
        id = b;
        enrolled = true;
    }
}
