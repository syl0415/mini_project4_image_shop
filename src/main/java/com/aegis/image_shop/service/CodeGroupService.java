package com.aegis.image_shop.service;

import com.aegis.image_shop.domain.CodeGroup;

import java.util.List;


public interface CodeGroupService {

    public void register(CodeGroup codeGroup) throws Exception;

    public CodeGroup read(String groupCode) throws Exception;

    public void modify(CodeGroup codeGroup) throws Exception;

    public void remove(String groupCode) throws Exception;

    public List<CodeGroup> list() throws Exception;

}
