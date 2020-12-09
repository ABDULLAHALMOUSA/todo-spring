package space.ibrahim.todo

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import kotlin.Throws
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.http.HttpMethod
import java.lang.Exception

@Configuration
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests {
            it.mvcMatchers(HttpMethod.POST, "/api/v1/user").permitAll().
                    anyRequest().hasRole("USER")
        }.httpBasic()
        http.csrf().disable()
    }
}