package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Removes the indicated contacts in the address book from favourites
 */
public class RemoveFavouriteCommand extends Command {
    public static final String COMMAND_WORD = "removefav";

    public static final String MESSAGE_SUCCESS = "The following contacts have been removed from favourites: %s";

    public static final String MESSAGE_SUCCESS_WITH_WARNING = "The following contacts have been removed from"
            + " favourites: %s" + "\nWarning: There are contacts in your input that were already not favourites.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes contacts identified by index number "
            + "as favourites.\n"
            + "Parameters: i/ [INDICES] (must be positive integers separated by "
            + "commas that correspond to existing favourite contacts)\n"
            + "Example: " + COMMAND_WORD + " "
            + "i/ 1,2,5";

    private final Set<Index> indices;

    /**
     * Creates an AddFavouriteCommand to mark the specified group of {@code Person} as favourites
     */
    public RemoveFavouriteCommand(Set<Index> indices) {
        requireNonNull(indices);
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> people = model.getFilteredPersonList();
        List<String> modifiedContacts = new ArrayList<>();

        boolean anyGreaterThanSize = this.indices.stream().anyMatch(index -> index.getZeroBased() >= people.size());
        if (anyGreaterThanSize) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        boolean anyNotFavourite = this.indices.stream().anyMatch(index ->
                !people.get(index.getZeroBased()).getIsFavourite());

        for (Index index : this.indices) {
            Person person = people.get(index.getZeroBased());
            modifiedContacts.add(person.getName().fullName);
            person.removeFavourite();
            model.setPerson(person, person);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (anyNotFavourite) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_WARNING, modifiedContacts));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, modifiedContacts));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveFavouriteCommand)) {
            return false;
        }

        RemoveFavouriteCommand otherRemoveFavouriteCommand = (RemoveFavouriteCommand) other;
        return this.indices.equals(otherRemoveFavouriteCommand.indices);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.indices.forEach(sb::append);
        return new ToStringBuilder(this)
                .add("indices", sb.toString())
                .toString();
    }
}
