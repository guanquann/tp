package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_FAVOURITES;

import seedu.address.model.Model;

/**
 * Lists all favourites in the address book to the user.
 */
public class ListFavouriteCommand extends Command {

    public static final String COMMAND_WORD = "listfav";

    public static final String MESSAGE_SUCCESS = "Displayed all favourites";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_FAVOURITES);
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof ListFavouriteCommand;
    }
}
