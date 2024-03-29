package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NO_ARGUMENTS_COMMAND;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddFavouriteCommand;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOrderCommand;
import seedu.address.logic.commands.RemoveFavouriteCommand;
import seedu.address.logic.commands.ShowFavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Date;
import seedu.address.model.order.Order;
import seedu.address.model.order.Remark;
import seedu.address.model.person.NameAndTagContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " ") instanceof ClearCommand);
        assertThrows(ParseException.class, MESSAGE_INVALID_NO_ARGUMENTS_COMMAND, () -> parser.parseCommand(
                ClearCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addOrder() throws Exception {
        Order order = new Order(new Date("2020-01-01"), new Remark("100 chicken wings"));
        AddOrderCommand command = (AddOrderCommand) parser.parseCommand(AddOrderCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " d/2020-01-01 r/100 chicken wings");
        assertEquals(new AddOrderCommand(INDEX_FIRST_PERSON, order), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " ") instanceof ExitCommand);
        assertThrows(ParseException.class, MESSAGE_INVALID_NO_ARGUMENTS_COMMAND, () -> parser.parseCommand(
                ExitCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeywords = Arrays.asList("foo", "bar");
        List<String> tagKeywords = Arrays.asList("friend", "colleague");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/ " + String.join(" n/ ", nameKeywords)
                        + " t/ " + String.join(" t/ ", tagKeywords));
        assertEquals(new FindCommand(new NameAndTagContainsKeywordsPredicate(nameKeywords, tagKeywords)), command);
    }

    @Test
    public void parseCommand_addFavourite() throws Exception {
        Set<Index> indices = Set.of(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(4));
        AddFavouriteCommand command = (AddFavouriteCommand) parser.parseCommand(
                AddFavouriteCommand.COMMAND_WORD + " "
                + "i/ 1,2,4"
        );
        assertEquals(new AddFavouriteCommand(indices), command);
    }

    @Test
    public void parseCommand_removeFavourite() throws Exception {
        Set<Index> indices = Set.of(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(4));
        RemoveFavouriteCommand command = (RemoveFavouriteCommand) parser.parseCommand(
                RemoveFavouriteCommand.COMMAND_WORD + " "
                        + "i/ 1,2,4"
        );
        assertEquals(new RemoveFavouriteCommand(indices), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " ") instanceof HelpCommand);
        assertThrows(ParseException.class, MESSAGE_INVALID_NO_ARGUMENTS_COMMAND, () -> parser.parseCommand(
                ListCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " ") instanceof ListCommand);
        assertThrows(ParseException.class, MESSAGE_INVALID_NO_ARGUMENTS_COMMAND, () -> parser.parseCommand(
                ListCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_listOrder() throws Exception {
        final Index targetIndex = Index.fromOneBased(1);
        ListOrderCommand command = (ListOrderCommand) parser.parseCommand(ListOrderCommand.COMMAND_WORD
                + " " + targetIndex.getOneBased());
        assertEquals(new ListOrderCommand(targetIndex), command);
    }

    @Test
    public void parseCommand_showFavourite() throws Exception {
        assertTrue(parser.parseCommand(ShowFavouriteCommand.COMMAND_WORD) instanceof ShowFavouriteCommand);
        assertTrue(parser.parseCommand(ShowFavouriteCommand.COMMAND_WORD + " ") instanceof ShowFavouriteCommand);
        assertThrows(ParseException.class, MESSAGE_INVALID_NO_ARGUMENTS_COMMAND, () -> parser.parseCommand(
                ShowFavouriteCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_deleteOrder() throws Exception {
        final Index personIndex = INDEX_FIRST_PERSON;
        final Index orderIndex = Index.fromOneBased(1);
        DeleteOrderCommand command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_WORD + " " + personIndex.getOneBased() + " o/" + orderIndex.getOneBased());
        assertEquals(new DeleteOrderCommand(personIndex, orderIndex), command);
    }


    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
