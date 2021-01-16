package pl.solventive.LocalFarmer.LocalFarmerApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import pl.solventive.LocalFarmer.LocalFarmerApi.security.services.LFUserDetailsService;
import pl.solventive.LocalFarmer.LocalFarmerApi.security.services.LFUserPrincipal;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    LFUserDetailsService userDetailsService;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("LocalFarmer")
                .secret(passwordEncoder.encode("VCk1U9eZ"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("read", "write");
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(defaultAccessTokenConverter())
                .userDetailsService(userDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(defaultAccessTokenConverter());
    }

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter() {
        DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
        defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
        return defaultUserAuthenticationConverter;
    }

    @Bean
    public JwtAccessTokenConverter defaultAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                LFUserPrincipal user = (LFUserPrincipal) authentication.getPrincipal();
                final Map<String, Object> additionalInfo = new HashMap<String, Object>();
                additionalInfo.put("userId", user.getUserId());
                ((DefaultOAuth2AccessToken) accessToken)
                        .setAdditionalInformation(additionalInfo);
                accessToken = super.enhance(accessToken, authentication);
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());
                return accessToken;
            }
        };
        ((DefaultAccessTokenConverter) converter.getAccessTokenConverter())
                .setUserTokenConverter(userAuthenticationConverter());
        converter.setSigningKey("VCk1U9eZ");
        return converter;
    }
}
