package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDICES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveFavouriteCommand;

public class RemoveFavouriteCommandParserTest {
    private static final String NON_EMPTY_INDICES = "1,2,4";
    private static final Set<Index> NON_EMPTY_INDICES_SET = Set.of(Index.fromOneBased(1),
            Index.fromOneBased(2), Index.fromOneBased(4));
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemoveFavouriteCommand.MESSAGE_USAGE);
    private RemoveFavouriteCommandParser parser = new RemoveFavouriteCommandParser();

    @Test
    public void parse_indicesSpecified_success() {
        String userInput = " " + PREFIX_INDICES + " " + NON_EMPTY_INDICES;

        RemoveFavouriteCommand expectedCommand = new RemoveFavouriteCommand(NON_EMPTY_INDICES_SET);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndices_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidStructure_failure() {
        // negative indices
        assertParseFailure(parser, " " + PREFIX_INDICES + "-1", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, " " + PREFIX_INDICES + "0", MESSAGE_INVALID_FORMAT);

        // non-numerical indices
        assertParseFailure(parser, " " + PREFIX_INDICES + "abcd", MESSAGE_INVALID_FORMAT);

        // invalid prefix
        assertParseFailure(parser, " " + "d/ abcd", MESSAGE_INVALID_FORMAT);

        // inclusion of preamble
        assertParseFailure(parser, "1 " + PREFIX_INDICES + "1", MESSAGE_INVALID_FORMAT);
    }
}
