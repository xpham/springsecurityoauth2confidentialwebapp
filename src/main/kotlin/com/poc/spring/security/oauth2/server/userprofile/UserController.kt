import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController {

    @GetMapping("/oauth/user/me")
    fun profile(): ResponseEntity<UserProfile> {
        //Build some dummy data to return for testing
        val user: User = SecurityContextHolder.getContext().authentication.principal as User

        val profile = UserProfile()
        profile.name = user.username

        return ResponseEntity.ok(profile)
    }
}
