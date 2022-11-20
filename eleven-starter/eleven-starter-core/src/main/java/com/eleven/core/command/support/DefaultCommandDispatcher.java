package com.eleven.core.command.support;

import com.eleven.core.command.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultCommandDispatcher implements CommandDispatcher, CommandRegister {

    private final Map<HandlerKey, CommandHandler<?, ?>> holder = new HashMap<>();

    private final List<CommandValidator> commandValidators;

    @Override
    public <C, R> void register(CommandHandler<?, ?> commandHandler, Class<C> commandClass, Class<R> resultClass) {
        this.add(commandHandler, commandClass, resultClass);
    }

    @Override
    public <C, R> R dispatch(@Validated C c, Class<R> resultClass) throws CommandHandleException,CommandValidateException {
        validate(c);

        @SuppressWarnings("unchecked")
        var commandClass = (Class<C>) c.getClass();
        var handler = find(commandClass, resultClass);
        if (handler.isEmpty()) {
            throw new IllegalStateException("no such handler can be found");
        }
        return handler.get().handle(c);
    }

    public void validate(Object... subjects) throws CommandValidateException {
        for (Object subject : subjects) {
            validate(subject);
        }
    }

    private void validate(Object subject) throws CommandValidateException {
        for (CommandValidator commandValidator : commandValidators) {
            if (commandValidator.support(subject)) {
                commandValidator.validate(subject);
            }
        }
    }

    private void add(CommandHandler<?, ?> handler) {
        Type handlerType = handler.getClass().getGenericSuperclass();
        Type commandType = ((ParameterizedType) handlerType).getActualTypeArguments()[0];
        Type resultType = ((ParameterizedType) handlerType).getActualTypeArguments()[1];
        add(handler, commandType, resultType);
    }

    private void add(CommandHandler<?, ?> handler, Type commandType, Type resultType) {
        HandlerKey handlerKey = new HandlerKey(commandType, resultType);
        if (holder.containsKey(handlerKey)) {
            throw new IllegalStateException("more than one handler registered for " + commandType + " and " + resultType);
        }
        holder.put(handlerKey, handler);
    }

    @SuppressWarnings("unchecked")
    private <C, R> Optional<CommandHandler<C, R>> find(Class<C> commandClass, Class<R> resultClass) {
        var key = new HandlerKey(commandClass, resultClass);
        return Optional.ofNullable(holder.get(key))
                .map(hotelHandler -> (CommandHandler<C, R>) hotelHandler);
    }


    private record HandlerKey(Type commandType, Type resultType) {


    }


}
