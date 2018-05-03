package net.formula97.springapp.skeltonapp.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Sample implementation of domain's base class.<br />
 * The created/update timestamp are automatically updated.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractDomain implements Serializable {

    // TODO : 更新日時、作成日時のカラム物理名は、別途決定しているものに書き換えること

    /**
     * Column physical name of "Updated timestamp".
     */
    public static final String COLUMN_UPDATE_TIME = "update_time";
    /**
     * Column physical name of "Created timestamp".
     */
    public static final String COLUMN_CREATED_TIME = "created_time";

    /**
     * Serialized UID.
     */
    private static final long serialVersionUID = 9206310086649313823L;

    /**
     * Timestamp when record was updated.
     */
    @Column(name = COLUMN_UPDATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * Timestamp when record was created.
     */
    @Column(name = COLUMN_CREATED_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     * Executed when record is inserted.
     */
    @PrePersist
    public void onPrePersist() {
        this.createdTime = new Date();
        this.updateTime = new Date();
    }

    /**
     * Executed when record is updated.
     */
    @PreUpdate
    public void onPreUpdate() {
        this.updateTime = new Date();
    }
}
