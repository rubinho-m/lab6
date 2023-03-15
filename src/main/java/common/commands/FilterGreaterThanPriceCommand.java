/**
 * The FilterGreaterThanPriceCommand class implements the execute method of the Command interface
 * to show all elements which price is greater than argument.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.networkStructures.Response;
import server.collectionManagement.CollectionManager;
import common.structureClasses.Ticket;

import java.util.Set;

public class FilterGreaterThanPriceCommand extends CommandTemplate implements CommandWithResponse{
    public FilterGreaterThanPriceCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        Set<Ticket> tickets = getCollectionManager().getCollection();
        for (Ticket ticket : tickets){
            if (ticket.getPrice() > Float.parseFloat(getArg())){
                System.out.println(ticket);
            }
        }
    }

    @Override
    public Response getCommandResponse() {
        return null;
    }
}
