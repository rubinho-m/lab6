/**
 * The RemoveGreaterCommand class implements the execute method of the Command interface
 * to remove all elements in collection which are bigger than argument.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import server.collectionManagement.CollectionManager;

import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveGreaterCommand extends CommandTemplate implements CommandWithResponse{
    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        Set<Ticket> tickets = getCollectionManager().getCollection();
        if (tickets.size() == 0){
            throw new EmptyCollectionException();
        }
        Set<Ticket> removeTickets = new LinkedHashSet<>();
        for (Ticket ticket : tickets){
            if (ticket.compareTo(getTicket()) > 0){
                removeTickets.add(ticket);
            }
        }
        for (Ticket ticketToRemove : removeTickets){
            tickets.remove(ticketToRemove);
        }
    }

    @Override
    public Response getCommandResponse() {
        return null;
    }
}
