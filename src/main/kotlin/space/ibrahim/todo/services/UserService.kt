package space.ibrahim.todo.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.MissingRequiredPropertiesException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import space.ibrahim.todo.models.User
import space.ibrahim.todo.repositories.UserRepository

@Service
class UserService() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bcrypt: PasswordEncoder

    fun createUser(user: User): User {
        if (user.username == null || user.password == null) {
            throw MissingRequiredPropertiesException()
        }

        val existingUser: User? = userRepository.getByUsernameEquals(username = user.username)

        if (existingUser != null) throw UserAlreadyExistsException()

        return userRepository.saveAndFlush(user.copy(password = bcrypt.encode(user.password)))
    }

    fun findUserByUsername(username: String?): User? {
        if (username == null) throw UsernameNotFoundException("Username not found exception")
        return userRepository.getByUsernameEquals(username)

    }
}

class UserAlreadyExistsException(message: String = "User already exists") : Exception(message) {

}
