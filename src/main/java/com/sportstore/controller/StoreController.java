package com.sportstore.controller;

import com.sportstore.entity.Shoes;
import com.sportstore.service.OrderService;
import com.sportstore.service.ShoesService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreController {
    @Autowired
    private ShoesService shoesService;
    @Autowired
    private OrderService orderService;
    
    @RequestMapping("/")
    public String index(Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            model.addAttribute("login_btn", "/logout");
            model.addAttribute("login_text", "выход");
            String userInfo = "Здравствуйте，" + subject.getPrincipal().toString();
            if (subject.hasRole("vip")) userInfo += "（VIPпользователь）"; else userInfo += "（Обычный пользователь）";
            model.addAttribute("user_info", userInfo);
        } else {
            model.addAttribute("login_btn", "/login");
            model.addAttribute("login_text", "войти");
        }
        return "index";
    }
    
    @RequestMapping("/menu")
    public String menu(Model model) {
        Subject subject = SecurityUtils.getSubject();
        List<Shoes> allShoes = deepCopy(shoesService.findAll());
        if (subject.isAuthenticated() || subject.isRemembered()) {
            model.addAttribute("login_btn", "/logout");
            model.addAttribute("login_text", "выход");
            String userInfo = "Здравствуйте，" + subject.getPrincipal().toString();
            if (subject.hasRole("vip")) userInfo += "（VIPпользователь）"; else userInfo += "（Обычный пользователь）";
            model.addAttribute("user_info", userInfo);
            model.addAttribute("login_show", "display:none;");
            if (!subject.hasRole("vip")) model.addAttribute("vip_hidden", "display:none;");
            else {
                for (int i = 0; i < 9; i++) {
                    if (allShoes.get(i).getIsvip())
                        allShoes.get(i).setPrice(String.format("%.2f", Double.parseDouble(allShoes.get(i).getPrice()) * 0.95));
                }
            }
        } else {
            model.addAttribute("login_btn", "/login");
            model.addAttribute("login_text", "войти");
            model.addAttribute("pay_show", "display:none;");
            model.addAttribute("vip_hidden", "display:none;");
        }
        model.addAttribute("shoes", allShoes);
        return "menu";
    }

    @RequestMapping("/cuisine_detail/{name}")
    public String detail(@PathVariable String name, Model model) {
        int id = name.charAt(name.length() - 1) - '0';
        List<Shoes> allShoes = deepCopy(shoesService.findAll());
        Shoes curShoes = allShoes.get(id - 1);
        String curDiscount = "нет";
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            model.addAttribute("login_btn", "/logout");
            model.addAttribute("login_text", "выход");
            String userInfo = "Здравствуйте，" + subject.getPrincipal().toString();
            if (subject.hasRole("vip")) userInfo += "（VIPпользователь）"; else userInfo += "（Обычный пользователь）";
            model.addAttribute("user_info", userInfo);
            if (!subject.hasRole("vip")) model.addAttribute("vip_hidden", "display:none;");
            else {
                if (curShoes.getIsvip()) {
                    curShoes.setPrice(String.format("%.2f", Double.parseDouble(curShoes.getPrice()) * 0.95));
                    curDiscount = "9.5%";
                }
            }
        } else {
            model.addAttribute("login_btn", "/login");
            model.addAttribute("login_text", "войти");
            model.addAttribute("vip_hidden", "display:none;");
        }
        model.addAttribute("all_shoes", allShoes);
        model.addAttribute("shoes_detail", curShoes);
        model.addAttribute("shoes_pic", "n" + id + ".jpg");
        model.addAttribute("discount", curDiscount);
        return "detail";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@RequestParam String amount) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            String curName = subject.getPrincipal().toString();
            Long id = orderService.submitOrder(curName, amount);
            return "redirect:/order/" + id;
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/order/{str}")
    public String order(@PathVariable String str, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            if (!isNumber(str)) return "redirect:/";
            String userInfo = "Здравствуйте，" + subject.getPrincipal().toString();
            if (subject.hasRole("vip")) userInfo += "（VIPпользователь）"; else userInfo += "（Обычный пользователь）";
            model.addAttribute("user_info", userInfo);
            return "order";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login_GET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login_POST(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        if (remember != null) token.setRememberMe(true);
        String msg = null;

        try{
            subject.login(token);
        } catch (UnknownAccountException e) {
            msg = "Имя пользователя не существует";
        } catch (IncorrectCredentialsException e) {
            msg = "Неверный пароль";
        } catch (AuthenticationException e) {
            msg = "Другие ошибки：" + e.getMessage();
        }

        if (msg != null) {
            model.addAttribute("msg", msg);
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/logout")
    public String user_logout() {
        SecurityUtils.getSubject().logout();
        return "logout";
    }

    private boolean isNumber(String str) {
        int len = str.length();
        if (len == 0) return false;
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    private List<Shoes> deepCopy(List<Shoes> data) {
        List<Shoes> tmp = new ArrayList<Shoes>();
        for (Shoes shoes : data) {
            tmp.add(new Shoes(shoes));
        }
        return tmp;
    }
}
