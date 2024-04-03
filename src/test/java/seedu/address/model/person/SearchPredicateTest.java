package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SearchPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateNameKeywordList = Collections.singletonList("first");
        List<String> secondPredicateNameKeywordList = Arrays.asList("first", "second");
        List<String> firstPredicateTagKeywordList = Collections.singletonList("tag1");
        List<String> secondPredicateTagKeywordList = Arrays.asList("tag2", "tag3");
        List<String> companyKeywords = Collections.emptyList(); // Adding company keywords for equality check

        SearchPredicate firstPredicate =
                new SearchPredicate(firstPredicateNameKeywordList, firstPredicateTagKeywordList, companyKeywords);
        SearchPredicate secondPredicate =
                new SearchPredicate(secondPredicateNameKeywordList, secondPredicateTagKeywordList, companyKeywords);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SearchPredicate firstPredicateCopy =
                new SearchPredicate(firstPredicateNameKeywordList, firstPredicateTagKeywordList, companyKeywords);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameTagCompanyContainsKeywords_returnsTrue() {
        // Name, tag, and company match
        SearchPredicate predicate = new SearchPredicate(Arrays.asList("Alice"), Arrays.asList("friend"),
                Arrays.asList("Alice Corp"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friend").withCompany(
                "Alice Corp").build()));

        // Only name matches
        predicate = new SearchPredicate(Arrays.asList("Alice"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Only tag matches
        predicate = new SearchPredicate(Collections.emptyList(), Arrays.asList("friend"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").withTags("friend").build()));

        // Only company matches
        predicate = new SearchPredicate(Collections.emptyList(), Collections.emptyList(), Arrays.asList("Alice Corp"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Alice Corp").build()));
    }

    @Test
    public void test_nameTagCompanyDoesNotContainKeywords_returnsFalse() {
        // Neither name, tag, nor company match
        SearchPredicate predicate = new SearchPredicate(
                Arrays.asList("Nonexistent"), Arrays.asList("nonexistentTag"), Arrays.asList("Nonexistent Corp"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("friend").withCompany(
                "Alice Corp").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("nameKeyword");
        List<String> tagKeywords = List.of("tagKeyword");
        List<String> companyKeywords = List.of("companyKeyword"); // Adding company keywords for toString check
        SearchPredicate predicate = new SearchPredicate(nameKeywords, tagKeywords, companyKeywords);

        String expected = "SearchPredicate{nameKeywords=" + nameKeywords
                + ", tagKeywords=" + tagKeywords
                + ", companyKeywords=" + companyKeywords + '}';
        assertEquals(expected, predicate.toString());
    }
}
