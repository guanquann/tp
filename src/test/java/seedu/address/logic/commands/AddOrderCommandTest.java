package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Date;
import seedu.address.model.order.Order;
import seedu.address.model.order.Remark;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddOrderCommandTest {
    private static final Date DATE_STUB = new Date("2020-01-01");
    private static final Remark REMARK_STUB = new Remark("100 chicken wings");
    private static final Order ORDER_STUB = new Order(DATE_STUB, REMARK_STUB);
    private static final ArrayList<Order> ORDERS_STUB = new ArrayList<>(List.of(ORDER_STUB));

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddOrderCommand(null, ORDER_STUB));
        assertThrows(NullPointerException.class, () -> new AddOrderCommand(Index.fromOneBased(1),
                null));
        assertThrows(NullPointerException.class, () -> new AddOrderCommand(null, null));
    }

    @Test
    public void execute_addOrder_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withOrders(ORDERS_STUB).build();
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ORDER_STUB);

        String expectedMessage = String.format(AddOrderCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateOrder_throwsCommandException() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withOrders(ORDERS_STUB).build();
        model.setPerson(firstPerson, editedPerson);

        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ORDER_STUB);
        assertThrows(CommandException.class, AddOrderCommand.MESSAGE_DUPLICATE_ORDER, () ->
                addOrderCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddOrderCommand addOrderCommand = new AddOrderCommand(outOfBoundIndex, ORDER_STUB);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                addOrderCommand.execute(model));
    }

    @Test
    public void equals() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ORDER_STUB);

        // same object -> returns true
        assert (addOrderCommand.equals(addOrderCommand));

        // same values -> returns true
        AddOrderCommand addOrderCommandCopy = new AddOrderCommand(INDEX_FIRST_PERSON, ORDER_STUB);
        assert (addOrderCommand.equals(addOrderCommandCopy));

        // different types -> returns false
        assertFalse(addOrderCommand.equals(1));

        // null -> returns false
        assertFalse(addOrderCommand.equals(null));

        // different index -> returns false
        AddOrderCommand differentOrderCommand = new AddOrderCommand(Index.fromOneBased(2), ORDER_STUB);
        assertFalse(addOrderCommand.equals(differentOrderCommand));

        // different date -> returns false
        Order orderWithDifferentDate = new Order(new Date("2020-01-02"), REMARK_STUB);
        differentOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, orderWithDifferentDate);
        assertFalse(addOrderCommand.equals(differentOrderCommand));

        // different remark -> returns false
        Order orderWithDifferentRemark = new Order(DATE_STUB, new Remark("200 chicken wings"));
        differentOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, orderWithDifferentRemark);
        assertFalse(addOrderCommand.equals(differentOrderCommand));
    }

    @Test
    public void toStringMethod() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ORDER_STUB);
        String expected = AddOrderCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON
                + ", order=" + ORDER_STUB + "}";
        assertEquals(expected, addOrderCommand.toString());
    }

}
