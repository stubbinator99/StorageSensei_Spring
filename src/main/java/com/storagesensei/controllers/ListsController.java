package com.storagesensei.controllers;

import com.storagesensei.db.ListRepository;
import com.storagesensei.models.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/lists")
public class ListsController {
  @Autowired
  private ListRepository listRepo;

  @RequestMapping("")
  public ModelAndView  showAllListsForUser(
      @RequestParam("username") String username
  ) {
    ModelAndView mav = new ModelAndView("all_user_lists");
    mav.addObject("lists", listRepo.findListsByUsername(username));
    return mav;
  }

  @RequestMapping("/{id}")
  public ModelAndView findListById(
      @PathVariable("id") long listId,
      @RequestParam("username") String username
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");
    mav.addObject("list", listRepo.findListByIdAndUser(username, listId));
    return mav;
  }

  /*@PostMapping("/add")
  public ModelAndView addList(
      @RequestParam("list") UserList list,
      @RequestParam("username") String username
  ) {
    ModelAndView mav = new ModelAndView("list_single_view");
    mav.addObject("message", "List created successfully!");

    mav.addObject("list", listRepo.addList(
        list.getName(),
        list.getDescription(),
        list.getItemCount(),
        list.getListType(),
        list.getList_id(),
        username));
    return mav;
  }

  @PostMapping("/update")
  public ModelAndView updateList(
      @RequestParam("list") UserList list,
      @RequestParam("username") String username
  ) {
    ModelAndView mav = new ModelAndView("update_list_response");

    mav.addObject("list", listRepo.updateList(
        list.getName(),
        list.getDescription(),
        list.getItemCount(),
        list.getListType(),
        list.getList_id(),
        username));
    return mav;
  }*/

}
