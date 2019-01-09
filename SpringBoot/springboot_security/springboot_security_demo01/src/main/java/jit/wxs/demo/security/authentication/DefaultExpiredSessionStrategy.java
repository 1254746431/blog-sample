package jit.wxs.demo.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认 Session 过期处理
 * @author jitwxs
 * @since 2019/1/8 23:40
 */
public class DefaultExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    private ObjectMapper objectMapper = new ObjectMapper();

//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 根据需要返回 Url 或者 Json
     * @author jitwxs
     * @since 2018/11/29 18:46
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>(16);
        map.put("status", false);
        map.put("msg", "您已被踢出服务器，可能在其他地方登录，您被迫下线。" + event.getSessionInformation().getLastRequest());
        // Map -> Json
        String json = objectMapper.writeValueAsString(map);

        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(json);

        // 如果是跳转html页面，url代表跳转的地址
//         redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "url");
    }
}