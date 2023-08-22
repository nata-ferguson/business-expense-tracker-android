package com.nferg.businessexpensetracker;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import com.nferg.businessexpensetracker.UI.ExpenseDetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ExpenseDetailsTest {
    @Test
    public void isDateValid_ValidDate_ReturnsTrue() {
        ExpenseDetails expenseDetails = new ExpenseDetails();
        boolean result = expenseDetails.isDateValid("08-18-23");
        assertTrue(result);
    }

    @Test
    public void isDateValid_InvalidDate_ReturnsFalse() {
        ExpenseDetails expenseDetails = new ExpenseDetails();
        boolean result = expenseDetails.isDateValid("08-40-23"); // Invalid day of the month
        assertFalse(result);
    }

}
