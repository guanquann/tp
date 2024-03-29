package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteOrderCommand;


public class DeleteOrderCommandParserTest {

    private DeleteOrderCommandParser parser = new DeleteOrderCommandParser();
    private final Index personIndex = Index.fromOneBased(1);
    private final Index orderIndex = Index.fromOneBased(1);

    @Test
    public void parse_validArgs_returnsDeleteOrderCommand() {
        assertParseSuccess(parser, "1 o/1", new DeleteOrderCommand(personIndex, orderIndex));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid format
        assertParseFailure(parser, "a o/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));

        // Missing person index
        assertParseFailure(parser, "o/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));

        // Missing order index
        assertParseFailure(parser, "1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));

        // Invalid person index
        assertParseFailure(parser, "-1 o/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));

        // Invalid order index
        assertParseFailure(parser, "1 o/x",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));

        // Prefix before person index
        assertParseFailure(parser, "potato 1 o/2",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));

        // Completely missing input
        assertParseFailure(parser, "",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));

        // Missing both person and order index but with the order index prefix present
        assertParseFailure(parser, "o/",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE));
    }
}
