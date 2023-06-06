package com.eleven.eng.domain;

import com.eleven.core.domain.AbstractDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("eng_paragraph")
@With
@Getter
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Article extends AbstractDomain<Article> {

    @Id
    @Column("id")
    private String id;


}
