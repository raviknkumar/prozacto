package com.prozacto.prozacto.jwtAuth.security;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prozacto.prozacto.Entity.User.User;
import com.prozacto.prozacto.jwtAuth.AuthConfiguration;
import com.prozacto.prozacto.jwtAuth.CustomEntryPoint;
import com.prozacto.prozacto.jwtAuth.RolesDataDTO;
import com.prozacto.prozacto.jwtAuth.model.Role;
import com.prozacto.prozacto.jwtAuth.service.AuthUserService;
import com.prozacto.prozacto.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.prozacto.prozacto.jwtAuth.security.Constants.SIGN_UP_URL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthConfiguration authConfiguration;

    @Autowired
    CacheService cacheService;

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/h2/**");
        web.ignoring().antMatchers("/appointment/**");
        web.ignoring().antMatchers("/clinic/**");
        web.ignoring().antMatchers("/enrollment/**");
        web.ignoring().antMatchers("/user/info/**");

    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        buildURLMatchers(http);
        // Disable CSRF (cross site request forgery)
        http.cors().and().csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers("/users/login").permitAll()
                .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        // http.exceptionHandling().accessDeniedPage("/users/login");

        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer( jwtTokenProvider, cacheService));

        // Optional, if you want to test the API from a browser
         http.httpBasic();
    }

    @Autowired
    private AuthUserService authUserService;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authentication -> {
            User user = authUserService.search(authentication.getName());
            if(user == null){
                return null;
            }
            if(BCrypt.checkpw(authentication.getCredentials().toString(), user.getPassword())){
                return new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword(), buildRoles(user.getRoles()));
            }
            return null;
        };
    }

   /* @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder);
    }*/

    public static List<GrantedAuthority> buildRoles(List<Role> roles){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(Role role : roles)
            authorityList.add((GrantedAuthority) () -> "ROLE_"+role);
        return authorityList;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    /**
     *
     * @param http
     * Responsible for reading the RolesJson file and store associate API URL with Roles
     *
     */
    private void buildURLMatchers(HttpSecurity http) throws Exception {
        Gson gson = new Gson();

        // Reading the URLRoles Mapping fike
        File file = ResourceUtils.getFile(authConfiguration.getRolesFilePath());
        BufferedReader br = new BufferedReader(
                new FileReader(file));

        Type type = new TypeToken<List<RolesDataDTO>>() {}.getType();
        List<RolesDataDTO> rolesDataDTOList = gson.fromJson(br, type);

        Assert.notNull(rolesDataDTOList, "Got Empty Data from Roles Json File");

        for(RolesDataDTO rolesDataDTO : rolesDataDTOList)
        {
            // Getting the list of roles for One API
            String roleList[] = rolesDataDTO.getRolesList().trim().split("\\s*,\\s*");;

            if(Arrays.asList(roleList).contains("ALL")){
                http.authorizeRequests().antMatchers(rolesDataDTO.getUrl()).permitAll();
            }
            else{
                http.authorizeRequests().antMatchers(rolesDataDTO.getUrl()).hasAnyRole(roleList);
            }
        }
        // Denying rest of the requests
        http.authorizeRequests().antMatchers("/**").permitAll();
    }
}