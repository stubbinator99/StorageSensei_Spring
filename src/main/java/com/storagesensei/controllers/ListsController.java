package com.storagesensei.controllers;

import com.storagesensei.db.ListRepository;
import com.storagesensei.models.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lists")
public class ListsController {
  @Autowired
  private ListRepository listRepo;

  @RequestMapping("/all/{username}")
  public ModelAndView  showAllListsForUser(
      /*@RequestParam("username") String username*/
      @PathVariable("username") String username//temporary
  ) {
    ModelAndView mav = new ModelAndView("all_user_lists");
    mav.addObject("lists", listRepo.findListsByUsername(username));
    return mav;
  }

  @RequestMapping("/{id}/{username}")
  public ModelAndView findListById(
      @PathVariable("id") long listId,
     /* @RequestParam("username") String username*/
      @PathVariable("username") String username//temporary
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");
    mav.addObject("list", listRepo.findListByIdAndUser(username, listId));
    return mav;
  }

  @RequestMapping("/new")
  public String showNewListForm(Model model) {
    model.addAttribute("list", new UserList());

    return "new_list";
  }

  @PostMapping("/new/{username}")
  public ModelAndView addList(
      @RequestParam("list") UserList list,
      /* @RequestParam("username") String username*/
      @PathVariable("username") String username//temporary
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");

    /*mav.addObject("list", listRepo.addList(
        list.getName(),
        list.getDescription(),
        list.getItemCount(),
        list.getListType(),
        list.getList_id(),
        username));*/

    list.setOwner(username);//temporary

    mav.addObject("list", listRepo.save(list));
    mav.addObject("message", "List created successfully.");

    return mav;
  }

  @PostMapping("/update")
  public ModelAndView updateList(
      @RequestParam("list") UserList list,
      @RequestParam("username") String username
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");

    /*mav.addObject("list", listRepo.updateList(
        list.getName(),
        list.getDescription(),
        list.getItemCount(),
        list.getListType(),
        list.getList_id(),
        username));*/

    mav.addObject("list", listRepo.save(list));
    mav.addObject("message", "List updated successfully.");

    return mav;
  }

}
