package com.alerts;

import java.util.List;
import com.data_management.Patient;
import com.data_management.PatientRecord;

public class BloodPressureStrategy implements AlertStrategy{
    /**
     * Checks if an alert should be triggered for a given patient based on their blood pressure readings.
     *
     * @param patient the patient to check
     * @return true if an alert should be triggered, false otherwise
     */
    @Override
    public boolean checkAlert(Patient patient) {
        // Get the patient's systolic readings
        List<PatientRecord> systolicPressure = patient.getSystolicReadings();
        int systolicLength = systolicPressure.size();
        // Check if the last systolic reading is outside the normal range
        if (systolicLength > 0){
            PatientRecord lastSystolicReading = systolicPressure.get(systolicLength-1);
            if (lastSystolicReading.getMeasurementValue()>180||lastSystolicReading.getMeasurementValue() < 90){
                return true;
            }
        }
        // Get the patient's diastolic readings
        List<PatientRecord> diastolicPressure = patient.getDiastolicReadings();
        int diastolicLength = diastolicPressure.size();
        // Check if the last diastolic reading is outside the normal range
        if (diastolicLength > 0){
            PatientRecord diastolicRecord = diastolicPressure.get(diastolicLength-1);
            if (diastolicRecord.getMeasurementValue() > 120 || diastolicRecord.getMeasurementValue() < 60){
                return true;
            }
        }
        // Check if the last three systolic or diastolic readings show a significant increase or decrease
        if (systolicLength >= 3 && diastolicLength >= 3){
            // Check for significant increase or decrease in systolic readings
            if(systolicPressure.get(systolicLength-1).getMeasurementValue() > systolicPressure.get(diastolicLength-2).getMeasurementValue() + 10
                    && systolicPressure.get(diastolicLength-2).getMeasurementValue() > systolicPressure.get(diastolicLength-3).getMeasurementValue() + 10){
                return true;
            }

            if(systolicPressure.get(systolicLength-1).getMeasurementValue() > systolicPressure.get(diastolicLength-2).getMeasurementValue() - 10
                    &&systolicPressure.get(diastolicLength-2).getMeasurementValue() > systolicPressure.get(diastolicLength-3).getMeasurementValue() - 10){
                return true;
            }

            // Check for significant increase or decrease in diastolic readings
            if(diastolicPressure.get(systolicLength-1).getMeasurementValue() > diastolicPressure.get(diastolicLength-2).getMeasurementValue() + 10
                    && diastolicPressure.get(diastolicLength-2).getMeasurementValue() > diastolicPressure.get(diastolicLength-3).getMeasurementValue() + 10){
                return true;
            }

            if(diastolicPressure.get(systolicLength-1).getMeasurementValue() > diastolicPressure.get(diastolicLength-2).getMeasurementValue() - 10
                    && diastolicPressure.get(diastolicLength-2).getMeasurementValue() > diastolicPressure.get(diastolicLength-3).getMeasurementValue() - 10){
                return true;
            }
        }

        return false;
    }
}
