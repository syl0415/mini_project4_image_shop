package com.aegis.image_shop.repository;

import com.aegis.image_shop.domain.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    public List<UserItem> findByUserNo(Long userNo);

    @Query("SELECT a.userItemNo, a.userNo, a.itemId, a.regDate, b.itemName, b.price, b.description, b.pictureUrl "
            + "FROM UserItem a INNER JOIN Item b ON a.itemId = b.itemId "
            + "WHERE a.userNo = ?1 "
            + "ORDER BY a.userItemNo DESC, a.regDate DESC")
    public List<Object[]> listUserItem(Long userNo);

    @Query("SELECT a.userItemNo, a.userNo, a.itemId, a.regDate, b.itemName, b.price, b.description, b.pictureUrl "
            + "FROM UserItem a INNER JOIN Item b ON a.itemId = b.itemId "
            + "WHERE a.userItemNo = ?1")
    public List<Object[]> readUserItem(Long userItemNo);
}
