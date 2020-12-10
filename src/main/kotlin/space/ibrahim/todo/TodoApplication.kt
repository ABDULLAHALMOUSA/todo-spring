package space.ibrahim.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import javax.sql.DataSource

@SpringBootApplication
class TodoApplication {
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(datasource: DataSource): UserDetailsService {
        return object : JdbcUserDetailsManager(datasource) {
            override fun loadUserAuthorities(username: String?): MutableList<GrantedAuthority> {
                return AuthorityUtils.createAuthorityList("ROLE_USER")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<TodoApplication>(*args)
}
