package com.demcia.eleven.alfred.domain.entity;

import com.demcia.eleven.core.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "africa_task")
public class Task extends BaseEntity {

    @Id
    @Column(name = "id_", nullable = false, length = 100)
    private String id;

    @Column(name = "subject_", nullable = false, length = 200)
    private String subject;

    @Column(name = "description_", length = 1024)
    private String description;

    @Column(name = "director_id_")
    private String directorId;

    @Column(name = "assistant_id_")
    private String assistantId;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "state_")
    private String state;

    @Column(name = "last_process_time_")
    private LocalDateTime lastProcessTime;

}
