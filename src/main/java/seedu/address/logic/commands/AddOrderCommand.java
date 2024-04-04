package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Adds an order to a person identified by the index from the address book.
 */
public class AddOrderCommand extends Command {
    public static final String COMMAND_WORD = "addorder";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_ORDER = "Order already exists for this person";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an order to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "d/ [DATE] r/ [REMARK] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "d/ 2024-01-01 r/ 100 chicken wings";

    private final Index index;
    private final Order order;

    /**
     * Creates an AddOrderCommand to add the specified {@code Person}
     */
    public AddOrderCommand(Index index, Order order) {
        requireNonNull(index);
        requireNonNull(order);

        this.index = index;
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(index.getZeroBased());

        if (model.hasOrder(person, order)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.addOrder(person, order);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(person)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOrderCommand)) {
            return false;
        }

        AddOrderCommand otherAddOrderCommand = (AddOrderCommand) other;
        return order.equals(otherAddOrderCommand.order) && index.equals(otherAddOrderCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("order", order)
                .toString();
    }
}
