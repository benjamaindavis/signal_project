package com.cardiogenerator.outputs;
/**
 * This interface calls the method output, and is implemented through every output strategy
 */
public interface OutputStrategy {
    /**
     * This interface calls the method output, with parameters :
     * @param patientId = the Id of the patient
     * @param timestamp = the timestamp of the data
     * @param label = the label of the data
     * @param data = the data that will be shown
     *
     */
    void output(int patientId, long timestamp, String label, String data);
}
