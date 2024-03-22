package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.order.Order;

/**
 * Controller for an order page
 */
public class OrderWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(OrderWindow.class);
    private static final String FXML = "OrderWindow.fxml";

    private OrderListPanel orderListPanel;
    private final Index targetIndex;

    @FXML
    private Label userId;
    @FXML
    private StackPane orderListPanelPlaceholder;

    /**
     * Creates a new OrderWindow.
     *
     * @param root Stage to use as the root of the OrderWindow.
     */
    public OrderWindow(Stage root, Index targetIndex) {
        super(FXML, root);
        this.targetIndex = targetIndex;
    }

    /**
     * Creates a new OrderWindow.
     */
    public OrderWindow(ObservableList<Order> orders, Index targetIndex) {
        this(new Stage(), targetIndex);
        logger.info("Creating order page for user " + targetIndex.getOneBased());
        userId.setText("Supplier " + targetIndex.getOneBased());
        orderListPanel = new OrderListPanel(orders, targetIndex);
        orderListPanelPlaceholder.getChildren().add(orderListPanel.getRoot());
    }

    /**
     * Shows the order window.
        */
    public void show() {
        logger.fine("Showing order page on the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }
}
