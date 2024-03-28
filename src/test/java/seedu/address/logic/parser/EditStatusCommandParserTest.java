package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditStatusCommand;
import seedu.address.model.order.Status;

public class EditStatusCommandParserTest {

    private EditStatusCommandParser parser = new EditStatusCommandParser();
    private final Index personIndex = Index.fromOneBased(1);
    private final Index orderIndex = Index.fromOneBased(1);
    private final Status status = new Status("pending");

    @Test
    public void parse_validArgs_returnsEditStatusCommand() {
        assertParseSuccess(parser, "1 o/1 s/pending", new EditStatusCommand(personIndex, orderIndex, status));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid format
        assertParseFailure(parser, "a o/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));

        // Missing person index
        assertParseFailure(parser, "o/1 s/pending",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));

        // Missing order index
        assertParseFailure(parser, "1 s/pending",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));

        // Missing status
        assertParseFailure(parser, "1 o/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));

        // Invalid person index
        assertParseFailure(parser, "-1 o/1 s/pending",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));

        // Invalid order index
        assertParseFailure(parser, "1 o/x s/pending",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));

        // Invalid status
        assertParseFailure(parser, "1 o/1 s/pendinggg",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));

        // Prefix before person index
        assertParseFailure(parser, "potato 1 o/2",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE));
    }
}
