package server.command;

import common.network.CommandType;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import server.command.handlers.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Управляет командами и их обработчиками.
 *
 * Отвечает за:
 * - регистрацию обработчиков команд
 * - маршрутизацию команд к соответствующим обработчикам
 * - выполнение команд
 */
public class CommandExecutor {

    private final Map<CommandType, CommandHandler> handlers = new HashMap<>();

    /**
     * Инициализирует CommandExecutor с обработчиками всех команд.
     *
     * @param collectionManager менеджер коллекции
     */
    public CommandExecutor(CollectionManager collectionManager) {

        handlers.put(CommandType.HELP, new HelpHandler());

        handlers.put(CommandType.INFO, new InfoHandler(collectionManager));

        handlers.put(CommandType.SHOW, new ShowHandler(collectionManager));

        handlers.put(CommandType.CLEAR, new ClearHandler(collectionManager));

        handlers.put(CommandType.ADD, new AddHandler(collectionManager));

        handlers.put(CommandType.REMOVE_BY_ID, new RemoveByIdHandler(collectionManager));

        handlers.put(CommandType.UPDATE, new UpdateHandler(collectionManager));

        handlers.put(CommandType.ADD_IF_MAX, new AddIfMaxHandler(collectionManager));

        handlers.put(CommandType.REMOVE_GREATER, new RemoveGreaterHandler(collectionManager));

        handlers.put(CommandType.SHUFFLE, new ShuffleHandler(collectionManager));

        handlers.put(CommandType.FILTER_GREATER_THAN_PART_NUMBER, new FilterGreaterThanPartNumberHandler(collectionManager));

        handlers.put(CommandType.FILTER_BY_OWNER, new FilterByOwnerHandler(collectionManager));

        handlers.put(CommandType.FILTER_LESS_THAN_OWNER, new FilterLessThanOwnerHandler(collectionManager));

        handlers.put(CommandType.SAVE, new SaveHandler(collectionManager));
    }

    /**
     * Выполняет команду на основе запроса.
     *
     * @param request запрос с типом и параметрами команды
     * @return ответ с результатом выполнения
     */
    public Response execute(Request request) {
        CommandHandler handler = handlers.get(request.getCommandType());
        if (handler == null) {
            return new Response(false, "Неизвестная команда.");
        }
        return handler.execute(request);
    }

    /**
     * Получить справку по всем доступным командам.
     *
     * @return строка со справкой
     */
    public String getHelp() {
        StringBuilder help = new StringBuilder("Доступные команды:\n");
        handlers.forEach((type, handler) -> 
            help.append("  ").append(handler.getName())
                .append(" - ").append(handler.getDescription()).append("\n")
        );
        return help.toString();
    }
}
