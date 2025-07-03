package INTRANET_ZEPHYRA.demo.configuracion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import INTRANET_ZEPHYRA.demo.Servicios.UsuarioServicio;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UsuarioServicio usuarioServicio) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioServicio);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
    .requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll()
    
    .requestMatchers("/producto/**", "/usuario/**", "/reporte/**", "/RealizarCompra", "/productos/**").hasRole("Administrador")
    
    .requestMatchers("/inicio", "/Ventas", "/registrar", "/ventas/detalle/**", "/GestionVentas").hasAnyRole("Administrador", "Vendedor")

    .anyRequest().authenticated()

            .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/inicio", true)
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout").permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/accessDenied");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.authenticationProvider(authenticationProvider);
        return auth.build();
    }
}