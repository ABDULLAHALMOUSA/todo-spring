package space.ibrahim.todo.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import space.ibrahim.todo.models.Task
import space.ibrahim.todo.models.TaskDto
import space.ibrahim.todo.repositories.TaskRepository
import space.ibrahim.todo.repositories.UserRepository
import java.lang.RuntimeException
import javax.transaction.Transactional

@Service
class TaskService {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun getAllTasks(username: String?, done: Boolean? = null): List<TaskDto> {
        if (username == null) throw UsernameNotFoundException("username not found")
        val user = userRepository.getByUsernameEquals(username)
            ?: throw UsernameNotFoundException("username not found")

        return if (done != null) taskRepository.getByUserEqualsAndDoneEquals(user, done).map {
            mapTaskToTaskDto(it)
        }
        else taskRepository.getByUserEquals(user).map { mapTaskToTaskDto(it) }
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

    fun getTask(username: String?, id: Long): TaskDto {
        if (username == null) throw UsernameNotFoundException("username not found")

        val user = userRepository.getByUsernameEquals(username)
            ?: throw UsernameNotFoundException("username not found")

        val task = taskRepository.getByUserEqualsAndIdEquals(user, id) ?: throw TaskDoesNotExist()

        return mapTaskToTaskDto(task)
    }

    fun deleteTask(username: String?, id: Long) {
        if (username == null) throw UsernameNotFoundException("username not found")

        val user = userRepository.getByUsernameEquals(username)
            ?: throw UsernameNotFoundException("username not found")

        val deletedTaskId = taskRepository.deleteTaskByUserEqualsAndIdEquals(user, id)

        if (deletedTaskId <= 0) throw TaskDoesNotExist()
    }

    fun updateTask(username: String?, taskDto: TaskDto, id: Long): TaskDto {
        if (username == null) throw UsernameNotFoundException("username not found")

        val user = userRepository.getByUsernameEquals(username)
            ?: throw UsernameNotFoundException("username not found")
        val task = taskRepository.getByUserEqualsAndIdEquals(user, id) ?: throw TaskDoesNotExist()

        return mapTaskToTaskDto(
            taskRepository.saveAndFlush(
                task.copy(
                    title = taskDto.title,
                    content = taskDto.content,
                    done = taskDto.done
                )
            )
        )
    }

    private fun mapTaskToTaskDto(task: Task): TaskDto = TaskDto(
        id = task.id,
        title = task.title,
        content = task.content,
        done = task.done
    )
}


class TaskDoesNotExist(message: String = "Task does not exist") : RuntimeException(message)
