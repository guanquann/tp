package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Person}'s {@code Name} and {@code Tag} matches any of the keywords given.
 */
public class SearchPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;
    private final List<String> companyKeywords;

    /**
     * Constructor for SearchPredicate.
     * @param nameKeywords List of name keywords to search for.
     * @param tagKeywords List of tag keywords to search for.
     * @param companyKeywords List of company keywords to search for.
     */
    public SearchPredicate(List<String> nameKeywords, List<String> tagKeywords,
                           List<String> companyKeywords) {
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
        this.companyKeywords = companyKeywords;

    }

    @Override
    public boolean test(Person person) {
        boolean matchesName = nameKeywords.stream()
                .allMatch(keyword -> StringUtil.containsSubstringIgnoreCase(person.getName().fullName, keyword));
        boolean matchesTags = tagKeywords.stream()
                .allMatch(keyword -> person.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsSubstringIgnoreCase(tag.tagName, keyword)));
        boolean matchesCompany = companyKeywords.stream()
                .allMatch(keyword -> StringUtil.containsSubstringIgnoreCase(person.getCompany().companyName, keyword));
        return matchesName && matchesTags && matchesCompany;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchPredicate)) {
            return false;
        }

        SearchPredicate that = (SearchPredicate) other;
        return Objects.equals(nameKeywords, that.nameKeywords)
                && Objects.equals(tagKeywords, that.tagKeywords)
                && Objects.equals(companyKeywords, that.companyKeywords);
    }

    @Override
    public String toString() {
        return "SearchPredicate{"
                + "nameKeywords=" + nameKeywords
                + ", tagKeywords=" + tagKeywords
                + ", companyKeywords=" + companyKeywords
                + '}';
    }
}
