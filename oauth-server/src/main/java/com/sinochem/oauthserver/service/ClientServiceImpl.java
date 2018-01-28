package com.sinochem.oauthserver.service;

import com.sinochem.oauthserver.entity.Oauth2Client;
import com.sinochem.oauthserver.mapper.Oauth2ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


@Transactional
@Service
public class ClientServiceImpl implements ClientService {
    @Resource
    private Oauth2ClientMapper clientDao;

    @Override
    public Oauth2Client createClient(Oauth2Client client) {

        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        clientDao.insert(client);
        return client;
    }

    @Override
    public Oauth2Client updateClient(Oauth2Client client) {
        clientDao.updateByPrimaryKeySelective(client);
        return client;
    }

    @Override
    public void deleteClient(Long clientId) {
        clientDao.deleteByPrimaryKey(clientId);
    }

    @Override
    public Oauth2Client findOne(Long clientId) {
        return clientDao.selectByPrimaryKey(clientId);
    }

    @Override
    public List<Oauth2Client> findAll() {
        return clientDao.selectAll();
    }

    @Override
    public Oauth2Client findByClientId(String clientId) {
        Oauth2Client oauth2Client = new Oauth2Client();
        oauth2Client.setClientId(clientId);
        return clientDao.selectOne(oauth2Client);
    }

    @Override
    public Oauth2Client findByClientSecret(String clientSecret) {
        Oauth2Client oauth2Client = new Oauth2Client();
        oauth2Client.setClientSecret(clientSecret);
        return clientDao.selectOne(oauth2Client);
    }
}
