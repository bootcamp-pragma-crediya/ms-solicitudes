package com.crediya.solicitudes.api.constants;

public final class LogMessages {
    private LogMessages() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String ERROR_BEAN_VALIDATION = "[Error] Bean validation: {}";
    public static final String ERROR_BAD_INPUT = "[Error] Bad input: {}";
    public static final String ERROR_DOMAIN = "[Error] {}";
    public static final String ERROR_UNEXPECTED = "[Error] Unexpected";
    public static final String HANDLER_POST_REQUEST = "[Handler] POST /api/v1/solicitud doc={} type={} amount={} term={}";
    public static final String HANDLER_CREATED = "[Handler] Created id={} status={}";
    public static final String HANDLER_ERROR = "[Handler] Error processing request: {}";
}