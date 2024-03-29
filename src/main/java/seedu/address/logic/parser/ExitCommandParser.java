package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.parseNoArgs;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExitCommand object
 */
public class ExitCommandParser implements Parser<ExitCommand> {

    /**
     * Parses any potential {@code String} of text in the context of the ExitCommand that should not be there,
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input has any text after the command word
     */
    public ExitCommand parse(String args) throws ParseException {
        parseNoArgs(args);
        return new ExitCommand();
    }

}
