package com.alerts;

public class AlertFactory {
    /**
     * Creates an Alert based on the condition.
     * If the condition contains "Systolic Pressure" or "Diastolic Pressure", a BloodPressureAlert is created.
     * If the condition contains "Saturation" or "Hypoxemia", a BloodOxygenAlert is created.
     * Otherwise, an ECGAlert is created.
     *
     * @param patientId the ID of the patient
     * @param condition the condition of the patient
     * @param timestamp the timestamp of the alert
     * @return an Alert object based on the condition
     */
    public Alert createAlert(String patientId, String condition, long timestamp) {
        if (condition.contains("Systolic Pressure")||condition.contains("Diastolic Pressure")){
            return new BloodPressureAlertFactory().createAlert(patientId, condition, timestamp);
        }
        if (condition.contains("Saturation")||condition.contains("Hypoxemia")){
            return new BloodOxygenAlertFactory().createAlert(patientId, condition, timestamp);
        }
        else{
            return new ECGAlertFactory().createAlert(patientId, condition, timestamp);
        }
    }
}
