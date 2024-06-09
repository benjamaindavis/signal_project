package com.alerts;

/**
 * The AlertDecorator class implements the AlertInterface.
 * It is a part of the Decorator design pattern and is used to decorate Alert objects.
 */
public class AlertDecorator implements AlertInterface{
    protected Alert decoratedAlert;

    /**
     * Constructs a new AlertDecorator with a specified Alert to decorate.
     *
     * @param decoratedAlert the Alert to be decorated
     */
    public AlertDecorator(Alert decoratedAlert) {
        this.decoratedAlert = decoratedAlert;
    }

    /**
     * Retrieves the patient's ID from the decorated Alert.
     *
     * @return the patient's ID as a string
     */
    @Override
    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    /**
     * Retrieves the condition from the decorated Alert.
     *
     * @return the condition as a string
     */
    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    /**
     * Retrieves the timestamp from the decorated Alert.
     *
     * @return the timestamp as a long
     */
    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }
}
