package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SearchPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_COMPANY);

        // Ensure no preamble exists.
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Check for non-empty Keywords after all Prefixes
        argMultimap.verifyNonEmptyKeywordValues(PREFIX_NAME, PREFIX_TAG, PREFIX_COMPANY);

        // Ensure all keywords are alphanumeric ONLY for tag and alphanumeric + hyphen + apostrophe for name and company
        argMultimap.verifyAllValuesAlphanumeric(PREFIX_TAG);
        argMultimap.verifyValuesNameCompany(PREFIX_NAME, PREFIX_COMPANY);

        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> companyKeywords = argMultimap.getAllValues(PREFIX_COMPANY);
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);

        // Ensure that at least one name or tag keyword is provided
        if (nameKeywords.isEmpty() && tagKeywords.isEmpty() && companyKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new SearchPredicate(nameKeywords, tagKeywords, companyKeywords));
    }

}

