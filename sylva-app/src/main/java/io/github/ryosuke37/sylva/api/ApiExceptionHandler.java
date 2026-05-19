package io.github.ryosuke37.sylva.api;

import io.github.ryosuke37.sylva.api.error.ApiError;
import io.github.ryosuke37.sylva.service.exception.PostNotFoundException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.github.ryosuke37.sylva.common.DebugModeChecker.isDebug;

@ControllerAdvice(basePackages = "io.github.ryosuke37.sylva.api")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    MessageSource messageSource;

    @Autowired
    ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private final Map<Class<? extends Exception>, String> messageKeyMappings =
            Collections.unmodifiableMap(new LinkedHashMap<>() {{
                // getMessageで得られるメッセージとは異なるメッセージをユーザーに見せたい場合は、ここでマッピングを行う。
                // 派生クラスをすべてマッピングする必要はなく、親クラスがキーとして存在していればそこにヒットする。
                // 上に定義するほど優先順位が高くなる。
                // 本来はセキュリティの観点から、Springが吐くExceptionはすべてメッセージを設定するべきだが、本PJは学習用のため行わない。
                // 理論上、本Mapの末尾に対して"Exception"に対するメッセージを設定することで、すべてのメッセージをもれなく設定できる。
                put(MethodArgumentNotValidException.class, "method.argument.not.valid.exception.message");
                put(ServletRequestBindingException.class, "servlet.request.binding.exception.message");
            }});

    private final List<Class<? extends Exception>> rawMessageExceptions =
            List.of(
                    // getMessageで得られるメッセージをそのままユーザーに見せたいExceptionは、ここで列挙する。
                    PostNotFoundException.class
            );

    private String resolveMessage(Exception exception, WebRequest request) {
        if (isDebug()) {
            return exception.getMessage();
        }

        Class<?> exClass = exception.getClass();

        for (Map.Entry<Class<? extends Exception>, String> entry : messageKeyMappings.entrySet()) {
            if (entry.getKey().isAssignableFrom(exClass)) {
                return messageSource.getMessage(entry.getValue(), null, request.getLocale());
            }
        }

        for (Class<? extends Exception> clazz : rawMessageExceptions) {
            if (clazz.isAssignableFrom(exClass)) {
                return exception.getMessage();
            }
        }

        return messageSource.getMessage("api.error.default.message", null, request.getLocale());
    }

    private ApiError createApiError(Exception exception, WebRequest request) {
        ApiError apiError = new ApiError();

        String message = resolveMessage(exception, request);
        apiError.setMessage(message);

        String documentationUrl = messageSource.getMessage("api.document.url", null, request.getLocale());
        apiError.setDocumentationUrl(documentationUrl);

        return apiError;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @Nonnull Exception exception,
            @Nullable Object body,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request
    ) {
        ApiError apiError = createApiError(exception, request);
        return super.handleExceptionInternal(exception, apiError, headers, status, request);
    }

    // バリデーションエラー発生時はhandlerの挙動をカスタムしたいため、ResponseEntityExceptionHandlerが持つ
    // Exceptionに対するhandleメソッドをOverrideする
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @Nonnull MethodArgumentNotValidException exception,
            @Nonnull HttpHeaders headers,
            @Nonnull HttpStatusCode status,
            @Nonnull WebRequest request
    ) {
        ApiError apiError = createApiError(exception, request);
        exception.getBindingResult().getGlobalErrors()
                .forEach(e -> apiError.addDetail(
                        e.getObjectName(),
                        messageSource.getMessage(e, request.getLocale())
                ));
        exception.getBindingResult().getFieldErrors()
                .forEach(e -> apiError.addDetail(
                        e.getField(),
                        messageSource.getMessage(e, request.getLocale())
                ));
        return super.handleExceptionInternal(exception, apiError, headers, status, request);
    }

    // ResponseEntityExceptionHandlerが対応していないExceptionに対しては、
    // @ExceptionHandlerを付与したメソッドを作成することで対応できる。
    // 個別にhandleメソッドを定義していないExceptionはhandleSystemExceptionにゆだねられる。
    @ExceptionHandler
    protected ResponseEntity<Object> handleSystemException(
            Exception exception,
            WebRequest request
    ) {
        ApiError apiError = createApiError(exception, request);
        return super.handleExceptionInternal(exception, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handlePostNotFoundException(
            PostNotFoundException exception,
            WebRequest request
    ) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
