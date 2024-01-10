package tms.cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource, UserRepository userRepository) {
        ArrayList<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(user -> userList.add(user));
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        userList.forEach((n) -> users.createUser(n));
        return users;
    }

 */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserRepositoryUserDetailsService myUserService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(myUserService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/login","/images/**", "/login.css").permitAll()
                                .requestMatchers("/", "/images/**", "/styles.css", "/fragments/header", "/navbar.css").permitAll()
                                .requestMatchers("/register").permitAll()
                                .anyRequest().authenticated())
               .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll())
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/"));
               //.csrf().disable();
        return http.build();
    }
}