package swp490.g7.OnlineLearningSystem.entities.packages.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "package")
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "package_id")
    Long packageId;

    @Column(name = "title")
    String title;

    @Column(name = "thumbnail_url")
    String thumbnailUrl;

    @Column(name = "subject_id")
    Long subjectId;

    @Column(name = "term_id")
    Long termId;

    @Column(name = "is_combo")
    Boolean isCombo;

    @Column(name = "duration")
    String duration;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "category")
    String category;
}
