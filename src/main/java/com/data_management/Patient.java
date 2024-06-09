package com.data_management;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and
 * retrieval
 * of medical records based on specified criteria.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;
    private List<PatientRecord> systolicReadings;
    private List<PatientRecord> diastolicReadings;
    private List<PatientRecord> ECGReadings;
    private List<PatientRecord> oxygenSatReadings;

    /**
     * Constructs a new Patient with a specified ID.
     * Initializes an empty list of patient records.
     *
     * @param patientId the unique identifier for the patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
        this.systolicReadings = new ArrayList<>();
        this.diastolicReadings = new ArrayList<>();
        this.ECGReadings = new ArrayList<>();
        this.oxygenSatReadings = new ArrayList<>();
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp.
     *
     * @param measurementValue the measurement value to store in the record
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since UNIX epoch
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that fall within a
     * specified time range.
     * The method filters records based on the start and end times provided.
     *
     * @param startTime the start of the time range, in milliseconds since UNIX
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since UNIX epoch
     * @return a list of PatientRecord objects that fall within the specified time
     * range
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> filteredRecords = new ArrayList<>();

        for (PatientRecord record : this.patientRecords) {
            if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                filteredRecords.add(record);
            }
        }

        return filteredRecords;
    }

    /**
     * Converts the patient's ID to a string.
     *
     * @return the patient's ID as a string
     */
    public String getPatientIdString() {
        return Integer.toString(this.patientId);
    }

    /**
     * Retrieves the patient's ID as an integer.
     *
     * @return the patient's ID as an integer
     */
    public int getPatientIdInt() {
        return this.patientId;
    }

    /**
     * Retrieves the list of systolic readings for this patient.
     *
     * @return a list of PatientRecord objects representing the systolic readings
     */
    public List<PatientRecord> getSystolicReadings() {
        return systolicReadings;
    }

    /**
     * Retrieves the list of diastolic readings for this patient.
     *
     * @return a list of PatientRecord objects representing the diastolic readings
     */
    public List<PatientRecord> getDiastolicReadings() {
        return diastolicReadings;
    }

    /**
     * Retrieves the list of ECG readings for this patient.
     *
     * @return a list of PatientRecord objects representing the ECG readings
     */
    public List<PatientRecord> getECGReadings() {
        return ECGReadings;
    }

    /**
     * Retrieves the list of oxygen saturation readings for this patient.
     *
     * @return a list of PatientRecord objects representing the oxygen saturation readings
     */
    public List<PatientRecord> getOxygenSatReadings() {
        return oxygenSatReadings;
    }
}
