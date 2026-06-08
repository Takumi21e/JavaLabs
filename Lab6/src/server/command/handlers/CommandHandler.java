package server.command.handlers;

import common.network.Request;
import common.network.Response;

public interface CommandHandler {
    Response execute(Request request);
}