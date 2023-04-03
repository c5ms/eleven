package com.demcia.eleven.core.security;

import lombok.Data;

import java.io.Serializable;

/**
 * 凭证信息
 */
@Data
public class Credential implements Serializable {

    private String grantType;

    private String credential;


}