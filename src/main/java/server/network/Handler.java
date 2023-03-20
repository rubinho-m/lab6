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
    private boolean isServerCommand;

    public Handler(CommandExecutor commandExecutor, OutputStream outputStream, boolean isServerCommand) {
        this.commandExecutor = commandExecutor;
        this.outputStream = outputStream;
        this.isServerCommand = isServerCommand;
    }
    public Handler(CommandExecutor commandExecutor,  boolean isServerCommand) {
        this.commandExecutor = commandExecutor;
        this.isServerCommand = isServerCommand;
    }

    public void handleCommand(ParsedString<ArrayList<String>, Ticket> parsedString) throws Exception {
        Response response = commandExecutor.execute(parsedString);
        if (!parsedString.getArray().get(0).equals("save")){
            ArrayList<String> save = new ArrayList<>();
            save.add("save");
            ParsedString<ArrayList<String>, Ticket> saveString = new ParsedString<>(save);
            Response saveResponse = commandExecutor.execute(saveString);
        }
        if (!isServerCommand) {
            Writer writer = new Writer(outputStream);
            writer.write(response);
        }
    }
}
