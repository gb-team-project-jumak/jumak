package com.example.jumak.controller.main;

import com.example.jumak.domain.dto.madang.MadangDto;
import com.example.jumak.domain.vo.main.MadangListVo;
import com.example.jumak.domain.vo.main.ProductSearchVo;
import com.example.jumak.service.main.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final MainService mainService;

    @GetMapping("/")
    public String mainPage(Model model){


//        공지사항
        List<MadangDto> announcementList = mainService.announcement();


//        수상한 마당
        List<MadangListVo> madangList = mainService.madang();


        model.addAttribute("announcementList", announcementList);
        model.addAttribute("madangList", madangList);

        return "main/main";
    }

    //   나에게 맞는 술 찾기
    @GetMapping("/search")
    public String searchPage(@RequestParam("keyword") String keyword, Model model){
        List<ProductSearchVo> searchList = mainService.findSearch(keyword);
        int count = mainService.countSearch(keyword);

        model.addAttribute("searchList",searchList);
        model.addAttribute("keyword",keyword);
        model.addAttribute("count",count);
        return "product/search";
    }


    //    상품검색
    @GetMapping("/find")
    public String findPage(){
        return "product/find";
    }


}