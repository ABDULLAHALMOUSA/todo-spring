package space.ibrahim.todo.repositories

import org.springframework.data.jpa.repository.JpaRepository
import space.ibrahim.todo.models.Task
import space.ibrahim.todo.models.User

interface UserRepository : JpaRepository<User, Long> {
    fun getByUsernameEquals(username: String): User?
}

interface TaskRepository : JpaRepository<Task, Long> {
    fun getByUserEquals(user: User): List<Task>
    fun getByUserEqualsAndDoneEquals(user: User, done: Boolean): List<Task>
}