package com.sinochem.oauthclient.controller;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    //oauth2 authc code参数名
    private String authcCodeParam = "code";
    //客户端id
    @Value("${oauth.clientId}")
    private String clientId;
    //三方登陆系统地址
    @Value("${oauth.loginUrl}")
    private String loginUrl;
    //服务器端登录成功/失败后重定向到的客户端地址
    @Value("${oauth.redirectUrl}")
    private String redirectUrl;
    //oauth2服务器响应类型
    private String responseType = "code";
    @Value("${oauth.clientSecret}")
    private String clientSecret;
    @Value("${oauth.accessTokenUrl}")
    private String accessTokenUrl;
    @Value("${oauth.userInfoUrl}")
    private String userInfoUrl;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("oauth_url", oauthUrl());
        return "index";
    }

    @RequestMapping("/oauthResult")
    public String oauthSuccess(String code, Model model) throws OAuthProblemException, OAuthSystemException {
        model.addAttribute("username", extractUsername(code));
        return "oauthSuccess";
    }

    private String oauthUrl() {
        return loginUrl + "?response_type=" + responseType + "&client_id=" + clientId + "&redirect_uri=" + redirectUrl;
    }

    private String extractUsername(String code) throws OAuthSystemException, OAuthProblemException {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(accessTokenUrl)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setCode(code)
                .setRedirectURI(redirectUrl)
                .buildQueryMessage();

        OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();

        OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(userInfoUrl)
                .setAccessToken(accessToken).buildQueryMessage();

        OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        return resourceResponse.getBody();
    }
}
