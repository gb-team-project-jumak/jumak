package com.example.jumak.controller.product;

import com.example.jumak.domain.vo.product.Criteria;
import com.example.jumak.domain.vo.product.PageVo;
import com.example.jumak.domain.vo.product.ProductDetailVo;
import com.example.jumak.domain.vo.product.ProductInfoVo;
import com.example.jumak.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

//    NEW 우리술
    @GetMapping("/list")
    public String productMain(Model model){

        List<ProductInfoVo> cateList = productService.findByCate();
        model.addAttribute("products",cateList);
        
        return "product/product_info_main";
    }


    // 우리술 상품보기
    @GetMapping("/view")
    public String productList(String orderCond, String cate, Criteria criteria, Model model){
        criteria.setAmount(10);
        List<ProductInfoVo> productList = productService.findByCond(orderCond, cate, criteria);
        model.addAttribute("products",productList);
        model.addAttribute("orderCond",orderCond);
        model.addAttribute("cate",cate);
        model.addAttribute("pageInfo", new PageVo(productService.findTotalByCond(cate).intValue(),criteria));
        return "product/product_info_list";}
    
    //우리술 상품 상세보기
    @GetMapping("/detail")
    public String productDetail(@RequestParam("productNumber")Long productNumber,Model model){

        ProductInfoVo productDetails = productService.findByDNumber(productNumber);
        model.addAttribute("detailproduct",productDetails);

        List<ProductInfoVo> byDImgList = productService.findByDImg(productNumber);
        model.addAttribute("dimgs",byDImgList);

        return "product/product_info_detail";
    }


}
