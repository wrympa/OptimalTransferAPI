package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class requestStructure {
    private int maxWeight;
    private List<transferStructure> availableTransferStructures;

    @JsonCreator
    public requestStructure(
            @JsonProperty("availableTransfers") List<transferStructure> availableTransfers,
            @JsonProperty("maxWeight") int maximumWeight) {
        this.availableTransferStructures = availableTransfers;
        this.maxWeight = maximumWeight;
    }
}
