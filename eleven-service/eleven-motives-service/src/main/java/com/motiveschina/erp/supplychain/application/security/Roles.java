package com.motiveschina.erp.supplychain.application.security;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Roles {

    String Seller = "seller";
    String approver = "approver";
}
