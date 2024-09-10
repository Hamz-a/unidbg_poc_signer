package me.bhamza.example;

public class Main {
    public static void main(String[] args) {
        Signer signer = new Signer("/tmp/libhellosignjni.so");
        System.out.println(signer.sign("helloworld"));
    }
}