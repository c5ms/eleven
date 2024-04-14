package com.eleven.access.core;

import java.util.Optional;

public interface TopicContext {

    Optional<Topic> getTopic(String name);

}
