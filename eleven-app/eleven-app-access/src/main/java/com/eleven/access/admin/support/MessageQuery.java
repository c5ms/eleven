package com.eleven.access.admin.support;

import com.cnetong.access.core.MessageLog;
import com.cnetong.common.query.domain.Page;

public interface MessageQuery {

    Page<MessageLog> query(String partition, MessageQueryFilter filter);

}
