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


public class AddFavouriteCommandTest {
    private static final Set<Index> INDICES_STUB = Set.of(Index.fromOneBased(1),
            Index.fromOneBased(2), Index.fromOneBased(4));
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddFavouriteCommand.MESSAGE_USAGE);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndices_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFavouriteCommand(null));
    }

    @Test
    public void execute_addFavourite_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).build();
        Set<Index> indices = Set.of(Index.fromOneBased(1));
        List<String> modifiedContacts = List.of(firstPerson.getName().fullName);
        AddFavouriteCommand addFavouriteCommand = new AddFavouriteCommand(indices);
        String expectedMessage = String.format(AddFavouriteCommand.MESSAGE_SUCCESS, modifiedContacts);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addFavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Set<Index> indices = Set.of(Index.fromOneBased(model.getFilteredPersonList().size() + 1));
        AddFavouriteCommand addFavouriteCommand = new AddFavouriteCommand(indices);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                addFavouriteCommand.execute(model));
    }

    @Test
    public void execute_alreadyFavourite_successWithWarning() {
        Set<Index> indices = Set.of(Index.fromOneBased(1));
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withFavourite(true).build();
        model.setPerson(firstPerson, editedPerson);
        AddFavouriteCommand addFavouriteCommand = new AddFavouriteCommand(indices);

        List<String> modifiedContacts = List.of(firstPerson.getName().fullName);
        String expectedMessage = String.format(AddFavouriteCommand.MESSAGE_SUCCESS_WITH_WARNING, modifiedContacts);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addFavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        AddFavouriteCommand addFavouriteCommand = new AddFavouriteCommand(INDICES_STUB);

        // same object -> returns true
        assert (addFavouriteCommand.equals(addFavouriteCommand));

        // same values -> returns true
        AddFavouriteCommand addFavouriteCommandCopy = new AddFavouriteCommand(INDICES_STUB);
        assert (addFavouriteCommand.equals(addFavouriteCommandCopy));

        // different types -> returns false
        assert (!addFavouriteCommand.equals(1));

        // null -> returns false
        assert (!addFavouriteCommand.equals(null));

        // different indices -> returns false
        AddFavouriteCommand differentFavouriteCommand = new AddFavouriteCommand(Set.of(Index.fromOneBased(1)));
        assert (!addFavouriteCommand.equals(differentFavouriteCommand));
    }

    @Test
    public void toStringMethod() {
        StringBuilder sb = new StringBuilder();
        INDICES_STUB.forEach(sb::append);
        AddFavouriteCommand addFavouriteCommand = new AddFavouriteCommand(INDICES_STUB);
        String expected = AddFavouriteCommand.class.getCanonicalName() + "{indices=" + sb.toString() + "}";
        assertEquals(expected, addFavouriteCommand.toString());
    }
}
