package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ShowFavouriteCommand.
 */
public class ShowFavouriteCommandTest {

    @Test
    public void execute_showFavouriteWithFavourites_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ShowFavouriteCommand showFavouriteCommand = new ShowFavouriteCommand();

        String expectedMessage = ShowFavouriteCommand.MESSAGE_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        model.setPerson(ALICE, new PersonBuilder(ALICE).withFavourite(true).build());

        expectedModel.updateFilteredPersonList(person -> person.isSamePerson(ALICE)); // only Alice is favourited
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_FAVOURITES);
        assertCommandSuccess(showFavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ShowFavouriteCommand showFavouriteCommand = new ShowFavouriteCommand();

        // same object -> returns true
        assert (showFavouriteCommand.equals(showFavouriteCommand));

        // same values -> returns true
        ShowFavouriteCommand showFavouriteCommandCopy = new ShowFavouriteCommand();
        assert (showFavouriteCommand.equals(showFavouriteCommandCopy));

        // different types -> returns false
        assertFalse(showFavouriteCommand.equals(1));

        // null -> returns false
        assertFalse(showFavouriteCommand.equals(null));
    }
}
