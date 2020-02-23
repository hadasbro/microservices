package com.github.hadasbro.supplier.service.graylog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@Service
public class ApploggerService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ApploggerConfig config;

    /**
     * GrayLogClient
     */
    private GrayLogClient grayLogClient;

    /**
     * GrayLogClient
     */
    public interface GrayLogClient {
        void logException(GrayLogException exc);
    }

    /**
     * GrayLogException
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class GrayLogException implements Serializable {

        private static final long serialVersionUID = 1L;

        private String logMsg;
        private String stackTrace;
        private LocalDateTime dateTime;
        private TYPE logType;

        /**
         * TYPE
         */
        public enum TYPE {
            INFO, ERROR, WARNING
        }

        /**
         * GrayLogException
         */
        public GrayLogException() {}

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
         *
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
         *
         * @param logMsg -
         */
        public GrayLogException(String logMsg) {
            this(logMsg, TYPE.INFO);
        }

        /**
         * getLogMsg
         *
         * @return String
         */
        public String getLogMsg() {
            return logMsg;
        }

        /**
         * getStackTrace
         *
         * @return String
         */
        public String getStackTrace() {
            return stackTrace;
        }

        /**
         * LocalDateTime
         *
         * @return LocalDateTime
         */
        public LocalDateTime getDateTime() {
            return dateTime;
        }

        /**
         * getLogType
         *
         * @return TYPE
         */
        public TYPE getLogType() {
            return logType;
        }

        /**
         * toString
         *
         * @return String
         */
        @Override
        public String toString() {
            return logMsg +
                    "[ " + dateTime + " ]" +
                    "[ type: " + logType + " ]" +
                    "[ trace: " + stackTrace + " ]";
        }

    }

    /**
     * init
     */
    @PostConstruct
    public void init() {
        grayLogClient = exc -> jmsTemplate.convertAndSend(config.getDestination(), exc);
    }

    /**
     * sendLog - final log's sender
     * using Feign client
     *
     * SEND LOG
     *
     * @param glog -
     */
    private void sendLog(GrayLogException glog) {
        try {
            grayLogClient.logException(glog);
        } catch (Throwable t){
            t.printStackTrace();
        }
    }

    /**
     * log
     *
     * @param t -
     */
    public void log(Throwable t) {
        sendLog(new GrayLogException(t));
    }

    /**
     * log
     *
     * @param log -
     */
    public void log(String log) {
        sendLog(new GrayLogException(log));
    }
}
