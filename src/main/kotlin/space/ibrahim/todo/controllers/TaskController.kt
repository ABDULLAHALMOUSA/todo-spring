package space.ibrahim.todo.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import space.ibrahim.todo.models.TaskDto
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
}