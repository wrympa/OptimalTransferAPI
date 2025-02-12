package com.service;

import com.model.transferStructure;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class LeftoverHolder {
    private List<transferStructure> leftovers;

    public LeftoverHolder() {
        leftovers = new ArrayList<>();
    }
}
