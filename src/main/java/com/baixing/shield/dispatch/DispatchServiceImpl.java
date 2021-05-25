package com.baixing.shield.dispatch;

import com.baixing.ms.springtime.modules.utils.Func;
import com.baixing.ms.springtime.modules.utils.mapper.JsonMapper;
import com.baixing.shield.dao.ServiceRedisConfigDao;
import com.baixing.shield.entity.dto.Message;
import com.baixing.shield.entity.po.ServiceRedisConfig;
import com.baixing.shield.enums.MessageType;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.baixing.shield.enums.Constants.CACHE_IP_PREFIX;
import static com.baixing.shield.enums.Constants.DEFAULT_EXPIRED_TIME;

@Service
@Slf4j
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService{

    private final ServiceRedisConfigDao redisConfigDao;
    private final StringRedisTemplate redisTemplate;

    private Map<String, StringRedisTemplate> redisTemplateMap = Maps.newHashMap();

    //服务启动，内存自动加载
    @PostConstruct
    public void init(){
        log.info("=========RedisTemplateMap init==========");
        List<ServiceRedisConfig> redisConfigs = redisConfigDao.findAll();
        for (ServiceRedisConfig redisConfig : redisConfigs) {
            String service = redisConfig.getService();
            String host = redisConfig.getRedisHost();
            Integer port = redisConfig.getRedisPort();
            Integer database = redisConfig.getDatabase();
            StringRedisTemplate redisTemplate = getStringRedisTemplate(host,port,database);
            redisTemplateMap.put(service, redisTemplate);
        }
    }

    //设置redis不同host和port
    private StringRedisTemplate getStringRedisTemplate(String host, int port, int database) {
        // Build factory object
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        LettucePoolingClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.defaultConfiguration();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, clientConfiguration);
        // Set the redis database used
        factory.setDatabase(database);
        // Reinitialize the factory
        factory.afterPropertiesSet();
        return new StringRedisTemplate(factory);
    }


    @Override
    public void dispatch(Message message) {
        log.info("dispatch message : {}", message);
        String service = message.getService();
        StringRedisTemplate redisTemplate = redisTemplateMap.get(service);
        ServiceRedisConfig redisConfig = redisConfigDao.findByService(service);
        String type = message.getType();
        MessageType messageType = MessageType.valueOf(type);
        String ip = message.getIp();
        Long expiredTime = message.getExpiredTime();
        expiredTime = Func.isNull(expiredTime) ? DEFAULT_EXPIRED_TIME : expiredTime;
        String messageJson = JsonMapper.INSTANCE.toJson(message);
        String key = CACHE_IP_PREFIX + ip;

        switch (messageType){
            case auto:
                String preJson = redisTemplate.opsForValue().get(key);
                if (Func.isNotBlank(preJson)) {
                    Message preMessage = JsonMapper.INSTANCE.fromJson(preJson, Message.class);
                    if(Func.equals(preMessage.getType(),MessageType.auto.name()) && message.getScore() >= preMessage.getScore()){
                        redisTemplate.opsForValue().set(key, messageJson, expiredTime);
                    }
                }else{
                    redisTemplate.opsForValue().set(key, messageJson, expiredTime);
                }
                break;
            case manual:
                redisTemplate.opsForValue().set(key, messageJson, expiredTime);
                break;
            case delete:
                redisTemplate.delete(key);
                break;
        }

    }
}
