package com.storagesensei.controllers;

import com.storagesensei.config.JwtTokenUtil;
import com.storagesensei.db.ListRepository;
import com.storagesensei.models.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Optional;

@Controller
@RequestMapping("/lists")
public class ListsController {
  @Autowired
  private ListRepository listRepo;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @RequestMapping("/all")
  public ModelAndView  showAllListsForUser(
      HttpServletRequest request
  ) {
    ModelAndView mav = new ModelAndView("all_user_lists");

    Optional<String> username = jwtTokenUtil.getUsernameFromRequest(request);
    if (username.isEmpty()) {
      return mav;
    }

    mav.addObject("lists", listRepo.findListsByUsername(username.get()));

    return mav;
  }

  @RequestMapping("/{id}")
  public ModelAndView findListById(
      HttpServletRequest request,
      @PathVariable("id") long listId
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");

    Optional<String> username = jwtTokenUtil.getUsernameFromRequest(request);
    if (username.isEmpty()) {
      return mav;
    }

    mav.addObject("list", listRepo.findListByUsernameAndId(username.get(), listId));
    return mav;
  }

  @RequestMapping("/new")
  public String showNewListForm(
      Model model
  ) {
    model.addAttribute("list", new UserList());

    return "new_list";
  }

  @PostMapping("/new")
  public ModelAndView addList(
      HttpServletRequest request,
      UserList list
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");

    Optional<String> username = jwtTokenUtil.getUsernameFromRequest(request);
    if (username.isEmpty()) {
      return mav;
    }

    list.setOwner(username.get());

    mav.addObject("list", listRepo.save(list));
    mav.addObject("message", "List created successfully.");

    return mav;
  }

  @RequestMapping("/edit/{id}")
  public String showEditListForm(
      HttpServletRequest request,
      /*@PathParam*/
      @PathVariable("id") String id,
      Model model
  ) {
    Optional<String> username = jwtTokenUtil.getUsernameFromRequest(request);
    if (username.isEmpty()) {
      // error here
    }

    UserList list = listRepo.findListByUsernameAndId(username.get(), Long.valueOf(id));

    model.addAttribute("list", list);
    return "edit_list";
  }

  @PostMapping("/update")
  public ModelAndView updateList(
      HttpServletRequest request,
      UserList list
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");

    Optional<String> username = jwtTokenUtil.getUsernameFromRequest(request);
    if (username.isEmpty()) {
      return mav;
    }

    list.setOwner(username.get());

    mav.addObject("list", listRepo.save(list));
    mav.addObject("message", "List updated successfully.");

    return mav;
  }

}
