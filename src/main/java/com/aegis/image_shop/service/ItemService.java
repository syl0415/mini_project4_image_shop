package com.aegis.image_shop.service;

import com.aegis.image_shop.domain.Item;

import java.util.List;

public interface ItemService {

    public void register(Item item) throws Exception;

    public Item read(Long itemId) throws Exception;

    public void modify(Item item) throws Exception;

    public void remove(Long itemId) throws Exception;

    public List<Item> list() throws Exception;

    public String getPreview(Long itemId) throws Exception;
}
