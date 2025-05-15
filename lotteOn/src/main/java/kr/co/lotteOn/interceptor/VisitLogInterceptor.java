package kr.co.lotteOn.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.lotteOn.service.VisitCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class VisitLogInterceptor implements HandlerInterceptor {

    private final VisitCounterService visitCounterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        if (uri.equals("/")) {
            visitCounterService.logVisit(uri);
        }
        return true;
    }
}
