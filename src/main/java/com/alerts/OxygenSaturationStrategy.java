package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class OxygenSaturationStrategy implements AlertStrategy{
    /**
     * Checks if an alert should be triggered for a given patient based on their oxygen saturation readings.
     *
     * @param patient the patient to check
     * @return true if an alert should be triggered, false otherwise
     */
    @Override
    public boolean checkAlert(Patient patient){
        // Check if the patient has any oxygen saturation readings
        if (patient.getOxygenSatReadings().size() < 1){
            return false;
        }
        // Get the last oxygen saturation reading
        PatientRecord data = patient.getOxygenSatReadings().get(patient.getOxygenSatReadings().size()-1);
        // Check if the reading is of type "Saturation" and if the value is below 92
        if (data.getRecordType().equals("Saturation")){
            if (data.getMeasurementValue() < 92){
                return true;
            }
        }

        // Check if the patient has any systolic readings
        if (patient.getSystolicReadings().size() < 1){
            return false;
        }
        // Check for hypotensive hypoxemia (low blood pressure and low oxygen saturation)
        PatientRecord systolicData = patient.getSystolicReadings().get(patient.getSystolicReadings().size() - 1);
        if (data.getMeasurementValue() < 92 && systolicData.getMeasurementValue() < 90){
            return true;
        }
        return false;
    }
}
