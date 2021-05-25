package com.baixing.shield.enums;

public interface Constants {

    //避免强代码依赖

    String CONSUMER_TOPIC = "shield-ip-dispatch";

    String CONSUMER_GROUP_ID = "shield-ip-dispatch-cc";

    String CACHE_IP_PREFIX = "shield:";

    Long DEFAULT_EXPIRED_TIME = 3600L;
}
