package com.example.businessexpensetracker;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.example.businessexpensetracker.UI.TripDetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TripDetailsTest {

    @Test
    public void isDateValid_ValidDate_ReturnsTrue() {
        TripDetails tripDetails = new TripDetails();
        boolean result = tripDetails.isDateValid("08-18-23");
        assertTrue(result);
    }

    @Test
    public void isDateValid_InvalidDate_ReturnsFalse() {
        TripDetails tripDetails = new TripDetails();
        boolean result = tripDetails.isDateValid("08-40-23");
        assertFalse(result);
    }

    @Test
    public void isEndDateAfterStartDate_EndDateAfter_ReturnsTrue() {
        TripDetails tripDetails = new TripDetails();
        boolean result = tripDetails.isEndDateAfterStartDate("08-18-23", "08-19-23");
        assertTrue(result);
    }

    @Test
    public void isEndDateAfterStartDate_EndDateBefore_ReturnsFalse() {
        TripDetails tripDetails = new TripDetails();
        boolean result = tripDetails.isEndDateAfterStartDate("08-19-23", "08-18-23");
        assertFalse(result);
    }

    @Test
    public void isEndDateAfterStartDate_SameDate_ReturnsFalse() {
        TripDetails tripDetails = new TripDetails();
        boolean result = tripDetails.isEndDateAfterStartDate("08-19-23", "08-19-23");
        assertFalse(result);
    }
}