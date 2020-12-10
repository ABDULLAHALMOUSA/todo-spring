package space.ibrahim.todo.exceptions

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ApiError(
    val status: HttpStatus,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyyThh:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val message: String = "Unexpected Error",
)