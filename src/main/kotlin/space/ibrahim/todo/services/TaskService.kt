package space.ibrahim.todo.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import space.ibrahim.todo.models.Task
import space.ibrahim.todo.models.TaskDto
import space.ibrahim.todo.repositories.TaskRepository
import space.ibrahim.todo.repositories.UserRepository

@Service
class TaskService {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun getAllTasks(username: String?, done: Boolean? = null): List<Task> {
        if (username == null) throw UsernameNotFoundException("username not found")
        val user = userRepository.getByUsernameEquals(username)
            ?: throw UsernameNotFoundException("username not found")

        return if (done != null) taskRepository.getByUserEqualsAndDoneEquals(user, done)
        else taskRepository.getByUserEquals(user)
    }

    fun addTask(taskDto: TaskDto, username: String?): Task {
        if (username == null) throw UsernameNotFoundException("username not found")
        val user = userRepository.getByUsernameEquals(username)
            ?: throw UsernameNotFoundException("username not found")

        val task = Task(
            title = taskDto.title,
            content = taskDto.content,
            done = taskDto.done,
            user = user
        )

        return taskRepository.saveAndFlush(task)
    }

}
