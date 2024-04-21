package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class is used to Alert patients, which is implemented through a random number generator
 *
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] AlertStates; // false = resolved, true = pressed
    /**
     * Constructs an AlertGenerator for a pre-determined amount of patients
     *
     * @param patientCount is referring to the amount of constructors needed for patients
     */
    public AlertGenerator(int patientCount) {
        AlertStates = new boolean[patientCount + 1];
    }

    @Override
    /**
     * This class generates the alerts for the specified patients from the AlertGenerator constructor
     *
     * @param patientId the ID of the patient
     * @param outputStrategy is the desired output strategy that the alert will use
     */
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (AlertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    AlertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    AlertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
