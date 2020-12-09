package space.ibrahim.todo.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import space.ibrahim.todo.models.TaskDto
import space.ibrahim.todo.services.TaskDoesNotExist
import space.ibrahim.todo.services.TaskService

@RestController
@RequestMapping("/api/v1/task")
class TaskController {

    @Autowired
    lateinit var taskService: TaskService

    @GetMapping
    fun getTasks(@RequestParam(value = "done", required = false) done: Boolean? = null): ResponseEntity<Any> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks(authentication.name, done = done))
    }

    @PostMapping
    fun addTask(@RequestBody task: TaskDto): ResponseEntity<Any> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(task, authentication.name))
    }

    @GetMapping("{id}")
    fun getTask(@PathVariable id: Long): ResponseEntity<Any> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity.status(HttpStatus.OK).body(taskService.getTask(authentication.name, id))
        } catch (e: TaskDoesNotExist) {
            ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    @DeleteMapping("{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Any> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return try {
            taskService.deleteTask(authentication.name, id)
            ResponseEntity.ok().build()
        } catch (e: TaskDoesNotExist) {
            ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    @PutMapping("{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody taskDto: TaskDto): ResponseEntity<Any> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return try {
            ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(authentication.name, taskDto, id))
        } catch (e: TaskDoesNotExist) {
            ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }
}