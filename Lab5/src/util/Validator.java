package util;

import java.util.Scanner;

public class Validator {

    private final Scanner scanner;

    public Validator(Scanner scanner) {
        this.scanner = scanner;
    }

    public double inputDouble(String str) {
        while (true) {
            try {
                return Double.parseDouble(str);
            } catch (Exception e) {
                System.out.println("Введите число:");
            }
        }
    }
}
