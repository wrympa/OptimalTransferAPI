package com.controller;

import com.model.replyStructure;
import com.model.requestStructure;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.service.Calculator;


@RestController
@RequestMapping("/api/transfers")
@ComponentScan(basePackages = "com.service")
public class TransfersController {
    private final Calculator calculator;

    public TransfersController(Calculator calculator) {
        this.calculator = calculator;
    }

    @PostMapping("/findOptimal")
    public ResponseEntity<replyStructure> findOptimalTransfers(@RequestBody requestStructure request) {
        try{
            calculator.setUp(request);
            replyStructure result = calculator.calculate();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new replyStructure(null, -1, -1));
        }
    }
}
