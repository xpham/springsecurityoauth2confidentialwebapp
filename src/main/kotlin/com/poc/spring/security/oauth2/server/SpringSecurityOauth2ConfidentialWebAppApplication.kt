package com.poc.spring.security.oauth2.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
// The resource displayed in this Authorization Server after login, is the user profile page
// it's not the actual Resource Server that we<re trying to access
// That Resource Server is another instance
@EnableResourceServer
class SpringSecurityOauth2ConfidentialWebAppApplication

fun main(args: Array<String>) {
    runApplication<SpringSecurityOauth2ConfidentialWebAppApplication>(*args)
}
