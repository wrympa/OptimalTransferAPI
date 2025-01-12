package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class replyStructure {
    private List<transferStructure> selectedTransferStructures;
    private int totalCost;
    private int totalWeight;

    public void setSelectedTransfers(List<transferStructure> selected_transfer_structures) {
        this.selectedTransferStructures = selected_transfer_structures;
    }

    public void setTotalCost(int total_cost) {
        this.totalCost = total_cost;
    }

    public void setTotalWeight(int total_weight) {
        this.totalWeight = total_weight;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public List<transferStructure> getSelectedTransfers() {
        return selectedTransferStructures;
    }

    @JsonCreator
    public replyStructure(
            @JsonProperty("selectedTransferStructures") List<transferStructure> selected_transfer_structures,
            @JsonProperty("totalCost") int total_cost,
            @JsonProperty("totalWeight") int total_weight) {
        this.selectedTransferStructures = selected_transfer_structures;
        this.totalCost = total_cost;
        this.totalWeight = total_weight;
    }
}
