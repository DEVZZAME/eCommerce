package com.hansol.eCommerce.dto;

import com.hansol.eCommerce.constant.ItemSellStatus;
import com.hansol.eCommerce.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotNull(message = "할인율은 필수 입력 값입니다. 0~100 사이의 숫자를 입력해주세요.")
    @Range(min = 0, max = 100)
    private int discountRate; //할인율

    private int discountPrice; //할인가격

    @NotNull(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    @NotNull(message = "상품 판매 시작 시간은 필수 입력 값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime sellStartTime; //상품 판매 시작 시간

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        Item item = modelMapper.map(this, Item.class);
        item.setDiscountPrice();
        return item;
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }

}
