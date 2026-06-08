package client.command;

import client.console.InputManager;
import common.network.CommandType;
import common.network.Request;

public class ClientCommandParser {

    private final InputManager inputManager;

    public ClientCommandParser(
            InputManager inputManager
    ) {
        this.inputManager = inputManager;
    }

    public Request parse(String line) {

        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase();

        switch (command) {

            case "info":
                return new Request(CommandType.INFO);

            case "show":
                return new Request(CommandType.SHOW);

            case "clear":
                return new Request(CommandType.CLEAR);

            case "add":

                Request request = new Request(CommandType.ADD);
                request.setProduct(inputManager.readProduct());
                return request;

            case "remove_by_id":

                if (parts.length != 2) {
                    System.out.println("Использование: remove_by_id <id>");
                    return null;
                }

                try {

                    long id = Long.parseLong(parts[1]);
                    Request request1 = new Request(CommandType.REMOVE_BY_ID);
                    request1.setId(id);
                    return request1;

                } catch (NumberFormatException e) {

                    System.out.println("id должен быть числом.");
                    return null;
                }

            case "update":

                if (parts.length != 2) {
                    System.out.println("Использование: update <id>");
                    return null;
                }

                try {

                    long id = Long.parseLong(parts[1]);
                    Request request2 = new Request(CommandType.UPDATE);
                    request2.setId(id);
                    request2.setProduct(inputManager.readProduct());
                    return request2;

                } catch (NumberFormatException e) {
                    System.out.println("id должен быть числом.");
                    return null;
                }

            case "filter_by_owner":

                if (parts.length != 2) {
                    System.out.println("Использование: filter_by_owner <owner>");
                    return null;
                }

                Request request3 = new Request(CommandType.FILTER_BY_OWNER);
                request3.setStringArgument(parts[1]);
                return request3;

            case "filter_greater_than_part_number":

                if (parts.length != 2) {
                    System.out.println("Использование: filter_greater_than_part_number <partNumber>");
                    return null;
                }

                Request partNumberRequest = new Request(CommandType.FILTER_GREATER_THAN_PART_NUMBER);
                partNumberRequest.setStringArgument(parts[1]);
                return partNumberRequest;

            case "shuffle":

                return new Request(CommandType.SHUFFLE);

            case "add_if_max":

                Request addIfMaxRequest = new Request(CommandType.ADD_IF_MAX);
                addIfMaxRequest.setProduct(inputManager.readProduct());
                return addIfMaxRequest;

            case "remove_greater":

                Request removeGreaterRequest = new Request(CommandType.REMOVE_GREATER);
                removeGreaterRequest.setProduct(inputManager.readProduct());
                return removeGreaterRequest;

            case "filter_less_than_owner":

                if (parts.length != 2) {
                    System.out.println("Использование: filter_less_than_owner <owner>");
                    return null;
                }

                Request lessOwnerRequest = new Request(CommandType.FILTER_LESS_THAN_OWNER);
                lessOwnerRequest.setStringArgument(parts[1]);
                return lessOwnerRequest;

            default:
                return null;
        }
    }
}