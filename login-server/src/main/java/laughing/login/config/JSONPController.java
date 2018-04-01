package laughing.login.config;

import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

public class JSONPController extends AbstractJsonpResponseBodyAdvice {
    public JSONPController() {
        super("callback", "jsonp");
    }
}
