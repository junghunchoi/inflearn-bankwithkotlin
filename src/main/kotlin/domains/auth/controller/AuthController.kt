package com.lecture.domains.auth.controller

import com.lecture.domains.auth.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.URL

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @RequestMapping("/login")
    fun login(state: String, code: String): String {
        return authService.handleAuth(state, code)
    }

    @GetMapping("/callback")
    fun callback(@RequestParam("state") state: String,
                 @RequestParam("code") code: String,
                 response: HttpServletResponse
    ) : ResponseEntity<Map<String, String>> {
        val token = authService.handleAuth(state, code)
        response.addCookie(
            Cookie("authToken", token).apply {
                isHttpOnly = true
                path = "/callback"
                maxAge = 60 * 60 * 24 // 1 day
            }
        )
        //TODO -> create url은 config로 관리하기
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000")).build()
    }

    @GetMapping("/verifty-token")
    fun verifyToken(@RequestParam("authorization") authHeader: String): ResponseEntity<Void> {
        authService.verifyToken(authHeader)
        return ResponseEntity.ok().build()
    }
}
