package com.alerts;

public class BloodOxygenAlert extends Alert {
    /**
     * Constructs a new BloodOxygenAlert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the patient
     * @param timestamp the timestamp of the alert
     */
    public BloodOxygenAlert(String patientId, String condition, long timestamp){
        super(patientId, condition, timestamp);
    }
}
