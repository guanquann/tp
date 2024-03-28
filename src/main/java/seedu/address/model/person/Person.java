package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.order.Order;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Company company;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private boolean isFavourite = false;

    private final ArrayList<Order> orders;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Company company, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, company, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
        this.tags.addAll(tags);
        this.orders = new ArrayList<>();
    }

    /**
     * Every field must be present and not null.
     * This constructor is used to create a person with orders, and
     * indicates whether they have been marked as favourite
     */
    public Person(Name name, Phone phone, Email email, Address address, Company company, boolean isFavourite,
                  Set<Tag> tags, ArrayList<Order> orders) {
        requireAllNonNull(name, phone, email, address, company, isFavourite, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
        this.isFavourite = isFavourite;
        this.tags.addAll(tags);
        this.orders = orders;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Company getCompany() {
        return company;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an order list
     * @param order the order to be added
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Edits the order specified by the order and replaces it with the edited order
     * @param order the order to be edited
     * @param editedOrder the edited order
     */
    public void editOrder(Order order, Order editedOrder) {
        orders.set(orders.indexOf(order), editedOrder);
    }

    /**
     * Sets the person specified by the contact as favourite
     */
    public void addFavourite() {
        this.isFavourite = true;
    }
    /**
     * Removes the person specified by the contact from favourites
     */
    public void removeFavourite() {
        this.isFavourite = false;
    }

    public boolean getIsFavourite() {
        return this.isFavourite;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && company.equals(otherPerson.company)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, company, tags, orders);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("company", company)
                .add("isFavourite", isFavourite)
                .add("tags", tags)
                .add("orders", orders)
                .toString();
    }

}
