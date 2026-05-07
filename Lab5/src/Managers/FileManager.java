package Managers;

import Model.*;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Stack;


/**
 * Отвечает за работу с файлами.
 *
 * Обеспечивает:
 * - чтение коллекции из CSV
 * - запись коллекции в файл
 */
public class FileManager {

    private final String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Отвечает за сохранение в файл
     */
    public void save(Stack<Product> collection) {
        try (OutputStreamWriter writer =
                     new OutputStreamWriter(new FileOutputStream(fileName))) {

            for (Product p : collection) {
                writer.write(toCSV(p) + "\n");
            }
            System.out.println("Коллекция сохранена.");

        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    private String toCSV(Product p) {
        return p.getId() + "," +
                p.getName() + "," +
                p.getCoordinates().getX() + "," +
                p.getCoordinates().getY() + "," +
                p.getCreationDate().getTime() + "," +
                p.getPrice() + "," +
                nullSafe(p.getPartNumber()) + "," +
                nullSafe(p.getUnitOfMeasure()) + "," +
                p.getOwner().getName() + "," +
                p.getOwner().getBirthday() + "," +
                p.getOwner().getHeight() + "," +
                nullSafe(p.getOwner().getWeight()) + "," +
                nullSafe(p.getOwner().getNationality());
    }

    private String nullSafe(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * Загрузка коллекции из CSV
     */
    public Stack<Product> load() {
        Stack<Product> collection = new Stack<>();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {

            String line;
            long maxId = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                Product p = fromCSV(line);
                collection.add(p);

                if (p.getId() > maxId) {
                    maxId = p.getId();
                }
            }

            IdGenerator.update(maxId);

            System.out.println("Коллекция загружена. Элементов: " + collection.size());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка формата данных: " + e.getMessage());
        }

        return collection;
    }

    /**
     * Парсинг строки CSV → Product
     */
    private Product fromCSV(String line) {
        String[] parts = line.split(",", -1); // -1 сохраняет пустые поля

        long id = Long.parseLong(parts[0]);
        String name = parts[1];

        double x = Double.parseDouble(parts[2]);
        float y = Float.parseFloat(parts[3]);

        long creationTime = Long.parseLong(parts[4]); // можно игнорировать

        Long price = Long.parseLong(parts[5]);

        String partNumber = parts[6].isEmpty() ? null : parts[6];

        UnitOfMeasure uom = parts[7].isEmpty() ? null : UnitOfMeasure.valueOf(parts[7]);

        String ownerName = parts[8];
        LocalDate birthday = LocalDate.parse(parts[9]);
        long height = Long.parseLong(parts[10]);
        Long weight = parts[11].isEmpty() ? null : Long.parseLong(parts[11]);
        Country nationality = parts[12].isEmpty() ? null : Country.valueOf(parts[12]);

        Coordinates coordinates = new Coordinates(x, y);
        Person owner = new Person(ownerName, birthday, height, weight, nationality);

        // используем конструктор с id
        return new Product(
                id,
                name,
                coordinates,
                price,
                partNumber,
                uom,
                owner
        );
    }
}
