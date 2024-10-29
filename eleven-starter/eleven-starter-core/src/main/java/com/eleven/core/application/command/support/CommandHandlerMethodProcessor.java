
package com.eleven.core.application.command.support;

import com.eleven.core.application.command.CommandEndpoint;
import com.eleven.core.application.command.CommandRegister;
import jakarta.annotation.Nonnull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CommandHandlerMethodProcessor implements SmartInitializingSingleton, ApplicationContextAware, BeanFactoryPostProcessor {

    protected final Log logger = LogFactory.getLog(getClass());

    private ConfigurableApplicationContext applicationContext;

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        Map<String, EventListenerFactory> beans = beanFactory.getBeansOfType(EventListenerFactory.class, false, false);
        List<EventListenerFactory> factories = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(factories);
    }

    @Override
    public void afterSingletonsInstantiated() {
        ConfigurableListableBeanFactory beanFactory = this.beanFactory;
        Assert.state(this.beanFactory != null, "No ConfigurableListableBeanFactory set");
        String[] beanNames = beanFactory.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            if (!ScopedProxyUtils.isScopedTarget(beanName)) {
                Class<?> type = null;
                try {
                    type = AutoProxyUtils.determineTargetClass(beanFactory, beanName);
                } catch (Throwable ex) {
                    // An unresolvable bean type, probably from a lazy bean - let's ignore it.
                    if (logger.isDebugEnabled()) {
                        logger.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
                    }
                }
                if (type != null) {
                    try {
                        processBean(beanName, type);
                    } catch (Throwable ex) {
                        throw new BeanInitializationException("Failed to process @CommandEndpoint " + "annotation on bean with name '" + beanName + "': " + ex.getMessage(), ex);
                    }
                }
            }
        }
    }

    private void processBean(final String beanName, final Class<?> targetType) {
        var annotatedMethods = MethodIntrospector.selectMethods(targetType, (MethodIntrospector.MetadataLookup<CommandEndpoint>) method -> AnnotatedElementUtils.findMergedAnnotation(method, CommandEndpoint.class));
        if (CollectionUtils.isEmpty(annotatedMethods)) {
            return;
        }
        var context = this.applicationContext;
        var commandRegister = applicationContext.getBean(CommandRegister.class);
        var bean = context.getBean(beanName, targetType);
        for (Method method : annotatedMethods.keySet()) {
            if (method.getParameterTypes().length == 0) {
                continue;
            }
            var returnType = method.getReturnType();
            var commandType = method.getParameterTypes()[0];
            var handler = new MethodCommandHandlerAdapter(bean, method);
            commandRegister.register(handler, commandType, returnType);
        }
    }

}
