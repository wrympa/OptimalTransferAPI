package com.controller;

import com.model.replyStructure;
import com.model.requestStructure;
import com.model.transferStructure;
import com.service.LeftoverHolder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.service.Calculator;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/transfers")
@ComponentScan(basePackages = "com.service")
public class TransfersController {
    private final Calculator calculator;
    private final LeftoverHolder leftoverHolder;

    public TransfersController(Calculator calculator, LeftoverHolder leftoverHolder) {
        this.calculator = calculator;
        this.leftoverHolder = leftoverHolder;
    }

    @PostMapping("/findOptimal")
    public ResponseEntity<replyStructure> findOptimalTransfers(@RequestBody requestStructure request) {
        try{
            calculator.setUp(request);
            replyStructure result = calculator.calculate();
            System.out.println("BASE REQUEST");


            List<transferStructure> prevLeftovers = leftoverHolder.getLeftovers();
            System.out.println("ACCESSED LEFTOVERS");
            prevLeftovers.addAll(calculator.getLeftovers());
            System.out.println("ADDED TO LEFTOVERS");

            int newMaxWeight = request.getMaxWeight() - result.getTotalWeight();
            requestStructure leftoverRequest = new requestStructure(prevLeftovers, newMaxWeight);
            calculator.setUp(leftoverRequest);
            replyStructure leftoverResult = calculator.calculate();
            System.out.println("RECALC");

            result.setTotalCost(result.getTotalCost()+leftoverResult.getTotalCost());
            result.setTotalWeight(result.getTotalWeight()+leftoverResult.getTotalWeight());

            List<transferStructure> finalSelected = new ArrayList<>();
            finalSelected.addAll(result.getSelectedTransferStructures());
            finalSelected.addAll(leftoverResult.getSelectedTransferStructures());
            result.setSelectedTransferStructures(finalSelected);
            System.out.println("MERGE RES");

            leftoverHolder.setLeftovers(calculator.getLeftovers());
            System.out.println("SET LEFTOVERS FOR NEXT");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new replyStructure(null, -1, -1));
        }
    }
}
