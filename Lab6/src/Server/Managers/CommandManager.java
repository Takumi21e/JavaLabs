package Server.Managers;

import Common.Network.*;
import Server.Commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Управляет командами и их обработчиками.
 * Отвечает за:
 * - регистрацию обработчиков команд
 * - маршрутизацию команд к соответствующим обработчикам
 * - выполнение команд
 */
public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Инициализирует CommandExecutor с обработчиками всех команд.
     *
     * @param collectionManager менеджер коллекции
     */
    public CommandManager(CollectionManager collectionManager) {
        commands.put("help", new Help());
        commands.put("info", new Info(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("add", new Add(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));
        commands.put("update", new Update(collectionManager));
        commands.put("add_if_max", new AddIfMax(collectionManager));
        commands.put("remove_greater", new RemoveGreater(collectionManager));
        commands.put("shuffle", new Shuffle(collectionManager));
        commands.put("filter_greater_than_part_number", new FilterGreaterThanPartNumber(collectionManager));
        commands.put("filter_by_owner", new FilterByOwner(collectionManager));
        commands.put("filter_less_than_owner", new FilterLessThanOwner(collectionManager));
        commands.put("save", new Save(collectionManager));
    }

    /**
     * Выполняет команду на основе запроса.
     *
     * @param request запрос с типом и параметрами команды
     * @return ответ с результатом выполнения
     */
    public Response execute(Request request) {
        Command command = commands.get(request.getCommandName());
        if (command == null) {
            return new Response("Неизвестная команда.");
        }
        return command.execute(request);
    }

    public String getHelp() {
        StringBuilder help = new StringBuilder("Доступные команды:\n");
        commands.forEach((type, command) ->
            help.append("  ").append(command.getName())
                .append(" - ").append(command.getDescription()).append("\n")
        );
        return help.toString();
    }
}
