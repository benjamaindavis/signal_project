package com.alerts;

public class ECGAlert extends Alert {
    /**
     * Constructs a new ECGAlert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the patient
     * @param timestamp the timestamp of the alert
     */
    public ECGAlert(String patientId, String condition, long timestamp){
        super(patientId, condition, timestamp);
    }
}
