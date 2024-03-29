package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Lists all orders of the person identified using it's displayed index from the address book.
 */
public class ListOrderCommand extends Command {

    public static final String COMMAND_WORD = "listorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all orders of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Creates a ListOrderCommand to list the orders of the person at the specified {@code Index}.
     *
     * @param targetIndex Index of the person in the filtered person list.
     */
    public ListOrderCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personTargeted = lastShownList.get(targetIndex.getZeroBased());
        List<Order> sortedOrders = model.getSortedOrders(personTargeted);

        return new CommandResult(createOrderListString(sortedOrders));
    }

    /**
     * Creates a string that lists all orders.
     *
     * @param orders List of orders to be listed.
     * @return A string representation of the order list.
     */
    private String createOrderListString(List<Order> orders) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order(s) for the selected person:\n");

        if (orders.isEmpty()) {
            sb.append("No orders found.");
            return sb.toString();
        }

        // Sort orders by date
        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedOrders.size(); i++) {
            Order order = sortedOrders.get(i);
            int displayIndex = i + 1;
            String orderDetails = String.format("%s: %s", order.getDate(), order.getRemark());

            sb.append(displayIndex).append(". ")
                    .append(orderDetails)
                    .append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListOrderCommand)) {
            return false;
        }

        ListOrderCommand otherListOrderCommand = (ListOrderCommand) other;
        return targetIndex.equals(otherListOrderCommand.targetIndex);
    }
}
