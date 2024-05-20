package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;//added for checkBloodPressure method
import java.util.List;
import java.util.Comparator;


/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    public long startTime = 0;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        long endTime = System.currentTimeMillis();
        List<PatientRecord> patientRecords = dataStorage.getRecords(patient.getPatientIdInt(), startTime, endTime);

        if (patientRecords.size() < 3){
            return;
        }

        if (!validTrendAlertBp(patientRecords)){
            Alert alert = new Alert(patient.getPatientIdString(), "Blood pressure shows a consistent increase or decrease across three consecutive readings", endTime);
            triggerAlert(alert);
        }

        if (!validBpThreshold(patientRecords.get(0), patientRecords.get(1))) {
            Alert alert = new Alert(patient.getPatientIdString(), "Blood pressure is outside the normal range", endTime);
            triggerAlert(alert);
        }

        if (!validBloodOxygenSat(patientRecords.get(0))) {
            Alert alert = new Alert(patient.getPatientIdString(), "Blood oxygen saturation is below the normal range", endTime);
            triggerAlert(alert);
        }

        if (!validBloodOxygenSatTimed(patientRecords)) {
            Alert alert = new Alert(patient.getPatientIdString(), "Blood oxygen saturation has changed by more than 5% within 10 minutes", endTime);
            triggerAlert(alert);
        }

        if (hypotensiveHypoxemia(patientRecords.get(0), patientRecords.get(1))) {
            Alert alert = new Alert(patient.getPatientIdString(), "Patient is experiencing hypotensive hypoxemia", endTime);
            triggerAlert(alert);
        }

        if (!ecgAlert(patientRecords, 5, 10)) {
            Alert alert = new Alert(patient.getPatientIdString(), "Heart rate peaks above certain values", endTime);
            triggerAlert(alert);
        }

        startTime = endTime;
    }
    public boolean validTrendAlertBp (List<PatientRecord> readings) {
        readings.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 0; i < readings.size() - 2; i++) {
            PatientRecord currentRecord = readings.get(i);
            PatientRecord nextRecord = readings.get(i + 1);
            PatientRecord nextNextRecord = readings.get(i + 2);

            double difference1 = Math.abs(nextRecord.getMeasurementValue() - currentRecord.getMeasurementValue());
            double difference2 = Math.abs(nextNextRecord.getMeasurementValue() - nextRecord.getMeasurementValue());

            if (difference1 > 10 && difference2 > 10) {
                return false;
            }
        }
        return true;
    }
    public boolean validBpThreshold(PatientRecord reading1, PatientRecord reading2){
        if (reading1.getMeasurementValue() > 180 || reading1.getMeasurementValue() < 90){
            return false;
        }
        return !(reading2.getMeasurementValue() > 120) && !(reading2.getMeasurementValue() < 60);
    }
    public boolean validBloodOxygenSat(PatientRecord reading1){
        return !(reading1.getMeasurementValue() < 92);
    }
    public boolean validBloodOxygenSatTimed(List<PatientRecord> reading){
        reading.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 0; i < reading.size(); i++) {
            PatientRecord currentRecord = reading.get(i);
            for (int j = i + 1; j < reading.size(); j++) {
                PatientRecord nextRecord = reading.get(j);

                if (nextRecord.getTimestamp() - currentRecord.getTimestamp() <= 600000) {
                    double percentageChange = Math.abs(nextRecord.getMeasurementValue() - currentRecord.getMeasurementValue()) / currentRecord.getMeasurementValue() * 100;
                    if (percentageChange >= 5) {
                            return false;
                        }
                    } else {

                        break;
                    }
                }
        }
        return true;
    }

    public boolean hypotensiveHypoxemia(PatientRecord reading1, PatientRecord reading2){
        return (reading1.getMeasurementValue() < 90) && (reading2.getMeasurementValue() < 92);
    }
    public boolean ecgAlert(List<PatientRecord> records, int windowSize, double threshold) {
        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = windowSize; i < records.size(); i++) {
            double average = 0;
            for (int j = i - windowSize; j < i; j++) {
                average += records.get(j).getMeasurementValue();
            }
            average /= windowSize;

            if (records.get(i).getMeasurementValue() > average + threshold) {
                return false;
            }
        }
        return true;
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        System.out.println("Alert triggered: " + alert.getDetails());
        System.out.println("Notifying medical staff about the alert...");
    }
}
