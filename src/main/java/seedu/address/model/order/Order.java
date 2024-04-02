package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Order in the address book.
 */
public class Order {
    private final Date arrivalDate;
    private final Remark remark;

    /**
     * Constructs a {@code Order}.
     *
     * @param arrivalDate a valid date of the order.
     * @param remark a valid remark of the order.
     */
    public Order(Date arrivalDate, Remark remark) {
        requireNonNull(arrivalDate);
        requireNonNull(remark);

        this.arrivalDate = arrivalDate;
        this.remark = remark;
    }

    /**
     * Returns the date of the order.
     */
    public Date getDate() {
        return arrivalDate;
    }

    /**
     * Returns the remark of the order.
     */
    public Remark getRemark() {
        return remark;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return arrivalDate.equals(otherOrder.arrivalDate) && remark.equals(otherOrder.remark);
    }

    @Override
    public int hashCode() {
        return arrivalDate.hashCode() + remark.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s (by: %s)]", remark, arrivalDate);
    }

}
