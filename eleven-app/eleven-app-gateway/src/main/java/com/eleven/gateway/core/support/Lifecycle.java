package com.eleven.gateway.core.support;

/**
 * 标记一个有生命周期的类
 * 这个类很重要,几乎系统所有的可运行的对象都是继承自这个类
 * 用来表示一个类可以被启动,启动期间此类可以获取自己的运行和停止状态
 * 这很有用,通常我们用这个方法来保证一个死循环线程的终止(但是你永远都无法保证一个方法在线程结束之后马上结束,他一定会执行完所有的处理指令.)
 * <ul>
 *    <li>初始状态: isRunning => false isStopped => false</li>
 *    <li>start后: isRunning => true  isStopped => false</li>
 *    <li>stop 后: isRunning => false isStopped => true</li>
 * </ui>
 *
 * @author wzc
 */
public interface Lifecycle {

    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 是否处于运行状态
     *
     * @return true 表示运行种
     */
    boolean isRunning();

    /**
     * 是否已经关闭,注意: 没有启动不代表关闭了.只有调用过关闭方法才能算作关闭了.
     * 所以: 如果你要检测是否没有启动,需要使用 !isRunning 来做
     *
     * @return true 表示已经关闭
     */
    boolean isStopped();

}
