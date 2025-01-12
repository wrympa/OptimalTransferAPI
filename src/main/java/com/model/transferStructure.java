package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class transferStructure {
    private final int weight;
    private final int cost;

    @JsonCreator
    public transferStructure(@JsonProperty("weight") int arg_weight,
                             @JsonProperty("cost") int arg_cost) {
        this.weight = arg_weight;
        this.cost = arg_cost;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
