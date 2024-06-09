package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {
    private String priorityMeasurement;

    /**
     * Constructs a new PriorityAlertDecorator with the specified decorated alert and priority measurement.
     *
     * @param decoratedAlert the alert to decorate
     * @param priorityMeasurement the priority measurement to add
     */
    public PriorityAlertDecorator(Alert decoratedAlert, String priorityMeasurement) {
        super(decoratedAlert);
        this.priorityMeasurement = priorityMeasurement;
    }

    /**
     * Constructs a new PriorityAlertDecorator with the specified decorated alert.
     *
     * @param decoratedAlert the alert to decorate
     */
    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    /**
     * Returns the priority measurement of this alert.
     *
     * @return the priority measurement
     */
    public String getPriorityMeasurement() {
        return priorityMeasurement;
    }

    /**
     * Returns the condition of the decorated alert, with the priority measurement added.
     *
     * @return the condition of the decorated alert
     */
    @Override
    public String getCondition() {
        return super.getCondition() + " (Priority: " + priorityMeasurement + ")";
    }
}
