package axsoup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web
        .configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    // Customizing user authentication


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
