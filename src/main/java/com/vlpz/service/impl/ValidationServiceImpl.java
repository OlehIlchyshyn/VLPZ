package com.vlpz.service.impl;

import com.vlpz.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean areDiagramsEqual(String validDiagram, String submittedResult) {
        return new Random().nextBoolean();
    }
}
