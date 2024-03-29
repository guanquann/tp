package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Date;
import seedu.address.model.order.Order;
import seedu.address.model.order.Remark;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String arrivalDate;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given {@code order}.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("arrivalDate") String arrivalDate,
                            @JsonProperty("remark") String remark) {
        this.arrivalDate = arrivalDate;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        this.arrivalDate = source.getDate().toString();
        this.remark = source.getRemark().toString();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        if (arrivalDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Remark"));
        }

        final Date modelDate = new Date(arrivalDate);
        final Remark modelRemark = new Remark(remark);

        return new Order(modelDate, modelRemark);
    }
}
