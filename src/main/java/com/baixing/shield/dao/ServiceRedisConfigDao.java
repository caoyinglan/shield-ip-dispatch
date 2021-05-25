package com.baixing.shield.dao;

import com.baixing.shield.entity.po.ServiceRedisConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRedisConfigDao extends JpaRepository<ServiceRedisConfig, Long> {

    ServiceRedisConfig findByService(String service);


}
