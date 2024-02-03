package com.example.jumak.controller.product;

import com.example.jumak.domain.vo.product.*;
import com.example.jumak.service.product.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller @Slf4j
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    // 상품 전체보기
    @GetMapping("/list")
    public String productList(String orderCond, String cate, Criteria criteria, Model model){
        criteria.setAmount(10);
        List<ProductListVo> productList = storeService.findByCond(orderCond, cate, criteria);
        model.addAttribute("products", productList);
        model.addAttribute("orderCond", orderCond);
        model.addAttribute("cate", cate);
        model.addAttribute("pageInfo",new PageVo(storeService.findTotalByCond(cate).intValue(), criteria));

        return "product/storeproduct";
    }



    //    상품 상세보기
    @GetMapping("/view")
    public String detail(@RequestParam("productNumber") Long productNumber, Model model){

        ProductDetailVo prodcutDetails = storeService.findByDNumber(productNumber);
        model.addAttribute("detailproduct",prodcutDetails);

        List<ProductDetailVo> byDImgList = storeService.findByDImg(productNumber);
        model.addAttribute("dimgs",byDImgList);


        return "product/storeproductdetail" ;
    }



}
