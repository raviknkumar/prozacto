package com.prozacto.prozacto.Entity;

import java.io.Serializable;
import java.util.Date;

public interface GenericEntity<PK extends Serializable> extends Serializable {

    PK getId();

    void setId(PK id);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);

    int getCreatedBy();

    void setCreatedBy(int createdBy);

    int getUpdatedBy();

    void setUpdatedBy(int updatedBy);

    boolean getDeleted() ;

    void setDeleted(boolean deleted) ;

}