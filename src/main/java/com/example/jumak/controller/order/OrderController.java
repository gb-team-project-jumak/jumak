package com.example.jumak.controller.order;

import com.example.jumak.domain.dto.order.OrderDto;
import com.example.jumak.domain.dto.order.ShoppingListDto;
import com.example.jumak.domain.vo.order.CartVo;
import com.example.jumak.domain.vo.order.OrderVo;
import com.example.jumak.domain.vo.product.ProductDetailVo;
import com.example.jumak.service.order.OrderService;
import com.example.jumak.service.product.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    private final StoreService storeService;

    
    //    장바구니
    @GetMapping("/cart")
    public String cartPage(@RequestParam("productNumber") Long productNumber,
                           @RequestParam(name="productCount",required = true) Long productCount,
                           HttpServletRequest req,
                           @SessionAttribute("userNumber") Long userNumber,
                           Model model){
        ShoppingListDto shoppingListDto = new ShoppingListDto();
        shoppingListDto.setProductCount(productCount);
        shoppingListDto.setProductNumber(productNumber);
        shoppingListDto.setUserNumber(userNumber);

        List<CartVo> cartList = orderService.cartRegister(shoppingListDto);

        long totalPrice = cartList.stream().mapToLong(item -> item.getProductPrice() * item.getProductCount()).sum();
        long totalCount = cartList.stream().mapToLong(item -> item.getProductCount()).sum();
        long salePrice = cartList.stream().mapToLong(item -> (int)item.getSalePrice()).sum();


        model.addAttribute("cartList", cartList);

        model.addAttribute("salePrice", salePrice);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("paymentTotal", totalPrice + 3000);

//        ProductDetailVo productCDetail = storeService.findByDNumber(productNumber);
//        model.addAttribute("productDetail",productCDetail);
//        model.addAttribute("productCount",productCount);
//        //        상품 토탈 가격
//        model.addAttribute("totalPrice", productCount * productCDetail.getProductPrice());
////        페이먼트 토탈 가격 (배송비+3000원)
//        model.addAttribute("paymentTotal", (productCount *(productCDetail.getProductPrice() - (productCDetail.getProductPrice() *(productCDetail.getProductDiscount()/100.0))))
//                +3000);



        return "order/cart";
    }

//    장바구니 바로구매
    @GetMapping("/cart_next")
    public String cartMain(@RequestParam("productNumber") Long productNumber,
                           @RequestParam(name="productCount",required = true) Long productCount,
                           HttpServletRequest req,
                           @SessionAttribute("userNumber") Long userNumber,
                           Model model){

        ShoppingListDto shoppingListDto = new ShoppingListDto();
        shoppingListDto.setProductCount(productCount);
        shoppingListDto.setProductNumber(productNumber);
        shoppingListDto.setUserNumber(userNumber);

        List<CartVo> cartList = orderService.cartRegister(shoppingListDto);

        long totalPrice = cartList.stream().mapToLong(item -> item.getProductPrice() * item.getProductCount()).sum();
        long totalCount = cartList.stream().mapToLong(item -> item.getProductCount()).sum();
        long salePrice = cartList.stream().mapToLong(item -> (int)item.getSalePrice()).sum();
        long totalDiscount = totalPrice - salePrice;



        model.addAttribute("cartList", cartList);

        model.addAttribute("salePrice", salePrice);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("paymentTotal", totalPrice + 3000);

        Object getUserNumber = req.getSession().getAttribute("userNumber");
        OrderVo byUNumber = null ;

        byUNumber = orderService.findByUNumber((Long) getUserNumber);
        model.addAttribute("orderUInfo",byUNumber);

        return "order/store_cart_order";
    }



//    바로구매
    @GetMapping("/next")
    public String orderMain(@RequestParam("productNumber") Long productNumber,
                            @RequestParam("productCount") Long productCount,
                            HttpServletRequest req,
                            Model model){
// productNumber 로 next페이지에 넘겨줄 정 보 서비스에서 뽑아서 넘겨주고
//        productCount 는 그냥 넘겨주기
        ProductDetailVo productDetail = storeService.findByDNumber(productNumber);


        model.addAttribute("productDetail",productDetail);
        model.addAttribute("productCount",productCount);
        // productCount는 ${productCount}로 html에 사용
        //        상품 토탈 가격
        model.addAttribute("totalPrice", productCount * productDetail.getProductPrice());
//        페이먼트 토탈 가격 (배송비+3000원)
        model.addAttribute("paymentTotal", (productCount *(productDetail.getProductPrice() - (productDetail.getProductPrice() *(productDetail.getProductDiscount()/100.0))))
                +3000);

        //주문자정보 사용자정보뿌리기 매퍼에서부터 찾아서
        Object userNumber = req.getSession().getAttribute("userNumber");

        //오더서비스연결해서 모델로 뿌리기

        OrderVo byUNumber = null ;

        byUNumber = orderService.findByUNumber((Long) userNumber);

        model.addAttribute("orderUInfo",byUNumber);
        
        return "order/storeorder";
    }


    @GetMapping("/fail")
    public String orderFail(Model model, Integer price, @SessionAttribute("userNumber") Long userNumber){
//        OrderVo orderFin = orderService.findByNumber(userNumber);
//        model.addAttribute("finalorder",orderFin);
        model.addAttribute("totalprice",price);
        return "order/storeorderfail";
    }
    @GetMapping("/success")
    public String orderSuccess(Model model, Integer price, @SessionAttribute("userNumber") Long userNumber){
        OrderVo orderFin = orderService.findByNumber(userNumber);
        model.addAttribute("finalorder",orderFin);
        model.addAttribute("totalprice",price);
        return "order/storeordersucc";
    }




}
