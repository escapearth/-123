package org.open.market.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class IndexRestController {

    @GetMapping("/")
    public void indexSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
