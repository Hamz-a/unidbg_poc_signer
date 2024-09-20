package me.bhamza.example;

public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        Signer signer = new Signer("/tmp/libhellosignjni.so");

        long t2 = System.currentTimeMillis();
        System.out.println("Execution time for initialization: " + ((t2 - t1) / 1000.0) + " seconds");

        String signature = signer.sign("hellosign");
        System.out.println("Signature: " + signature);
        long t3 = System.currentTimeMillis();

        System.out.println("Execution time for signing: " + ((t3 - t2) / 1000.0) + " seconds");
    }
}