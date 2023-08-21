package com.storagesensei.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserList {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long list_id;

  @Column(name="name")
  private String name;

  @Column(name="owner", updatable=false, nullable=false)
  private String owner;

  @Column(name="description")
  private String description;

  @Column(name="item_count")
  private int itemCount;

  /*@Column(name="type")
  private ListType listType;*/

  /*@Column(name="")
  private List<Item> items;*/

  public UserList() { }

  public UserList(String owner, String name, String description, int itemCount, ListType listType/*, List<Item> items*/) {
    this.owner = owner;
    this.name = name;
    this.description = description;
    this.itemCount = itemCount;
    //this.listType = listType;
    //this.items = items;
  }

  public void setList_id(Long list_id) {
    this.list_id = list_id;
  }

  public Long getList_id() {
    return list_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String userEmail) {
    this.owner = userEmail;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  /*public ListType getListType() {
    return listType;
  }

  public void setListType(ListType listType) {
    this.listType = listType;
  }*/

  /*public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }*/
}
