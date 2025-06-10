package com.lecture.common.exception

interface CodeInterface {
    val code: Int
    val message: String
}

enum class ErrorCode(
    override val code: Int,
    override val message: String
) : CodeInterface {
    AUTH_CONFIG_NOT_FOUND(-100, "auth config not found"),
    FAILED_TO_CALL_CLIENT(-101, "Failed to call client"),
    CALL_RESULT_BODY_NULL(-102, "body is nil"),
    PROVIDER_NOT_FOUND(-103, "provider not found"),
    TOKEN_IS_INVALID(-104, "token invalid"),
    TOKEN_IS_EXPIRED(-105, "token expired"),
    FAILED_TO_INVOKE_IN_LOGGER(-106, "failed to invoke in logger"),
    FAILED_TO_SAVE_DATA(-107, "failed to save data"),
    FAILED_TO_FIND_ACCOUNT(108, "failed to find account"),
    MISS_MATCH_ACOUNT_ULID_AND_USER_ULID(109, "failed to matching ulid"),
    ACCOUNT_BALANCE_IS_NOT_ZERO(110, "account balance is not zero"),
    FAILED_TO_MUTEX_INVOKE(111, "failed to invoke function in mutex"),
    FAILED_TO_GET_LOCK(112,"failed to get lock"),
    ENOUGH_VALUE(113, "enough value"),
    VALUE_MUST_NOT_BE_UNDER_ZERO(114, "value must be under zero"),
    FAILED_TO_SEND_MESSAGE(115, "failed to send message"),
    FAILED_TO_CONNECT_MONGO(116, "failed to connect mongo"),
    FAILED_TO_FIND_MONGO_TEMPLATE(117, "failed to find mongo template"),
    ACCESS_TOKEN_NEED(118, "access token needed"),


}