package com.github.hadasbro.graylog_service.controller;

import com.github.hadasbro.graylog_service.dto.GrayLogException;
import com.github.hadasbro.graylog_service.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * LogController
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/log-server")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * GrayLogException
     * @param glog -
     */
    @PostMapping("/log")
    @ResponseStatus(HttpStatus.CREATED)
    public void log(@RequestBody GrayLogException glog) {
        logService.process(glog);
    }

    /**
     * test
     * <p>
     * Temporary test method
     * just for GrayLog's test
     */
    @Profile("test")
    @GetMapping("/test")
    public void test() {
        logService.process(new GrayLogException("a1", GrayLogException.TYPE.INFO));
        logService.process(new GrayLogException("a3", GrayLogException.TYPE.WARNING));
        logService.process(new GrayLogException("a4", GrayLogException.TYPE.ERROR));
    }
}
