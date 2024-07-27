package ukgo.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ukgo.shop.dto.SalesDto;
import ukgo.shop.entity.Sales;
import ukgo.shop.repository.SalesRepository;
import ukgo.shop.service.SalesService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;
    private final SalesRepository salesRepository;

    @PostMapping("/order")
    public String postOrder(@RequestParam Integer itemId, @RequestParam Integer price, @RequestParam Integer count, Authentication auth) throws IOException {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        try {
            salesService.processOrder(auth.getName(), itemId, price, count);
        } catch (IllegalArgumentException e) {
            return "redirect:/error"; // 유효하지 않은 경우 에러 페이지로 리디렉션
        }

        return "redirect:/list"; // 리스트 페이지로 리디렉션
    }

    @GetMapping("/order/all")
    public String getOrderAll(Model model) {
        List<SalesDto> result = salesService.getAllSalesDTO();
        model.addAttribute("salesList", result);
        return "sales.html"; // sales.html에서 DTO 데이터를 표시
    }
}
