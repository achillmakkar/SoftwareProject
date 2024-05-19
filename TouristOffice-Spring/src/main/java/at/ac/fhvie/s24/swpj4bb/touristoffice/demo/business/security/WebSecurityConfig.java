package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.security;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailServiceImpl();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/", "/h2-console/**").permitAll() // Code_Achill_19.05.2024_H2Console_FIX
            .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
        .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
        .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
        .antMatchers("/delete/**").hasAuthority("ADMIN")
        .antMatchers("/images/**","/css/**","/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/loginpage")
        .defaultSuccessUrl("/index",true)
        .permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/403")
            .and() // Code_Achill_19.05.2024_H2Console_FIX
            .csrf().disable() // Code_Achill_19.05.2024_H2Console_FIX
            .headers().frameOptions().disable(); // Code_Achill_19.05.2024_H2Console_FIX

    ;
  }
}