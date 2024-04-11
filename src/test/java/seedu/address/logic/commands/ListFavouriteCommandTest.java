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
public class ListFavouriteCommandTest {

    @Test
    public void execute_listFavouriteWithFavourites_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ListFavouriteCommand listFavouriteCommand = new ListFavouriteCommand();

        String expectedMessage = ListFavouriteCommand.MESSAGE_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        model.setPerson(ALICE, new PersonBuilder(ALICE).withFavourite(true).build());

        expectedModel.updateFilteredPersonList(person -> person.isSamePerson(ALICE)); // only Alice is favourited
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_FAVOURITES);
        assertCommandSuccess(listFavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ListFavouriteCommand listFavouriteCommand = new ListFavouriteCommand();

        // same object -> returns true
        assert (listFavouriteCommand.equals(listFavouriteCommand));

        // same values -> returns true
        ListFavouriteCommand listFavouriteCommandCopy = new ListFavouriteCommand();
        assert (listFavouriteCommand.equals(listFavouriteCommandCopy));

        // different types -> returns false
        assertFalse(listFavouriteCommand.equals(1));

        // null -> returns false
        assertFalse(listFavouriteCommand.equals(null));
    }
}
