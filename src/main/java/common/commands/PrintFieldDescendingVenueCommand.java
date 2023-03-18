/**
 * The PrintFieldDescendingCommand class implements the execute method of the Command interface
 * to show collection in sort of venues.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import common.exceptions.EmptyCollectionException;
import common.networkStructures.Response;
import common.structureClasses.Ticket;
import common.structureClasses.Venue;
import server.collectionManagement.CollectionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintFieldDescendingVenueCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    private boolean flag;
    public PrintFieldDescendingVenueCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        try {
            List<Venue> venues = new ArrayList<>();
            output = new StringBuilder();
            flag = true;
            if (getCollectionManager().getCollection().size() == 0) {
                output.append("Collection is empty, please add ticket");
            }
            for (Ticket ticket : getCollectionManager().getCollection()) {
                if (ticket.getVenue() != null) {
                    venues.add(ticket.getVenue());
                }
            }
            System.out.println("A");
            System.out.println(venues);
            if (venues.size() != 0) {
                Collections.sort(venues);
                Collections.reverse(venues);
            }
            System.out.println("B");
            for (Venue venue : venues) {
                output.append(venue);
                flag = false;
            }
            System.out.println("C");
            System.out.println(output.toString());
            if (flag) {
                output.append("No tickets have venues");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Response getCommandResponse() {
        System.out.println(output.toString());
        return new Response(output.toString());
    }
}
