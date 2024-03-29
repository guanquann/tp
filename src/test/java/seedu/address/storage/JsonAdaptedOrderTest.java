package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Date;
import seedu.address.model.order.Order;
import seedu.address.model.order.Remark;

public class JsonAdaptedOrderTest {
    private static final Order ORDER = new Order(new Date("2020-01-01"), new Remark("100 chicken wings"));

    private static final String VALID_DATE = "2020-01-01";
    private static final String VALID_REMARK = "100 chicken wings";

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws IllegalValueException {
        JsonAdaptedOrder jsonAdaptedOrder = new JsonAdaptedOrder(VALID_DATE, VALID_REMARK);
        Order order = jsonAdaptedOrder.toModelType();
        assertEquals(ORDER, order);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedOrder jsonAdaptedOrder = new JsonAdaptedOrder(null, VALID_REMARK);
        assertThrows(IllegalValueException.class, jsonAdaptedOrder::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedOrder jsonAdaptedOrder = new JsonAdaptedOrder(VALID_DATE, null);
        assertThrows(IllegalValueException.class, jsonAdaptedOrder::toModelType);
    }
}
