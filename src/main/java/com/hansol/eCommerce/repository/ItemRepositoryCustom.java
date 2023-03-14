package com.hansol.eCommerce.repository;

import com.hansol.eCommerce.dto.ItemSearchDto;
import com.hansol.eCommerce.dto.MainItemDto;
import com.hansol.eCommerce.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
