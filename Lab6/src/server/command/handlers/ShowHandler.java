package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import java.util.stream.Collectors;

/**
 * Обработчик команды SHOW.
 * Выводит все элементы коллекции в отсортированном виде.
 */
public class ShowHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public ShowHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }

    /**
     * Выполняет команду show - выводит все элементы коллекции,
     * отсортированные по умолчанию (по цене).
     *
     * @param request запрос
     * @return ответ с выводом элементов
     */
    @Override
    public Response execute(Request request) {

        if (collectionManager.getCollection().isEmpty()) {
            return new Response(true, "Коллекция пуста.");
        }

        String result = collectionManager
                .getCollection()
                .stream()
                .sorted(Product::compareTo)
                .map(Product::toString)
                .collect(Collectors.joining("\n\n"));

        return new Response(true, result);
    }
}
