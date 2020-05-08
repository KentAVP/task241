package CRUD.controller;

import CRUD.model.User;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin/**")
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"","list"}, method = {RequestMethod.GET})
    public String listUser(ModelMap model) {
        List<User> listUser = userService.getAll();
        model.addAttribute("listUser", listUser);
        return "list";
    }
    @RequestMapping(value = {"new"}, method = {RequestMethod.GET})
    public String showNewForm() {
        return "form";
    }
    @RequestMapping(value = {"insert"}, method = {RequestMethod.POST})
    public String insertUser(@ModelAttribute("user")User user, ModelMap model) {
        model.addAttribute("user",user);
        userService.add(user);
        return "redirect:admin/list";
    }
    @RequestMapping(value = {"update"}, method = {RequestMethod.POST})
    public String updateUser(@ModelAttribute("user")User user, ModelMap model) {
        model.addAttribute("user",user);
        userService.update(user);
        return "redirect:admin/list";
    }
    @RequestMapping(value = {"delete"}, method = {RequestMethod.GET})
    public String deleteUser(@ModelAttribute("id")User user, ModelMap model) {
        model.addAttribute("id",user.getId());
        userService.delete(user);
        return "redirect:admin/list";
    }
    @RequestMapping(value = {"edit"}, method = {RequestMethod.GET})
    public String showEditForm(@ModelAttribute("id")User user, ModelMap model) {
        model.addAttribute("id",user.getId());
        User us = userService.getbyID(user.getId());
        model.addAttribute("user",us);
        return "form";
    }
}
