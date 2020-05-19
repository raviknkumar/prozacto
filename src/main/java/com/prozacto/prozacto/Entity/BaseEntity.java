package com.prozacto.prozacto.Entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> implements GenericEntity<PK>, Cloneable {

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column (name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column (name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column (name = "created_by")
    private int createdBy;

    @Column (name = "updated_by")
    private int updatedBy;

    @Column(name = "deleted")
    private boolean deleted;
}

