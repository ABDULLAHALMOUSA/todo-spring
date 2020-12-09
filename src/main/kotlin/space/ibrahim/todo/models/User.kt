package space.ibrahim.todo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException
import javax.persistence.*

@Entity(name = "users")
@JsonIgnoreProperties("hibernateLazyInitializer", "handler", "password")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "username", unique = true)
    val username: String?,
    @Column(name = "password")
    val password: String?,
    @Column(name = "enabled")
    val enabled: Boolean = true
)


data class UserRegistration(
    val username: String? = null,
    val password: String? = null
)