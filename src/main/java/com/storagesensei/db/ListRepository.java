package com.storagesensei.db;

import com.storagesensei.models.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ListRepository extends JpaRepository<UserList, Long> {
  @Query(value = "SELECT * FROM lists WHERE owner = ?1", nativeQuery = true)
  public List<UserList> findListsByUsername(String username);

  @Query(value = "SELECT * FROM lists WHERE owner=?1 AND list_id = ?2", nativeQuery = true)
  public UserList findListByUsernameAndId(String username, long listId);

  /*@Modifying
  @Query("UPDATE UserList l SET l.name = ?1, l.description = ?2, l.itemCount = ?3, l.listType = ?4 WHERE l.list_id = ?5 AND l.owner = ?6")
  public UserList updateList(String name, String description, int itemCount, ListType type, long listId, String username);*/

  /*@Modifying
  @Query("INSERT INTO UserList () SELECT  ")
  public UserList addList(String name, String description, int itemCount, ListType type, long listId, String username);*/

}
