package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.order.Date;
import seedu.address.model.order.Order;
import seedu.address.model.order.Remark;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final Order ORDER = new Order(new Date("2020-01-01"), new Remark("100 chicken wings"));

    private TypicalOrders() {} // prevents instantiation

    public static ArrayList<Order> getTypicalOrders() {
        return new ArrayList<>(List.of(ORDER));
    }
}
