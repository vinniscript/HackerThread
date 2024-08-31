package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static final int MAX_PASSWORD = 9999;


    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        threads.add(new Ascending(vault));
        threads.add(new Descending(vault));
        threads.add(new PoliceThread());

        for(Thread thread : threads) {
            thread.start();
        }
    }

    public static class Vault {

        int password;

        public Vault(int password) { // Constructor
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("Erro em Thread " + Thread.currentThread().getName());
            }

            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {

        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Iniciando thread" + this.getName());
            super.start();
        }

    }

    private static class Ascending extends HackerThread {

        public Ascending(Vault vault) {
            super(vault);

        }

        // Código que é executado quando a Thread inicializa.
        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " Acertou a senha. " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class Descending extends HackerThread {

        public Descending(Vault vault) {
            super(vault);

        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " acertou a senha. " + guess);
                    System.exit(0);
                }
            }
        }

    }

    public static class PoliceThread extends Thread {
        @Override
        public void run() {
            for(int i = 10 ; i >= 0; i--){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Erro em " + Thread.currentThread().getName());
                }
                System.out.println(i);
            }
            System.out.println("Game over.");
            System.exit(0);
        }

    }

}

