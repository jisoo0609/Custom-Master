package JuDBu.custommaster.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/testController")
public class ReviewController {

    @PostMapping("/test")
    public String Create(){

        return "reviewTest 중입니다.";
    }

}
