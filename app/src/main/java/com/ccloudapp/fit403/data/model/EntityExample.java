package com.ccloudapp.fit403.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dev on 11/8/17.
 */

@Entity
public class EntityExample {
    @Id
    private Long id;

    @Generated(hash = 1535090073)
    public EntityExample(Long id) {
        this.id = id;
    }

    @Generated(hash = 249155243)
    public EntityExample() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
