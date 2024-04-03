package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_CANNOT_BE_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.SearchPredicate;

public class FindCommandParserTest {
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Single name keyword
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice",
                new FindCommand(new SearchPredicate(Collections.singletonList("Alice"),
                        Collections.emptyList(), Collections.emptyList())));

        // Multiple name keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_NAME + "Bob",
                new FindCommand(new SearchPredicate(Arrays.asList("Alice", "Bob"),
                        Collections.emptyList(), Collections.emptyList())));

        // Single tag keyword
        assertParseSuccess(parser, " " + PREFIX_TAG + "friend",
                new FindCommand(new SearchPredicate(Collections.emptyList(),
                        Collections.singletonList("friend"), Collections.emptyList())));

        // Multiple tag keywords
        assertParseSuccess(parser, " " + PREFIX_TAG + "friend " + PREFIX_TAG + "colleague",
                new FindCommand(new SearchPredicate(Collections.emptyList(),
                        Arrays.asList("friend", "colleague"), Collections.emptyList())));

        // Combined name and tag keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_TAG + "friend",
                new FindCommand(new SearchPredicate(Collections.singletonList("Alice"),
                        Collections.singletonList("friend"), Collections.emptyList())));
    }

    @Test
    public void parse_alphanumericNameKeyword_success() {
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice1",
                new FindCommand(new SearchPredicate(Collections.singletonList("Alice1"),
                        Collections.emptyList(), Collections.emptyList())));
    }

    @Test
    public void parse_alphanumericTagKeyword_success() {
        assertParseSuccess(parser, " " + PREFIX_TAG + "friend1",
                new FindCommand(new SearchPredicate(Collections.emptyList(),
                        Collections.singletonList("friend1"), Collections.emptyList())));
    }

    @Test
    public void parse_emptyNameKeyword_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_NAME + " ",
                String.format(MESSAGE_CANNOT_BE_EMPTY, PREFIX_NAME));
    }

    @Test
    public void parse_emptyTagKeyword_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_TAG + " ",
                String.format(MESSAGE_CANNOT_BE_EMPTY, PREFIX_TAG));
    }

    @Test
    public void parse_singleCompanyKeyword_success() {
        assertParseSuccess(parser, " " + PREFIX_COMPANY + "Acme",
                new FindCommand(new SearchPredicate(Collections.emptyList(), Collections.emptyList(),
                        Collections.singletonList("Acme"))));
    }

    @Test
    public void parse_multipleCompanyKeywords_success() {
        assertParseSuccess(parser, " " + PREFIX_COMPANY + "Acme " + PREFIX_COMPANY + "Widgets",
                new FindCommand(new SearchPredicate(Collections.emptyList(), Collections.emptyList(),
                        Arrays.asList("Acme", "Widgets"))));
    }

    @Test
    public void parse_combinedNameTagCompanyKeywords_success() {
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_TAG + "friend " + PREFIX_COMPANY + "Acme",
                new FindCommand(new SearchPredicate(Collections.singletonList("Alice"),
                        Collections.singletonList("friend"), Collections.singletonList("Acme"))));
    }

    @Test
    public void parse_emptyCompanyKeyword_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_COMPANY + " ",
                String.format(MESSAGE_CANNOT_BE_EMPTY, PREFIX_COMPANY));
    }

    @Test
    public void parse_noFieldsProvided_throwsParseException() {
        // No name or tag provided
        assertParseFailure(parser, "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        // Preamble before valid prefixes
        assertParseFailure(parser, "RandomText " + PREFIX_NAME + "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
