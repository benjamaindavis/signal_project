package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatInterval; // seconds

    /**
     * Constructs a new RepeatedAlertDecorator with the specified decorated alert and repeat interval.
     *
     * @param decoratedAlert the alert to decorate
     * @param repeatInterval the repeat interval in seconds
     */
    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatInterval) {
        super(decoratedAlert);
        this.repeatInterval = repeatInterval;
    }

    /**
     * Checks and repeats the alert every specified interval for a patient.
     */
    public void checkAndRepeat() {
        System.out.println("Checking and repeating alert every " + repeatInterval + " seconds for patient " + getPatientId());
    }
}
