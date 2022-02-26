package com.poc.spring.security.oauth2.server.authorization

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer


/*
 *  The AuthServerConfig class is the class that will create and return our JSON web tokens when the client properly authenticates.
 */
@Configuration
@EnableAuthorizationServer
class AuthServerConfig(private val passwordEncoder: PasswordEncoder) : AuthorizationServerConfigurerAdapter() {
    @Value("\${user.oauth.clientId}")
    private val ClientID: String? = null

    @Value("\${user.oauth.clientSecret}")
    private val ClientSecret: String? = null

    @Value("\${user.oauth.redirectUris}")
    private val RedirectURLs: String? = null

    @Throws(Exception::class)
    override fun configure(
        oauthServer: AuthorizationServerSecurityConfigurer
    ) {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient(ClientID)
            .secret(passwordEncoder.encode(ClientSecret))
            .authorizedGrantTypes("authorization_code")
            .scopes("user_info")
            .autoApprove(true)
            .redirectUris(RedirectURLs)
    }
}
