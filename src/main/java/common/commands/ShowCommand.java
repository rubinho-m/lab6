/**
 * The ShowCommand class implements the execute method of the Command interface
 * to show the item in collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;

import server.collectionManagement.CollectionManager;
import common.exceptions.EmptyCollectionException;

public class ShowCommand extends CommandTemplate implements Command{
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws EmptyCollectionException {
        if (getCollectionManager().getCollection().size() == 0){
            throw new EmptyCollectionException();
        }
        getCollectionManager().printCollection();
    }
}
