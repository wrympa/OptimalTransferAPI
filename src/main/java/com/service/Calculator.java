package com.service;

import com.model.transferStructure;
import com.model.replyStructure;
import com.model.requestStructure;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Calculator {
    private int maxWeight;
    private List<transferStructure> transferStructures;
    @Getter
    private List<transferStructure> leftovers;

    public void setUp(requestStructure request) {
        this.maxWeight = request.getMaxWeight();
        this.transferStructures = request.getAvailableTransferStructures();
        this.leftovers = new ArrayList<>();
    }

    public replyStructure calculate() {
        if(!sanityCheck()){
            return new replyStructure(List.of(), 0, 0);
        }

        int transfer_count = transferStructures.size();
        int[][] dp = new int [transfer_count + 1][maxWeight + 1];
        boolean[][] selected = new boolean [transfer_count + 1][maxWeight + 1];

        for (int i = 1; i <= transfer_count; i++) {
            transferStructure currentTransferStructure = transferStructures.get(i - 1);
            for (int j = 0; j <= maxWeight; j++) {
                int excluded = dp[i - 1][j];

                if (currentTransferStructure.getWeight() <= j) {
                    int included = dp[i - 1][j - currentTransferStructure.getWeight()] + currentTransferStructure.getCost();

                    if (included > excluded) {
                        dp[i][j] = included;
                        selected[i][j] = true;
                    } else {
                        dp[i][j] = excluded;
                        selected[i][j] = false;
                    }
                } else {
                    dp[i][j] = excluded;
                    selected[i][j] = false;
                }
            }
        }

        List<transferStructure> selectedTransferStructures = new ArrayList<>();
        int currentWeight = maxWeight;

        for (int i = transfer_count; i > 0; i--) {
            if (selected[i][currentWeight]) {
                transferStructure selectedTransferStructure = transferStructures.get(i - 1);
                selectedTransferStructures.add(selectedTransferStructure);
                currentWeight -= selectedTransferStructure.getWeight();
            }
        }
        Collections.reverse(selectedTransferStructures);

        this.leftovers.addAll(transferStructures.stream().filter(selectedTransferStructures::contains).toList());

        return new replyStructure(selectedTransferStructures, dp[transfer_count][maxWeight], maxWeight - currentWeight);
    }

    private boolean sanityCheck() {
        for (transferStructure transferStructure : transferStructures) {
            if (transferStructure.getWeight() <= maxWeight) {
                return true;
            }
        }
        return false;
    }
}
