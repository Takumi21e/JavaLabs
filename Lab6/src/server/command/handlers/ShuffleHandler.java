package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

/**
 * Обработчик команды SHUFFLE.
 * Перемешивает элементы коллекции в случайном порядке.
 */
public class ShuffleHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public ShuffleHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "перемешать элементы коллекции";
    }

    /**
     * Выполняет команду shuffle - перемешивает элементы в случайном порядке.
     *
     * @param request запрос
     * @return ответ с результатом
     */
    @Override
    public Response execute(Request request) {

        collectionManager.shuffle();

        return new Response(true, "Коллекция перемешана.");
    }
}
