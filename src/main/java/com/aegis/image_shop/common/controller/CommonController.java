package com.aegis.image_shop.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CommonController {

    @GetMapping("/error/errorCommon")
    public void handleCommonError(){
        log.info("errorCommon");
    }

    @GetMapping("/error/accessError")
    public void accessDenied(Authentication auth, Model model){
        log.info("access Denied: " + auth);
        model.addAttribute("msg", "Access Denited");
    }
}