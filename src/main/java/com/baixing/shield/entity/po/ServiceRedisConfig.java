package com.baixing.shield.entity.po;

import com.baixing.ms.springtime.modules.jpa.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Table(name = "service_redis_config")
@Entity
@Where(clause = "is_deleted = 0")        //筛选出未被删除的
public class ServiceRedisConfig extends BaseEntity {
    //父类已经有id了
    private String bizIdentity;
    private String redisHost;
    private Integer database;
    private Integer redisPort;
}
