package com.hansol.eCommerce.entity;

import com.hansol.eCommerce.constant.ItemSellStatus;
import com.hansol.eCommerce.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name="price", nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int discountRate; //할인율

    @Column(nullable = true)
    private int discountPrice; //할인가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    @Column(name="sell_start_time")
    private LocalDateTime sellStartTime; //상품 판매 시작 시간

    public void setDiscountPrice() {
        this.discountPrice = this.price - ((this.price * this.discountRate) / 100);
    }

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
        this.discountRate = itemFormDto.getDiscountRate();
        this.sellStartTime = itemFormDto.getSellStartTime();
        this.discountPrice = this.price - ((this.price * this.discountRate) / 100);

    }


}
