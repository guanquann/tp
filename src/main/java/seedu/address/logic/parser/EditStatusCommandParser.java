package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Status;

/**
 * Parses input arguments and creates a new EditStatusCommand object
 */
public class EditStatusCommandParser implements Parser<EditStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditStatusCommand
     * and returns an EditStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditStatusCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER_INDEX, PREFIX_STATUS);

        if (!argMultimap.arePrefixesPresent(PREFIX_STATUS)
                || !argMultimap.arePrefixesPresent(PREFIX_ORDER_INDEX)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStatusCommand.MESSAGE_USAGE));
        }

        try {
            Index personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            Index orderIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ORDER_INDEX).get());
            Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
            return new EditStatusCommand(personIndex, orderIndex, status);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditStatusCommand.MESSAGE_USAGE), pe);
        }
    }
}
