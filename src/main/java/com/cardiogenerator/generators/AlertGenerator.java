// Removed underscore and changed file name
package com.cardiogenerator.generators;

import java.util.Random; //Removed space
import com.cardiogenerator.outputs.OutputStrategy;

/**
 * This class is used to Alert patients, which is implemented through a random number generator
 */
public class AlertGenerator implements PatientDataGenerator {
    //Changed variable name to comply to criteria for constants
    public static final Random RANDOM_GENERATOR = new Random();
    //Changed variable name to camelCase
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Constructs an AlertGenerator for a pre-determined amount of patients
     *
     * @param patientCount is referring to the amount of constructors needed for patients
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * This class generates the alerts for the specified patients from the AlertGenerator constructor
     *
     * @param patientId the ID of the patient
     * @param outputStrategy is the desired output strategy that the alert will use
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //Changed variable name to camelCase
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
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
