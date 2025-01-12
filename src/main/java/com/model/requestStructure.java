package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class requestStructure {
    private int maxWeight;
    private List<transferStructure> availableTransferStructures;

    public int getMaxWeight() {
        return maxWeight;
    }

    public List<transferStructure> getAvailableTransfers() {
        return availableTransferStructures;
    }

    public void setAvailableTransfers(List<transferStructure> available_transfer_structures) {
        this.availableTransferStructures = available_transfer_structures;
    }

    public void setMaxWeight(int max_weight) {
        this.maxWeight = max_weight;
    }

    @JsonCreator
    public requestStructure(
            @JsonProperty("availableTransferStructures") List<transferStructure> available_transfer_structures,
            @JsonProperty("totalCost") int max_weight) {
        this.availableTransferStructures = available_transfer_structures;
        this.maxWeight = max_weight;
    }
}
