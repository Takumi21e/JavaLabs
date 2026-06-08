package server.command;

import common.network.*;
import server.collection.*;
import server.command.handlers.*;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private final Map<CommandType, CommandHandler> handlers = new HashMap<>();

    public CommandExecutor(CollectionManager collectionManager) {

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
    }

    public Response execute(Request request) {
        CommandHandler handler = handlers.get(request.getCommandType());
        if (handler == null) {
            return new Response(false, "Неизвестная команда.");
        }
        return handler.execute(request);
    }
}