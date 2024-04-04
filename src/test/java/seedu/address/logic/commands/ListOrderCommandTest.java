package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws CommandException {
        Index validIndex = Index.fromOneBased(1);
        ListOrderCommand listOrderCommand = new ListOrderCommand(validIndex);
        CommandResult commandResult = listOrderCommand.execute(model);

        String expectedMessageStart = "Order(s) for the selected person:\n";
        assertTrue(commandResult.getFeedbackToUser().startsWith(expectedMessageStart));
    }

    @Test
    public void execute_validIndexNoOrders_success() throws CommandException {
        ListOrderCommand listOrderCommand = new ListOrderCommand(Index.fromOneBased(2));
        CommandResult commandResult = listOrderCommand.execute(model);

        String expectedMessage = "Order(s) for the selected person:\nNo orders found";
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ListOrderCommand listOrderCommand = new ListOrderCommand(Index.fromOneBased(999));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                listOrderCommand.execute(model));
    }

    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);

        ListOrderCommand firstCommand = new ListOrderCommand(firstIndex);
        ListOrderCommand secondCommand = new ListOrderCommand(secondIndex);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        ListOrderCommand firstCommandCopy = new ListOrderCommand(firstIndex);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }



}


