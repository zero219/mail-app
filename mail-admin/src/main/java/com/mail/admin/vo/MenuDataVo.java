package com.mail.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDataVo {
    private Long parentId;
    private String parentName;
    private List<MenuSubDataVo> menuSubDataVos;
    private Integer sort;
}
