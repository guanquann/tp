package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class RemoveFavouriteCommandTest {
    private static final Set<Index> INDICES_STUB = Set.of(Index.fromOneBased(1),
            Index.fromOneBased(2), Index.fromOneBased(4));
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemoveFavouriteCommand.MESSAGE_USAGE);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndices_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveFavouriteCommand(null));
    }

    @Test
    public void execute_removeFavourite_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withFavourite(true).build();
        model.setPerson(firstPerson, editedPerson);
        Set<Index> indices = Set.of(Index.fromOneBased(1));
        List<String> modifiedContacts = List.of(firstPerson.getName().fullName);
        RemoveFavouriteCommand removeFavouriteCommand = new RemoveFavouriteCommand(indices);
        String expectedMessage = String.format(RemoveFavouriteCommand.MESSAGE_SUCCESS, modifiedContacts);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(removeFavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Set<Index> indices = Set.of(Index.fromOneBased(model.getFilteredPersonList().size() + 1));
        RemoveFavouriteCommand removeFavouriteCommand = new RemoveFavouriteCommand(indices);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                removeFavouriteCommand.execute(model));
    }

    @Test
    public void execute_alreadyNonFavouriteContact_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withFavourite(false).build();
        model.setPerson(firstPerson, editedPerson);
        Set<Index> indices = Set.of(Index.fromOneBased(1));
        List<String> modifiedContacts = List.of(firstPerson.getName().fullName);
        RemoveFavouriteCommand removeFavouriteCommand = new RemoveFavouriteCommand(indices);
        String expectedMessage = String.format(RemoveFavouriteCommand.MESSAGE_SUCCESS_WITH_WARNING, modifiedContacts);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(removeFavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        RemoveFavouriteCommand removeFavouriteCommand = new RemoveFavouriteCommand(INDICES_STUB);

        // same object -> returns true
        assert (removeFavouriteCommand.equals(removeFavouriteCommand));

        // same values -> returns true
        RemoveFavouriteCommand removeFavouriteCommandCopy = new RemoveFavouriteCommand(INDICES_STUB);
        assert (removeFavouriteCommand.equals(removeFavouriteCommandCopy));

        // different types -> returns false
        assert (!removeFavouriteCommand.equals(1));

        // null -> returns false
        assert (!removeFavouriteCommand.equals(null));

        // different indices -> returns false
        RemoveFavouriteCommand differentFavouriteCommand = new RemoveFavouriteCommand(Set.of(Index.fromOneBased(1)));
        assert (!removeFavouriteCommand.equals(differentFavouriteCommand));
    }

    @Test
    public void toStringMethod() {
        StringBuilder sb = new StringBuilder();
        INDICES_STUB.forEach(sb::append);
        RemoveFavouriteCommand removeFavouriteCommand = new RemoveFavouriteCommand(INDICES_STUB);
        String expected = RemoveFavouriteCommand.class.getCanonicalName() + "{indices=" + sb.toString() + "}";
        assertEquals(expected, removeFavouriteCommand.toString());
    }
}
