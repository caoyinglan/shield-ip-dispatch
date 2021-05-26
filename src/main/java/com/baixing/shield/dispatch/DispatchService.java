package com.baixing.shield.dispatch;

import com.baixing.shield.dao.ServiceRedisConfigDao;
import com.baixing.shield.entity.dto.Message;
import com.baixing.shield.entity.po.ServiceRedisConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface DispatchService {

    void dispatch(Message message);

}
