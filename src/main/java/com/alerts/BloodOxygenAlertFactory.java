package com.alerts;

public class BloodOxygenAlertFactory extends AlertFactory {
    /**
     * Creates a new BloodOxygenAlert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the patient
     * @param timestamp the timestamp of the alert
     * @return a new BloodOxygenAlert object
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, condition, timestamp);

    }
}