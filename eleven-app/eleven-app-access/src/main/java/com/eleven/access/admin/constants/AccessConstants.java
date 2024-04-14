package com.eleven.access.admin.constants;

public final class AccessConstants {
    public final static String VERSION_KEY_CONNECTIONS = "access_connection_version";
    public final static String VERSION_KEY_PRODUCERS = "access_producer_version";
    public final static String VERSION_KEY_LISTENERS = "access_listener_version";
    public final static String VERSION_KEY_TOPICS = "access_topic_version";
    public final static String VERSION_KEY_PARTITION = "access_partition_version";

    public static final String VERSION_KEY_PRODUCER_FORMAT = "access_producer:%s:version";
    public static final String VERSION_KEY_LISTENER_FORMAT = "access_listener:%s:version";
    public static final String VERSION_KEY_CONNECTION_FORMAT = "access_connection:%s:version";
    public static final String VERSION_KEY_TOPIC_FORMAT = "access_topic:%s:version";

    public static String getProducerKey(String id) {
        return String.format(VERSION_KEY_PRODUCER_FORMAT, id);
    }

    public static String getListenerKey(String id) {
        return String.format(VERSION_KEY_LISTENER_FORMAT, id);
    }

    public static String getResourceKey(String id) {
        return String.format(VERSION_KEY_CONNECTION_FORMAT, id);
    }

    public static String getTopicKey(String id) {
        return String.format(VERSION_KEY_TOPIC_FORMAT, id);
    }

}
