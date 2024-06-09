package com.alerts;

public class BloodPressureAlert extends Alert {
    /**
     * Constructs a new BloodPressureAlert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the patient
     * @param timestamp the timestamp of the alert
     */
    public BloodPressureAlert(String patientId, String condition, long timestamp){
        super(patientId, condition, timestamp);
    }
}
