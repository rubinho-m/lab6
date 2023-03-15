package client; /**
 * The client.Main class is the entry point of the application. It initializes the collection manager, parses the XML file
 * containing the initial collection, and sets the path of the XML file. It also sets up a command executor with the
 * collection manager and a command parser. The program then enters a loop where it reads commands from the standard
 * input using the command parser and executes them using the command executor. If an exception is thrown during
 * command execution, the exception message is printed to the console.
 */

import common.dataStructures.ParsedString;
import common.networkStructures.Request;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import common.exceptions.XMLTroubleException;
import client.io.commandParsing.CommandParser;

import javax.xml.bind.UnmarshalException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
    /**
     * The main method is the entry point of the application.
     *
     * @param args command line arguments. The first argument should be the path to the XML file containing the initial collection.
     * @throws IOException         if there is an error reading the XML file.
     * @throws XMLTroubleException if there is an error parsing the XML file.
     */
    public static void main(String[] args) throws Exception {
        try {
//            CollectionManager collectionManager = new CollectionManager();
//            TicketXMLParser xmlParser = new TicketXMLParser(args[0]);
//            collectionManager.setCollection(xmlParser.parse());
//            collectionManager.setPath(args[0]);
//
            Scanner scanner = new Scanner(System.in);
            CommandParser commandParser = new CommandParser();
            NetworkConnection networkConnection = new NetworkConnection("127.0.0.1", 2828);

            while (true) {
                ParsedString<ArrayList<String>, Ticket> parsedString = commandParser.readCommand(scanner, false);
                ArrayList<String> commandWithArguments = parsedString.getArray();
                Ticket ticket = parsedString.getTicket();
                Request request = new Request(commandWithArguments, ticket);
                networkConnection.connectionManage(request);
                Response response = networkConnection.getReturnResponse();
                System.out.println(response.getOutput());
            }


//            CommandExecutor commandExecutor = new CommandExecutor();
//            commandExecutor.setCommands(collectionManager);



//            while (true) {
//                try {
//                    ParsedString<ArrayList<String>, Ticket> parsedString = commandParser.readCommand(scanner, false);
//                    ArrayList<String> commandWithArguments = parsedString.getArray();
//                    Ticket ticket = parsedString.getTicket();
//
//
//                } catch (NoSuchElementException e) {
//                    break;
//                }
//                catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            }
        } catch (XMLTroubleException e){
            System.exit(1);
        }


    }

}