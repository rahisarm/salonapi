package com.solutrix.salon.service.impl;


import com.solutrix.salon.dto.MenuDTO;
import com.solutrix.salon.entity.Menu;
import com.solutrix.salon.mapper.MenuMapper;
import com.solutrix.salon.repository.MenuRepo;
import com.solutrix.salon.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {
    private MenuRepo menurepo;


    @Override
    public List<MenuDTO> getAllMenu() {
        List<Menu> menus=menurepo.findAll();
        return menus.stream().map((menu)-> MenuMapper.maptoMenuDTO(menu))
                .collect(Collectors.toList());
    }
}
