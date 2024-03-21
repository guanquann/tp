package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;

/**
 * A UI component that displays information of a {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    public final Order order;

    @FXML
    private Label id;
    @FXML
    private Label remark;
    @FXML
    private Label date;
    @FXML
    private Label status;

    /**
     * Creates a {@code OrderCode} with the given {@code Order} and index to display.
     */
    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(String.valueOf(displayedIndex));
        remark.setText(order.getRemark());
        date.setText(order.getDate().toString());
        status.setText(order.getStatus());
    }
}
