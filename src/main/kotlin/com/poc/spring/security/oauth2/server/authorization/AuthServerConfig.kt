import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
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
open class AuthServerConfig : AuthorizationServerConfigurerAdapter() {

    @Value("\${user.oauth.clientId}")
    private val CLIENT_ID: String? = null

    @Value("\${user.oauth.clientSecret}")
    private val CLIENT_SECRET: String? = null

    @Value("\${user.oauth.redirectUris}")
    private val REDIRECT_URLS: String? = null

    // definition of this class as a bean in SecurityConfig, otherwise it won't compile
    @Autowired
    private var passwordEncoder: PasswordEncoder? = null

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        /**
         * Spring security oauth exposes two endpoints for checking tokens
         * - oauth/check_token
         * - /oauth/token_key)
         * which are by default protected behind denyAll().
         *
         * tokenKeyAccess() and checkTokenAccess() methods open these endpoints for use.
         */
        security
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients()
    }

    // http://localhost:8080/oauth/authorize?client_id=R2dpxQ3vPrtfgF72&response_type=code&scope=read_profile_info
    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
            .inMemory()
            .withClient(CLIENT_ID).secret(passwordEncoder!!.encode(CLIENT_SECRET))
            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
            .authorities("READ_ONLY_CLIENT")
            .scopes("read_profile_info")
            .autoApprove(false)
            .redirectUris("http://localhost:8081/login")
            .accessTokenValiditySeconds(120)
            .refreshTokenValiditySeconds(240000)
    }
}
