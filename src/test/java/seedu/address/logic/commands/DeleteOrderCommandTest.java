package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Date;
import seedu.address.model.order.Order;
import seedu.address.model.order.Remark;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteOrderCommandTest {
    private static final Date DATE_STUB = new Date("2020-01-01");
    private static final Remark REMARK_STUB = new Remark("100 chicken wings");
    private static final Order ORDER_STUB = new Order(DATE_STUB, REMARK_STUB);
    private static final ArrayList<Order> ORDERS_STUB = new ArrayList<>(List.of(ORDER_STUB));
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDeleteOrderFrom = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToDeleteOrderFrom).withOrders(ORDERS_STUB).build();
        model.setPerson(personToDeleteOrderFrom, editedPerson);

        Index orderIndexToDelete = Index.fromZeroBased(0);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST_PERSON, orderIndexToDelete);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                ORDER_STUB, Messages.format(editedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personAfterDeletion = new PersonBuilder(editedPerson).withoutOrder(
                orderIndexToDelete.getZeroBased()).build();
        expectedModel.setPerson(personToDeleteOrderFrom, personAfterDeletion);

        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex, Index.fromOneBased(1));

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidOrderIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getOrders().size() + 1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deletionWithNoOrders_throwsCommandException() {
        Person personWithNoOrders = new PersonBuilder().withOrders(new ArrayList<>()).build();
        model.addPerson(personWithNoOrders);
        Index personIndex = Index.fromZeroBased(model.getFilteredPersonList().size() - 1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(personIndex, Index.fromZeroBased(0));

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        DeleteOrderCommand deleteFirstOrderFromFirstPerson = new DeleteOrderCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_ORDER);
        DeleteOrderCommand deleteFirstOrderFromSecondPerson = new DeleteOrderCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_ORDER);
        DeleteOrderCommand deleteSecondOrderFromFirstPerson = new DeleteOrderCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_ORDER);

        assertEquals(deleteFirstOrderFromFirstPerson, deleteFirstOrderFromFirstPerson);

        DeleteOrderCommand deleteFirstOrderFromFirstPersonCopy = new DeleteOrderCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_ORDER);
        assertEquals(deleteFirstOrderFromFirstPerson, deleteFirstOrderFromFirstPersonCopy);

        assertFalse(deleteFirstOrderFromFirstPerson.equals(1));

        assertFalse(deleteFirstOrderFromFirstPerson.equals(null));

        assertFalse(deleteFirstOrderFromFirstPerson.equals(deleteFirstOrderFromSecondPerson));

        assertFalse(deleteFirstOrderFromFirstPerson.equals(deleteSecondOrderFromFirstPerson));
    }

}
