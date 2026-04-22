package com.jeeplus.monitor.controller;


import com.jeeplus.monitor.server.ServerOS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统监控Controller
 *
 * @author liugf
 * @version 2021-09-01
 */
@Tag(name = "系统监控")
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @GetMapping("/server/info")
    public ResponseEntity info() {

        return ResponseEntity.ok ( ServerOS.getInfo ( ) );
    }

}
