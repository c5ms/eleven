package com.eleven.access.core.support;

import com.cnetong.access.core.AbstractMessage;
import com.cnetong.access.core.MessageException;

public abstract class TransactionalMessage extends AbstractMessage {

    /**
     * 确认消费消息
     *
     * @throws MessageException 消费消息的时候可能产生异常
     */
    public abstract void acknowledge() throws MessageException;

    /**
     * 拒绝消息的方法
     *
     * @throws MessageException 拒绝消息期间可能产生异常
     */
    public abstract void reject() throws MessageException;

}
