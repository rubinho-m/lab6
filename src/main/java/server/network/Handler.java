package server.network;

import common.dataStructures.ParsedString;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CommandExecutor;

import java.io.OutputStream;
import java.util.ArrayList;

public class Handler {
    CommandExecutor commandExecutor;
    OutputStream outputStream;

    public Handler(CommandExecutor commandExecutor, OutputStream outputStream) {
        this.commandExecutor = commandExecutor;
        this.outputStream = outputStream;
    }

    public void handleCommand(ParsedString<ArrayList<String>, Ticket> parsedString) throws Exception {
        Response response = commandExecutor.execute(parsedString);
        Writer writer = new Writer(outputStream);
        writer.write(response);
    }
}
