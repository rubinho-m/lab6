/**
 * The ExitCommand class implements the execute method of the Command interface
 * to exit the program.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package common.commands;


import common.networkStructures.Response;

public class ExitCommand extends CommandTemplate implements CommandWithResponse {
    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public Response getCommandResponse() {
        return null;
    }
}
