package com.github.hadasbro.graylog_service.service.graylog.client.sample;

import feign.Feign;
import feign.Headers;
import feign.Logger;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@Profile("sample")
@Service
public class FeignLoggerClient {

    /**
     * GrayLogClient
     */
    private GrayLogClient grayLogClient;

    /**
     * logServerEndpoint
     */
    @Value("${log_server_endpoint}")
    private String logServerEndpoint;

    /**
     * GrayLogClient
     */
    //@FeignClient(value = "spring-boot-provider", configuration = GlobalConfiguration.class)
    public interface GrayLogClient {
        @RequestLine("POST")
        @Headers("Content-Type: application/json")
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
        grayLogClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(GrayLogClient.class))
                .logLevel(Logger.Level.FULL)
                .target(GrayLogClient.class, logServerEndpoint);
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
