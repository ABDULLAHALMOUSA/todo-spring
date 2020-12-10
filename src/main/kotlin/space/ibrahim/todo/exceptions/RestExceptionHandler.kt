package space.ibrahim.todo.exceptions

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.env.MissingRequiredPropertiesException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(e: EntityNotFoundException): ResponseEntity<Any> {
        val apiError = ApiError(
            status = HttpStatus.NOT_FOUND,
            message = e.message ?: "Entity not found",
        )

        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(e: UserAlreadyExistsException): ResponseEntity<Any> {
        val apiError = ApiError(
            status = HttpStatus.CONFLICT,
            message = e.message ?: "User already exists",
        )

        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(MissingRequiredPropertiesException::class)
    fun handleMissingRequiredPropertiesException(e: MissingRequiredPropertiesException): ResponseEntity<Any> {
        val apiError = ApiError(
            status = HttpStatus.BAD_REQUEST,
            message = e.message,
        )

        return buildResponseEntity(apiError)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val error = "Malformed request, please enter all required parameters"
        return buildResponseEntity(
            apiError = ApiError(
                status = HttpStatus.BAD_REQUEST,
                message = error
            )
        )
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity.status(apiError.status).body(apiError)
    }
}
