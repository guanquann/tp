package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.SearchPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> nameKeywordsList = Collections.singletonList("first");
        List<String> tagKeywordsList = Collections.singletonList("second");
        SearchPredicate firstPredicate = new SearchPredicate(nameKeywordsList, tagKeywordsList,
                Collections.emptyList());
        SearchPredicate secondPredicate = new SearchPredicate(Collections.emptyList(),
                Collections.emptyList(), Collections.singletonList("company"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // standard equality tests
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findFirstCommand.equals(new FindCommand(firstPredicate)));
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findFirstCommand.equals(null));
        assertFalse(findFirstCommand.equals(1));
    }

    @Test
    public void execute_tagMatches_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        SearchPredicate predicate = preparePredicate("", "friends", "");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameTagCompanyMatches_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        SearchPredicate predicate = preparePredicate("Alice", "friends", "");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_noMatches_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SearchPredicate predicate = preparePredicate("Nonexistent", "NonexistentTag",
                "NonexistentCompany");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        SearchPredicate predicate = preparePredicate("Alice", "friend", "");
        FindCommand findCommand = new FindCommand(predicate);
        String expectedString =
                "seedu.address.logic.commands.FindCommand{predicate=SearchPredicate{nameKeywords=[Alice], "
                        + "tagKeywords=[friend], companyKeywords=[]}}";
        assertEquals(expectedString, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code SearchPredicate}.
     */
    private SearchPredicate preparePredicate(String nameInput, String tagInput, String companyInput) {
        List<String> nameKeywords = nameInput.isEmpty() ? Collections.emptyList() : Arrays.asList(
                nameInput.trim().split("\\s+"));
        List<String> tagKeywords = tagInput.isEmpty() ? Collections.emptyList() : Arrays.asList(
                tagInput.trim().split("\\s+"));
        List<String> companyKeywords = companyInput.isEmpty() ? Collections.emptyList() : Arrays.asList(
                companyInput.trim().split("\\s+"));
        return new SearchPredicate(nameKeywords, tagKeywords, companyKeywords);
    }
}
