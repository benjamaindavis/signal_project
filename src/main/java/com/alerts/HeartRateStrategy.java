package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class HeartRateStrategy implements AlertStrategy{
    /**
     * Checks if an alert should be triggered for a given patient based on their ECG readings.
     *
     * @param patient the patient to check
     * @return true if an alert should be triggered, false otherwise
     */
    @Override
    public boolean checkAlert(Patient patient){
        // Check if the patient has any ECG readings
        if (patient.getECGReadings().isEmpty()){
            return false;
        }
        // Get the last ECG reading
        PatientRecord data = patient.getECGReadings().get(patient.getECGReadings().size()-1);
        // Check if the reading is of type "ECG"
        if(data.getRecordType().equals("ECG")){
            // Check if the heart rate is below 50 or above 100
            if (data.getMeasurementValue()<50){
                return true;
            }
            return data.getMeasurementValue() > 100;
        }
        return false;
    }
}
