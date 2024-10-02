package com.eleven.access.admin.support;

import com.cnetong.common.query.domain.Page;

public interface MessageSearch {

    Page<MessageSearchResult> search(String partition, MessageSearchFilter filter);

}
