package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Order in the address book.
 */
public class Order {
    private final Date arrivalDate;
    private final Remark remark;
    private final Status status;

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
        this.status = new Status("pending");
    }

    /**
     * Constructs a {@code Order}.
     *
     * @param arrivalDate a valid date of the order.
     * @param remark a valid remark of the order.
     * @param status a valid status of the order.
     */
    public Order(Date arrivalDate, Remark remark, Status status) {
        requireNonNull(arrivalDate);
        requireNonNull(remark);

        this.arrivalDate = arrivalDate;
        this.remark = remark;
        this.status = status;
    }

    public Date getDate() {
        return arrivalDate;
    }

    public Remark getRemark() {
        return remark;
    }

    public Status getStatus() {
        return status;
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
        return arrivalDate.equals(otherOrder.arrivalDate) && remark.equals(otherOrder.remark)
                && status.equals(otherOrder.status);
    }

    @Override
    public int hashCode() {
        return arrivalDate.hashCode() + remark.hashCode() + status.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[%s (by: %s, status: %s)]", remark, arrivalDate, status);
    }

}
