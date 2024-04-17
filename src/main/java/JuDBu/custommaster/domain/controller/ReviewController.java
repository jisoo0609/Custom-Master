package JuDBu.custommaster.domain.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@Slf4j
public class ReviewController {

    @GetMapping("/test")
    public String Create(){
        log.info("done");
        return "review/reviewTest";
    }

}
