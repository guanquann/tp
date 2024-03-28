package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.person.Person;

/**
 * Edit the status of an order from a person in the address book.
 */
public class EditStatusCommand extends Command {

    public static final String COMMAND_WORD = "editstatus";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the order identified by the index number from the specified person.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) o/ORDER_INDEX (must be a positive integer) "
            + "s/STATUS (must be one of 'pending', 'arrived', or 'late')\n"
            + "Example: " + COMMAND_WORD + " 1 o/2 s/Arrived";

    public static final String MESSAGE_EDIT_STATUS_SUCCESS = "Edited Status: %1$s";

    private final Index personIndex;
    private final Index orderIndex;
    private final Status status;

    /**
     * Creates an EditStatusCommand to edit the status of the order at the specified {@code Index}
     * from the specified {@code Person}.
     *
     * @param personIndex Index of the person in the filtered person list.
     * @param orderIndex Index of the order in the sorted order list.
     * @param status Status to be edited to.
     */
    public EditStatusCommand(Index personIndex, Index orderIndex, Status status) {
        this.personIndex = personIndex;
        this.orderIndex = orderIndex;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEditOrderFrom = lastShownList.get(personIndex.getZeroBased());
        String oP = Messages.format(personToEditOrderFrom);

        List<Order> sortedOrders = personToEditOrderFrom.getOrders();

        if (orderIndex.getZeroBased() >= sortedOrders.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        // TODO
        Order orderToEdit = sortedOrders.get(orderIndex.getZeroBased());

        Order newOrder = new Order(orderToEdit.getDate(), orderToEdit.getRemark(), status);

        personToEditOrderFrom.editOrder(orderToEdit, newOrder);

        model.setPerson(personToEditOrderFrom, personToEditOrderFrom);

        // TODO
        return new CommandResult(String.format(MESSAGE_EDIT_STATUS_SUCCESS, Messages.format(personToEditOrderFrom)));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditStatusCommand // instanceof handles nulls
                && personIndex.equals(((EditStatusCommand) other).personIndex)
                && orderIndex.equals(((EditStatusCommand) other).orderIndex)); // state check
    }
}
