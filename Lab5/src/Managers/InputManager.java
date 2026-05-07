package Managers;

import Model.*;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Отвечает за ввод данных от пользователя или из файла.
 *
 * Обеспечивает:
 * - чтение примитивных типов
 * - чтение сложных объектов (Product)
 * - обработку ошибок ввода
 */
public class InputManager {

    private final Scanner scanner;

    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public Product readProduct() {
        System.out.print("Введите product.name: ");
        String name = readNonEmptyString();

        System.out.print("Введите coordinate.x: ");
        double x = readDouble();

        System.out.print("Введите coordinate.y: ");
        float y = readFloat();

        Coordinates coordinates = new Coordinates(x, y);

        System.out.print("Введите price: ");
        Long price = readLongObj(false);

        System.out.print("Введите partNumber (или пусто): ");
        String pn = scanner.nextLine();
        String partNumber = pn.isEmpty() ? null : pn;

        printEnum(UnitOfMeasure.values());
        System.out.print("Введите unitOfMeasure (или пусто): ");
        UnitOfMeasure uom = readUnitOfMeasure();

        Person owner = readPerson();

        return new Product(name, coordinates, price, partNumber, uom, owner);
    }

    private Person readPerson() {
        System.out.print("Введите owner.name: ");
        String name = readNonEmptyString();

        System.out.print("Введите owner.birthday (yyyy-mm-dd): ");
        LocalDate birthday = readDate();

        System.out.print("Введите owner.height: ");
        long height = readLong();

        System.out.print("Введите owner.weight (или пусто): ");
        Long weight = readLongObj(true);

        printEnum(Country.values());
        System.out.print("Введите owner.nationality (или пусто): ");
        Country nationality = readCountry();

        return new Person(name, birthday, height, weight, nationality);
    }

    private UnitOfMeasure readUnitOfMeasure(){
        while (true){
            try {
                String str = scanner.nextLine();
                if (str.isEmpty()){
                    return null;
                }
                return UnitOfMeasure.valueOf(str);
            }catch (Exception e){
                System.out.print("Ошибка: введите единицы измерения (UnitOfMeasure) либо ничего: ");
            }
        }
    }

    private Country readCountry(){
        while (true){
            try {
                String str = scanner.nextLine();
                if (str.isEmpty()){
                    return null;
                }
                return Country.valueOf(str);
            }catch (Exception e){
                System.out.print("Ошибка: введите страну (Country) либо ничего: ");
            }
        }
    }

    private LocalDate readDate() {
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Ошибка: введите дату в формате yyyy-mm-dd: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Ошибка: введите число (double): ");
            }
        }
    }
    private float readFloat() {
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Ошибка: введите число (float): ");
            }
        }
    }
    private long readLong() {
        while (true) {
            try {
                String str = scanner.nextLine();
                long value = Long.parseLong(str);
                if (value > 0){
                    return value;
                }
                System.out.print("Ошибка: число должно быть больше 0: ");
            } catch (Exception e) {
                System.out.print("Ошибка: введите число (long): ");
            }
        }
    }
    private Long readLongObj(boolean canbenull) {
        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty() && canbenull){
                return null;
            } else {
                try {
                    long value = Long.parseLong(input);
                    if (value > 0) {
                        return value;
                    }
                    System.out.print("Ошибка: число должно быть больше 0: ");
                } catch (Exception e) {
                    System.out.print("Ошибка: введите число (Long): ");
                }
            }
        }
    }
    private String readNonEmptyString() {
        while (true) {
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) { return input; }
            System.out.print("Строка не может быть пустой, повторите: ");
        }
    }

    private void printEnum(Object[] values) {
        for (Object v : values) {
            System.out.println(v);
        }
    }
}