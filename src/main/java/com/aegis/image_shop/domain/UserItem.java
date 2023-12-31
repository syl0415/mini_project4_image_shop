package com.aegis.image_shop.domain;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of="userItemNo")
@ToString
@Entity
@Table(name="user_item")
public class UserItem {

    @Id
    @SequenceGenerator(name = "USER_ITEM_SEQUENCE_GEN", sequenceName = "seq_user_item", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ITEM_SEQUENCE_GEN")
    private Long userItemNo;

    private Long userNo;
    private Long itemId;

    @Transient
    private String itemName;
    @Transient
    private Integer price;
    @Transient
    private String description;
    @Transient
    private String pictureUrl;

    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    private LocalDateTime updDate;

}
