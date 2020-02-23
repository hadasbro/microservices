package com.github.hadasbro.graylog_service.service;

import com.github.hadasbro.graylog_service.dto.GrayLogException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
public class LogService {

    /**
     * logger
     */
    private Logger logger = LogManager.getLogger(LogService.class);

    /**
     * process
     *
     * @param glog -
     */
    public void process(GrayLogException glog) {

        // TODO - all logging logic
        // filter, transform, dispatch logs

        switch (glog.getLogType()) {

            case WARNING:
                logger.warn(glog.toString());
                break;

            case ERROR:
                logger.error(glog.toString());
                break;

            case INFO:
            default:
                logger.info(glog.toString());
                break;

        }

    }

}
