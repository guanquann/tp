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
        List<String> firstCompanyKeywords = Collections.singletonList("company1");
        List<String> secondCompanyKeywords = Arrays.asList("company2", "company3");

        SearchPredicate firstPredicate =
                new SearchPredicate(firstPredicateNameKeywordList, firstPredicateTagKeywordList, firstCompanyKeywords);
        SearchPredicate secondPredicate =
                new SearchPredicate(secondPredicateNameKeywordList, secondPredicateTagKeywordList, secondCompanyKeywords);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SearchPredicate firstPredicateCopy = new SearchPredicate(firstPredicateNameKeywordList, firstPredicateTagKeywordList, firstCompanyKeywords);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different company keywords -> returns false
        SearchPredicate differentCompanyPredicate = new SearchPredicate(firstPredicateNameKeywordList, firstPredicateTagKeywordList, secondCompanyKeywords);
        assertFalse(firstPredicate.equals(differentCompanyPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_combinationOfNameTagCompanyMatches_returnsTrue() {
        // Combination of name, tag, and company matches
        SearchPredicate predicate = new SearchPredicate(Arrays.asList("Alice"), Arrays.asList("friend"), Arrays.asList("Corp"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friend").withCompany("Alice Corp").build()));
    }

    @Test
    public void test_nameAndCompanyMatch_tagDoesNotExist_returnsFalse() {
        // Name and company match, but tag does not match (logical AND condition)
        SearchPredicate predicate = new SearchPredicate(Arrays.asList("Alice"), Arrays.asList("nonexistentTag"), Arrays.asList("Corp"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withCompany("Alice Corp").build()));
    }

    @Test
    public void test_tagAndCompanyMatch_nameDoesNotMatch_returnsFalse() {
        // Tag and company match, but name does not match (logical AND condition)
        SearchPredicate predicate = new SearchPredicate(Arrays.asList("Nonexistent"), Arrays.asList("friend"), Arrays.asList("Corp"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").withCompany("Alice Corp").build()));
    }

    @Test
    public void test_onlyOneFieldMatches_returnsFalse() {
        // Only one of the fields matches, others do not (logical AND condition)
        // Only name matches
        SearchPredicate predicate = new SearchPredicate(Arrays.asList("Alice"), Arrays.asList("nonexistentTag"), Arrays.asList("Nonexistent Corp"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("friend").withCompany("Alice Corp").build()));

        // Only tag matches
        predicate = new SearchPredicate(Arrays.asList("Nonexistent"), Arrays.asList("friend"), Arrays.asList("Nonexistent Corp"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friend").withCompany("Alice Corp").build()));

        // Only company matches
        predicate = new SearchPredicate(Arrays.asList("Nonexistent"), Arrays.asList("nonexistentTag"), Arrays.asList("Alice Corp"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friend").withCompany("Alice Corp").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = Collections.singletonList("Alice");
        List<String> tagKeywords = Collections.singletonList("friend");
        List<String> companyKeywords = Collections.singletonList("Alice Corp");

        SearchPredicate predicate = new SearchPredicate(nameKeywords, tagKeywords, companyKeywords);
        String expected = "SearchPredicate{nameKeywords=[Alice], tagKeywords=[friend], companyKeywords=[Alice Corp]}";
        assertEquals(expected, predicate.toString());
    }
}
