package com.eleven.core.cluster;

import lombok.Data;

@Data
public class ClusterMember {
    private String id;
    private String address;
    private Integer port;
}
