package axsoup.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web
        .configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    // Customizing user authentication

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/design", "/orders")
                     .access("hasRole('ROLE_USER')")
                        .antMatchers("/", "/**").access("permitAll")
                .and()
                 .formLogin()
                     .loginPage("/login")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")

                .and()
                    .csrf()
                        .ignoringAntMatchers("/h2-console/**")

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .and()
                    .headers()
                     .frameOptions()
                         .sameOrigin()

//      Disable CSRF support
//                .and()
//                .csrf()
//                .disable()
        ;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

//    //LDAP-backed user store
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth
//                .ldapAuthentication()
//                .userSearchBase("ou=people")
//                .userSearchFilter("(uid={0})")
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("member={0}")
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("passcode")
//
////                .contextSource()
////                .url("ldap://axsoupcloud.com:389/dc=axsoupcloud,dc=com"); // Запрос на удаленный   сервер
//
////                .contextSource()
////                .root("dc=axsoupcloud,dc=com"); //Запрос на встроенный сервис
////                .ldif("classpath:users.ldif"); // Чтобы спринг не копался в classpath можно указать путь к файлу
//
//    }

//    //Customizing user detail queries
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username, password, enabled from Users " +
//                                "where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities " +
//                                "where username=?")
//                .passwordEncoder(new StandardPasswordEncoder("53cr3t");
//    }

//    Spring Security’s cryptography module includes several such implementations:

// BCryptPasswordEncoder—Applies bcrypt strong hashing encryption
// NoOpPasswordEncoder—Applies no encoding
// Pbkdf2PasswordEncoder—Applies PBKDF2 encryption
// SCryptPasswordEncoder—Applies scrypt hashing encryption
// StandardPasswordEncoder—Applies SHA-256 hashing encryption

//    //Defining users in an in-memory user store
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("art")
//                .password("infinity")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("nate")
//                .password("123")
//                .authorities("ROLE_USER");
//    }
}
