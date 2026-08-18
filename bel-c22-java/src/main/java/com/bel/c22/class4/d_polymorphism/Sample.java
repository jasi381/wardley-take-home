package com.bel.c22.class4.d_polymorphism;

public class Sample {
    void show(Long s) {
        System.out.println("String");
    }

    void show(Integer i) {
        System.out.println("Integer");
    }

    public static void main(String[] args) {
        Sample obj = new Sample();
        obj.show(1L); 
    }
}