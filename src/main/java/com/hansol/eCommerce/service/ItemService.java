package com.hansol.eCommerce.service;

import com.hansol.eCommerce.constant.ItemSellStatus;
import com.hansol.eCommerce.dto.ItemFormDto;
import com.hansol.eCommerce.dto.ItemImgDto;
import com.hansol.eCommerce.dto.ItemSearchDto;
import com.hansol.eCommerce.dto.MainItemDto;
import com.hansol.eCommerce.entity.Item;
import com.hansol.eCommerce.entity.ItemImg;
import com.hansol.eCommerce.repository.ItemImgRepository;
import com.hansol.eCommerce.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList) throws Exception {
        //상품 등록
        Item item = itemFormDto.createItem();

        LocalDateTime sellStartTime = itemFormDto.getSellStartTime();
        if(sellStartTime.isBefore(LocalDateTime.now())) {
            item.setItemSellStatus(ItemSellStatus.SELL);
        } else {
            item.setItemSellStatus(ItemSellStatus.SOON);
        }

        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto,
                           List<MultipartFile> itemImgFileList) throws Exception {
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

    @Scheduled(cron = "0 * * * * ?", zone="Asia/Seoul") // 10초마다 실행
    public void checkSellStartTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Item> itemList = itemRepository.findByItemSellStatus(ItemSellStatus.SOON);
        for (Item item : itemList) {
            if (item.getSellStartTime().isBefore(now)) {
                item.setItemSellStatus(ItemSellStatus.SELL);
                itemRepository.save(item);
                System.out.println("===========================");
                System.out.println("상품 상태 변경 성공");
            }
        }
        System.out.println("===========================");

    }
}
