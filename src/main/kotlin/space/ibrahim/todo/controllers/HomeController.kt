package space.ibrahim.todo.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping
    @RequestMapping("/")
    fun getStatus(): Map<String, String> {
        return mutableMapOf<String, String>("app_version" to "1.0.0")
    }
}