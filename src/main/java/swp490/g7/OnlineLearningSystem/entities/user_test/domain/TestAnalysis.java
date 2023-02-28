package swp490.g7.OnlineLearningSystem.entities.user_test.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_analysis")
@Data
public class TestAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_analysis")
    private Long testAnalysisId;

    @Column(name = "user_test_id")
    private Long userTestId;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "test_config_type")
    private String testConfigType;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "percent")
    private Double percent;
}
