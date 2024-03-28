package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a status of an order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    enum StatusEnum {
        PENDING("pending"),
        ARRIVED("arrived"),
        LATE("late");

        private final String status;

        StatusEnum(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return status;
        }
    }

    public static final String MESSAGE_CONSTRAINTS = "Status should be one of 'pending', 'arrived', or 'late'";

    private final String status;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.status = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getStatus().equals(test)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return status.equals(otherStatus.status);
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
