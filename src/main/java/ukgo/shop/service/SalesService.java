package ukgo.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ukgo.shop.dto.SalesDto;
import ukgo.shop.entity.Item;
import ukgo.shop.entity.Member;
import ukgo.shop.entity.Sales;
import ukgo.shop.repository.SalesRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    public void processOrder(String username, Integer itemId, Integer price, Integer count) throws IOException {
        // 인증된 사용자 정보와 아이템 정보를 가져옵니다.
        Member member = memberService.findMemberByUsername(username);
        Item item = itemService.findItemById(itemId);

        if (member == null || item == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자 또는 아이템 정보입니다.");
        }

        // Sales 객체를 생성하고 필요한 필드를 설정합니다.
        Sales sales = new Sales();
        sales.setItem(item);
        sales.setMember(member);
        sales.setPrice(price);
        sales.setCount(count);

        // Sales 객체를 데이터베이스에 저장합니다.
        salesRepository.save(sales);
    }

    public List<SalesDto> getAllSalesDTO() {
        List<Sales> salesList = salesRepository.findAll(); // or use a custom method
        return salesList.stream()
                .map(s -> new SalesDto(
                        s.getItem().getId(),
                        s.getPrice(),
                        s.getCount(),
                        s.getMember().getId(),
                        s.getCreated()
                ))
                .collect(Collectors.toList());
    }
}
