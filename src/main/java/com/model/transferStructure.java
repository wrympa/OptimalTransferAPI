package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class transferStructure {
    private final int weight;
    private final int cost;

    @JsonCreator
    public transferStructure(@JsonProperty("weight") int argWeight,
                             @JsonProperty("cost") int argCost) {
        this.weight = argWeight;
        this.cost = argCost;
    }

}
