package com.github.hadasbro.graylog_service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GrayLogException
 */
@SuppressWarnings({"unused","WeakerAccess"})
public final class GrayLogException implements Serializable {

    private static final long serialVersionUID = 1L;

    private String logMsg;
    private String stackTrace;
    private LocalDateTime dateTime;
    private TYPE logType;

    public enum TYPE {
        INFO, ERROR, WARNING
    }

    /**
     * GrayLogException
     */
    public GrayLogException(){}

    /**
     * GrayLogException
     *
     * @param t -
     * @param type -
     */
    public GrayLogException(Throwable t, TYPE type) {

        this.stackTrace = Stream
                .of(t.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n", "[", "]"));

        this.logMsg = t.getMessage();

        this.dateTime = LocalDateTime.now();

        this.logType = type;

    }

    /**
     * GrayLogException
     * @param t -
     */
    public GrayLogException(Throwable t) {
        this(t, TYPE.INFO);

    }

    /**
     * GrayLogException
     *
     * @param logMsg -
     * @param type -
     */
    public GrayLogException(String logMsg, TYPE type) {
        this.stackTrace = null;
        this.logMsg = logMsg;
        this.dateTime = LocalDateTime.now();
        this.logType = type;
    }

    /**
     * GrayLogException
     * @param logMsg -
     */
    public GrayLogException(String logMsg) {
        this(logMsg, TYPE.INFO);
    }

    /**
     * getLogMsg
     *
     * @return -
     */
    public String getLogMsg() {
        return logMsg;
    }

    /**
     * getStackTrace
     * @return -
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * getDateTime
     * @return -
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }


    /**
     * getLogType
     *
     * @return -
     */
    public TYPE getLogType() {
        return logType;
    }

    /**
     * toString
     *
     * @return -
     */
    @Override
    public String toString() {

        return logMsg +
                "[ " + dateTime + " ]" +
                "[ type: " + logType + " ]" +
                "[ trace: " + stackTrace + " ]";
    }

}
