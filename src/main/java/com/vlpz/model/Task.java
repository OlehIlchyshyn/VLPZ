package com.vlpz.model;

import com.vlpz.model.enums.Complexity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private Complexity complexity;
    private String solutionDiagramJson;
}
