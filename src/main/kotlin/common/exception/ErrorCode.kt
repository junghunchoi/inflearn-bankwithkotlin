package com.lecture.common.exception

interface CodeInterface {
    val code: Int
    val message: String
}

enum class ErrorCode(
    override val code: Int,
    override val message: String
) : CodeInterface {
    AUTH_CONFIG_NOT_FOUND(-100, "지원하지 않는 OAuth2 Provider입니다."),
    FAILED_TO_CALL_CLIENT(-101, "failed to call client"),
    CALL_RESULT_BODY_NULL(-102, "call result body is null"),
    PROVIDER_NOT_FOUND(-103, "provider not found"),
    TOKEN_IS_INVALID(-104, "token is invalid"),
    TOKEN_IS_EXPIRED(-105, "token is expired"),
    FAILED_TO_INVOKE_IN_LOGGER(-106, "logger failed "), ;


}