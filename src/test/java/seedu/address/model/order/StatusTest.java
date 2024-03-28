package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Status("invalid"));
    }

    @Test
    public void isValidStatus() {
        // invalid status
        assertFalse(Status.isValidStatus("test"));
        assertFalse(Status.isValidStatus(""));

        // valid status
        assertTrue(Status.isValidStatus("pending"));
        assertTrue(Status.isValidStatus("arrived"));
        assertTrue(Status.isValidStatus("late"));
    }

    @Test
    public void equals() {
        Status status = new Status("pending");

        // same values -> returns true
        assertTrue(status.equals(new Status("pending")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(new Status("late")));

        // check for enum
        assertFalse(Status.StatusEnum.PENDING.equals(null));
        assertFalse(Status.StatusEnum.PENDING.toString().equals("arrived"));
        assertFalse(Status.StatusEnum.ARRIVED.toString().equals("late"));
        assertFalse(Status.StatusEnum.LATE.toString().equals("pending"));

        assertTrue(Status.StatusEnum.PENDING.toString().equals("pending"));
        assertTrue(Status.StatusEnum.ARRIVED.toString().equals("arrived"));
        assertTrue(Status.StatusEnum.LATE.toString().equals("late"));
    }
}
