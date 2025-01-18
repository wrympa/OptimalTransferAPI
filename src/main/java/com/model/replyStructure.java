package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class replyStructure {
    private List<transferStructure> selectedTransferStructures;
    private int totalCost;
    private int totalWeight;

    @JsonCreator
    public replyStructure(
            @JsonProperty("selectedTransfers") List<transferStructure> selectedTransfers,
            @JsonProperty("totalCost") int costTotal,
            @JsonProperty("totalWeight") int weightTotal) {
        this.selectedTransferStructures = selectedTransfers;
        this.totalCost = costTotal;
        this.totalWeight = weightTotal;
    }
}
