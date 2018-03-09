package com.hklh8.properties;

/**
 * Created by GouBo on 2017/12/27.
 */
public class OAuth2Properties {

    private String jwtSigningKey = "ad8ac0ec276c8b1089ff83065c86af90";

    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
