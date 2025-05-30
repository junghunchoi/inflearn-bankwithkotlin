package com.lecture.domains.auth.service

import com.lecture.common.exception.CustomException
import com.lecture.common.exception.ErrorCode
import com.lecture.common.jwt.JwtProvider
import com.lecture.common.logging.Logging
import com.lecture.common.transaction.Transactionl
import com.lecture.domains.auth.repository.AuthUserRepository
import com.lecture.interfaces.OAuthServiceInterface
import com.lecture.types.entity.User
import org.slf4j.Logger
import org.springframework.stereotype.Service
import com.github.f4b6a3.ulid.*
import org.apache.tomcat.util.http.parser.Authorization

@Service
class AuthService(
    private val oAuth2Services: Map<String, OAuthServiceInterface>,
    private val jwtProvider: JwtProvider,
    private val logger: Logger = Logging.getLogger(AuthService::class.java),
    private val transaction: Transactionl,
    private val authUserRepository: AuthUserRepository
) {
    fun handleAuth(state: String, code: String): String = Logging.logFor(logger) {
        val provider = state.lowercase()

        val callService = oAuth2Services[provider] ?: throw CustomException(
            ErrorCode.PROVIDER_NOT_FOUND,
            provider
        )

        val accessToken = callService.getToken(code)
        val userInfo = callService.getUserInfo(accessToken.accessToken)
        val token = jwtProvider.createToken(provider, userInfo.email, userInfo.name, userInfo.id)

        val username = (userInfo.name ?: userInfo.email.toString())

        transaction.run {
            val exist = authUserRepository.existsByUsername(username)

            if(exist){
                authUserRepository.updateAccessTokenByUsername(username, token)
            }else{
                val ulid = UlidCreator.getUlid().toString()
                val user = User(ulid, username, token)

                authUserRepository.save(user)

            }
        }

        // logFor 함수의 람다에서 값을 반환하겠다는 명시적인 의미이다.
        return@logFor token
    }

    fun verifyToken(authorization: String) {
        jwtProvider.verifyToken(authorization.replace("Bearer ", ""))
    }
}
