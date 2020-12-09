package space.ibrahim.todo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.sql.Date
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "tasks")
@JsonIgnoreProperties("hibernateLazyInitializer", "handler")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String,
    val done: Boolean,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    @ManyToOne val user: User
)

data class TaskDto(
    val id: Long? = null,
    val title: String,
    val content: String,
    val done: Boolean,
)