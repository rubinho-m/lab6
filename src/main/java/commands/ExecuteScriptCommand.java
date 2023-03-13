/**
 * The ExecuteScriptCommand class implements the execute method of the Command interface
 * to execute the user's script with commands.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package commands;

import collectionManagement.CollectionManager;
import collectionManagement.CommandExecutor;
import dataStructures.ParsedString;
import exceptions.ScriptRecursionException;
import io.commandParsing.CommandParser;
import structureClasses.Ticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ExecuteScriptCommand extends CommandTemplate implements Command {
    private final CommandExecutor commandExecutor;

    public ExecuteScriptCommand(CollectionManager collectionManager, CommandExecutor commandExecutor) {
        super(collectionManager);
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute() throws ScriptRecursionException {
        try {
            File file = new File(getArg());
            Scanner scanner = new Scanner(file);
            CommandParser commandParser = new CommandParser();
            if (commandExecutor.getPaths().contains(getArg())) {
                throw new ScriptRecursionException();
            }
            commandExecutor.addToPaths(getArg());
            while (true) {
                try {
                    ParsedString<ArrayList<String>, Ticket> parsedString = commandParser.readCommand(scanner, true);
                    commandExecutor.execute(parsedString, scanner, true);
                } catch (Exception e) {
                    break;
                }
            }
            commandExecutor.clearPaths();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (ScriptRecursionException e){
            System.out.println("Looping scripts... Please fix it");
        }
        catch (Exception e) {
            System.out.println("Some troubles with your script. Please fix it");
        }

    }
}
