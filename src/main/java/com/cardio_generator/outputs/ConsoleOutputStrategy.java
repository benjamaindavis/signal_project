package com.cardio_generator.outputs;

public class ConsoleOutputStrategy implements OutputStrategy {
    /**
     * Outputs data provided into the console
     *
     * @param patientId = the Id of the patient
     * @param timestamp = the timestamp of the data
     * @param label = the label of the data
     * @param data = the data that will be shown
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
