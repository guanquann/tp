package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Deletes an order from a person in the address book.
 */
public class DeleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "deleteorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index number from the specified person.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) o/ORDER_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 o/2";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s from Person: %2$s";

    private final Index personIndex;
    private final Index orderIndex;

    /**
     * Creates a DeleteOrderCommand to delete the order at the specified {@code Index}
     * from the specified {@code Person}.
     *
     * @param personIndex Index of the person in the filtered person list.
     * @param orderIndex Index of the order in the sorted order list.
     */
    public DeleteOrderCommand(Index personIndex, Index orderIndex) {
        this.personIndex = personIndex;
        this.orderIndex = orderIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteOrderFrom = lastShownList.get(personIndex.getZeroBased());
        String oP = Messages.format(personToDeleteOrderFrom);

        List<Order> sortedOrders = model.getSortedOrders(personToDeleteOrderFrom);

        if (orderIndex.getZeroBased() >= sortedOrders.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToDelete = sortedOrders.get(orderIndex.getZeroBased());
        model.deleteOrder(personToDeleteOrderFrom, orderToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete, oP));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteOrderCommand // instanceof handles nulls
                && personIndex.equals(((DeleteOrderCommand) other).personIndex)
                && orderIndex.equals(((DeleteOrderCommand) other).orderIndex)); // state check
    }
}
