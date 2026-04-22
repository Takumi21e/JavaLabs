package command;

import manager.CollectionManager;
import model.Person;
import model.Product;
import util.InputManager;

public class FilterLessThanOwner implements Command {

    private final CollectionManager collectionManager;

    public FilterLessThanOwner(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName(){
        return "filter_less_than_owner";
    }
    @Override
    public String getDescription(){
        return "вывести элементы, значение поля owner которых меньше заданного";
    }
    @Override
    public void execute(String[] args){
        if (args.length < 2){
            System.out.println("Ошибка: укажите имя владельца");
        }
        String ownerName = args[1];
        for (Product product : collectionManager.getAll()){
            if (product.getOwner().getName().compareTo(ownerName) < 0){
                System.out.println(product);
            }
        }
    }
}
