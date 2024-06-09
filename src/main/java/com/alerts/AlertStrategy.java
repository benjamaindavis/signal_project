package com.alerts;

import com.data_management.Patient;

/**
 * The AlertStrategy interface defines the contract for implementing different alert strategies.
 * Each strategy should implement the checkAlert method which takes a Patient object and returns a boolean.
 */
public interface AlertStrategy {
    /**
     * Checks if an alert should be triggered for a given patient.
     *
     * @param patient the patient to check
     * @return true if an alert should be triggered, false otherwise
     */
    boolean checkAlert(Patient patient);
}
