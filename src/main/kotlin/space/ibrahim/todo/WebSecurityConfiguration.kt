package space.ibrahim.todo

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    //TODO: use a better auth mechanism
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .httpBasic()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST, "/api/v1/user")
            .permitAll()
            .anyRequest().hasRole("USER")
            .and()
    }
}