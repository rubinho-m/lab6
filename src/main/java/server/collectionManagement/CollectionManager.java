/**
 * CollectionManager is responsible for managing the Ticket collection.
 */

package server.collectionManagement;

import common.structureClasses.Ticket;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

@XmlRootElement(name = "Ledger")
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionManager {
    @XmlElementWrapper(name = "Tickets")
    @XmlElement(name = "Ticket")
    private Set<Ticket> tickets = new LinkedHashSet<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        CollectionManager.path = path;
    }

    private static String path;

    public void setCollection(Set<Ticket> data) {
        for (Ticket ticket : data) {
            if (ticket.getName().isBlank() | ticket.getName() == null) {
                System.out.println("In your xml file empty name, please fix it");
                System.exit(1);
            }
            if (ticket.getCoordinates() == null) {
                System.out.println("In your xml file no coords, please fix it");
                System.exit(1);
            }
            if (ticket.getComment() == null | ticket.getComment().isBlank()) {
                System.out.println("In your xml file comment is empty, please fix it");
                System.exit(1);
            }
            if (ticket.getVenue() != null) {
                if (ticket.getVenue().getName().isBlank() | ticket.getVenue().getName() == null) {
                    System.out.println("Venue name is empty, please fix it");
                    System.exit(1);
                }
                if (ticket.getVenue().getType() == null) {
                    System.out.println("Venue type is null, please fix it");
                    System.exit(1);
                }
                if (ticket.getVenue().getAddress() != null) {
                    if (ticket.getVenue().getAddress().getStreet().isBlank() | ticket.getVenue().getAddress().getStreet() == null) {
                        System.out.println("Street in your address is empty, please fix it");
                        System.exit(1);
                    }
                }
            }
        }
        tickets = data;
    }

    public Set<Ticket> getCollection() {
        return tickets;
    }

    public void addToCollection(Ticket ticket) {
        ticket.setId(Ticket.getLastId() + 1);
        if (ticket.getVenue() != null) {
            ticket.getVenue().setId((int) ticket.getId());
        }
        Ticket.increaseId();
        ticket.setCreationDate(LocalDate.now());
        tickets.add(ticket);
    }

    public Ticket getFirstElement() {
        return (Ticket) tickets.toArray()[0];
    }

    public StringBuilder printCollection() {
        StringBuilder output = new StringBuilder();
        if (tickets.size() == 0) {
            output.append("Collection is empty" + "\n");
        } else {
            output.append("Collection:" + "\n");
        }
        tickets.stream()
                .sorted(Comparator.comparing(ticket -> ticket.getCoordinates()))
                .map(ticket -> ticket + "\n" + "\n")
                .forEach(output::append);

        return output;
    }


}
