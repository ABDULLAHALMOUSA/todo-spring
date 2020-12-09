package space.ibrahim.todo.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.MissingRequiredPropertiesException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import space.ibrahim.todo.models.User
import space.ibrahim.todo.models.UserRegistration
import space.ibrahim.todo.repositories.UserRepository

@Service
class UserService() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bcrypt: PasswordEncoder

    fun createUser(user: UserRegistration): User {
        if (user.username.isNullOrBlank() || user.password.isNullOrBlank()) {
            throw MissingRequiredPropertiesException()
        }

        val existingUser: User? = userRepository.getByUsernameEquals(username = user.username)

        if (existingUser != null) throw UserAlreadyExistsException()

        val newUser = User(
            username = user.username,
            password = bcrypt.encode(user.password)
        )
        return userRepository.saveAndFlush(newUser)
    }

    fun findUserByUsername(username: String?): User? {
        if (username == null) throw UsernameNotFoundException("Username not found exception")
        return userRepository.getByUsernameEquals(username)

    }
}

class UserAlreadyExistsException(message: String = "User already exists") : Exception(message) {

}
