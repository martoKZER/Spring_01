package org.iesch.ad.jwtdemo.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String key;

}
