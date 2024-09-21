package com.solutrix.salon.mapper;


import com.solutrix.salon.dto.MenuDTO;
import com.solutrix.salon.entity.Menu;

public class MenuMapper {

    public static MenuDTO maptoMenuDTO(Menu menu) {
        return new MenuDTO(menu.getMno(), menu.getMenuname(), menu.getMenupath(), menu.getPrintpath(), menu.getPmenu(), menu.getDoctype(), menu.getGate());
    }

    public static Menu maptoMenu(MenuDTO menuDTO) {
        return new Menu(menuDTO.getMno(), menuDTO.getMenuname(), menuDTO.getMenupath(), menuDTO.getPrintpath(), menuDTO.getPmenu(), menuDTO.getDoctype(), menuDTO.getGate());
    }
}
