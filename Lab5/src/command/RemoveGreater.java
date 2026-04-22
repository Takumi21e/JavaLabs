package command;

import manager.CollectionManager;
import model.Product;
import util.InputManager;

public class RemoveGreater implements Command {

    private final InputManager inputManager;
    private final CollectionManager collectionManager;

    public RemoveGreater(CollectionManager collectionManager,
                         InputManager inputManager){
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }
    @Override
    public String getName(){
        return "remove_greater";
    }
    @Override
    public String getDescription(){
        return "удалить элементы, превышающие заданный";
    }
    @Override
    public void execute(String[] args){
        try {
            System.out.println("Введите элемент для сравнения:");
            Product newProduct = inputManager.readProduct();
            int before = collectionManager.size();
            collectionManager.getAll().removeIf(target -> target.compareTo(newProduct) > 0);
            int after = collectionManager.size();
            System.out.println("Удалено элементов: " + (before-after));
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
