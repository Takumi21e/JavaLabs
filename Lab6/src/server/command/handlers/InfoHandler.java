package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

/**
 * Обработчик команды INFO.
 * Выводит информацию о коллекции.
 */
public class InfoHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public InfoHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }

    /**
     * Выполняет команду info - выводит информацию о коллекции.
     *
     * @param request запрос
     * @return ответ с информацией
     */
    @Override
    public Response execute(Request request) {

        String result = "Тип коллекции: "
                        + collectionManager
                        .getCollection()
                        .getClass()
                        .getSimpleName()
                        + "\nКоличество элементов: "
                        + collectionManager
                        .getCollection()
                        .size()
                        + "\nДата инициализации: "
                        + collectionManager
                        .getInitializationDate();

        return new Response(true, result);
    }
}
