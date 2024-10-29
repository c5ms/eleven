package com.eleven.core.data.support;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.domain.DomainHelper;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.concurrent.atomic.LongAdder;

@Table(name = "infra_serial")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class Serial extends AbstractEntity implements Serializable {

    @Transient
    private final LongAdder usedTimes = new LongAdder();

    @Id
    private String id;

    @Embedded.Empty
    private Key key;

    @Column("start_val")
    private Integer startVal;

    @Setter
    @Column("step")
    private Integer step;

    @Version
    @Column("version")
    private Integer version;

    public boolean isExhausted() {
        return usedTimes.longValue() >= step;
    }

    public void refresh() {
        this.startVal = this.startVal + this.step;
    }

    public long nextVal() {
        usedTimes.increment();
        return this.startVal + usedTimes.longValue();
    }

    public static Key keyOf(String group, String item) {
        return new Key(group, item);
    }

    public static Serial create(Key key, int startVal, int step) {
        var id = DomainHelper.nextId();
        return new Serial(id, key, startVal, step, 0);
    }


    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    public static class Key {

        @Column("group")
        @InsertOnlyProperty
        private String group;

        @Column("name")
        @InsertOnlyProperty
        private String name;
    }
}
