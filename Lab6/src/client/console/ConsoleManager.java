package client.console;

import java.util.Scanner;

public class ConsoleManager {

    private final Scanner scanner;

    public ConsoleManager() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public Scanner getScanner() {
        return scanner;
    }
}