package pr.study.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@RestController
@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "this a msg from HelloWorldController");
        mv.setViewName("helloworld");;
        return mv;
    }
}
