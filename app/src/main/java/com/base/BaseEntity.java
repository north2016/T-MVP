package com.base;

import java.io.Serializable;

/**
 * Created by baixiaokang on 16/4/26.
 */
public interface BaseEntity {
    class BaseBean implements Serializable {
        public long id;
        public String objectId;
    }
}
