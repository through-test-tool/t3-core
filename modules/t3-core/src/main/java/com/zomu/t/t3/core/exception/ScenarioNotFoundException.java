package com.zomu.t.t3.core.exception;

public class ScenarioNotFoundException extends RuntimeException {

    public ScenarioNotFoundException(String scenarioId) {
        super("not found scenario: '" + scenarioId + "'");
    }
}
