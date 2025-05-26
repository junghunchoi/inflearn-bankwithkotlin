package com.lecture.common.exception

class CustomException(
    private val codeInterface: CodeInterface,
    private val additionalMessage : String? = null,
) : RuntimeException( // 상위 클래스 생성자 호출
    // 여기서 상위 클래스 생성자에 전달할 값을 계산
    if(additionalMessage == null) {
        codeInterface.message
    } else {
        "${codeInterface.message} - $additionalMessage"
    }
) {
}

/*
public class CustomException extends RuntimeException {
    private final CodeInterface codeInterface;
    private final String additionalMessage;

    public CustomException(CodeInterface codeInterface, String additionalMessage) {
        super(additionalMessage == null ?
              codeInterface.getMessage() :
              codeInterface.getMessage() + " - " + additionalMessage);

        this.codeInterface = codeInterface;
        this.additionalMessage = additionalMessage;
    }

    public CustomException(CodeInterface codeInterface) {
        this(codeInterface, null);
    }
}

동일한 구문
 */