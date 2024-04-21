package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class generates data for each patient
 */
public interface PatientDataGenerator {
    /**
     * Generates the data
     *
     * @param patientId the id of the patient
     * @param outputStrategy the specified method of outputting the data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
