package command;

import manager.CollectionManager;
import model.Product;
import util.InputManager;

/**
 * Команда добавления элемента
 */
public class Add implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    public Add(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    @Override
    public String getName() {
        return "add";
    }
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
    @Override
    public void execute(String[] args) {
        try {
            Product product = inputManager.readProduct();
            collectionManager.add(product);
            System.out.println("Элемент добавлен.");
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }
}