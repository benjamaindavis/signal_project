package com.alerts;

public class BloodPressureAlertFactory extends AlertFactory {
    /**
     * Creates a new BloodPressureAlert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the patient
     * @param timestamp the timestamp of the alert
     * @return a new BloodPressureAlert object
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp){
        return new BloodPressureAlert(patientId, condition, timestamp);
    }
}
