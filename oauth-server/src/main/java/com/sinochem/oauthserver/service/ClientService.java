package com.sinochem.oauthserver.service;

import com.sinochem.oauthserver.entity.Oauth2Client;

import java.util.List;

public interface ClientService {

    Oauth2Client createClient(Oauth2Client client);
    Oauth2Client updateClient(Oauth2Client client);
    void deleteClient(Long clientId);

    Oauth2Client findOne(Long clientId);

    List<Oauth2Client> findAll();

    Oauth2Client findByClientId(String clientId);
    Oauth2Client findByClientSecret(String clientSecret);
}
