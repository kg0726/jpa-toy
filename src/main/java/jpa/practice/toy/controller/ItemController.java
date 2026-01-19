package jpa.practice.toy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jpa.practice.toy.domain.Member;
import jpa.practice.toy.dto.ItemRequest;
import jpa.practice.toy.dto.ItemResponse;
import jpa.practice.toy.dto.ItemUpdateRequest;
import jpa.practice.toy.dto.LikeItemListResponse;
import jpa.practice.toy.repository.ItemRepository;
import jpa.practice.toy.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> addItem(@Valid @RequestBody ItemRequest dto, HttpServletRequest request) {
        // 세션에서 로그인한 회원 정보 꺼내기
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");

        // 서비스에 상품 정보와 누가 등록하는지 정보를 전달
        ItemResponse response = itemService.addProduct(dto, loginMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/like/{id}")
    public ResponseEntity<ItemResponse> likeItem(@PathVariable Long id, HttpServletRequest request) {
        // 세션에서 로그인한 사용자 꺼내기
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");

        // 좋아요 요청
        ItemResponse response = itemService.likeItem(id, loginMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 좋아요한 상품 모두 조회
    @GetMapping("/like")
    public ResponseEntity<LikeItemListResponse> likeItemList(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");

        // 찜한 상품 목록 요청
        LikeItemListResponse response = itemService.likeItemList(loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 등록된 상품 모두 조회
    @GetMapping
    public ResponseEntity<Page<ItemResponse>> itemList(
            HttpServletRequest request,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ) {
        // 로그인한 사용자 조회(해당 사용자가 좋아요를 했는지 확인 여부)
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        return ResponseEntity.status(HttpStatus.OK).body(itemService.allItems(pageable, loginMember));
    }

    // 등록된 상품 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> item(
            HttpServletRequest request, @PathVariable Long id) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItem(id, loginMember));
    }

    // 등록된 상품 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(
            HttpServletRequest request, @PathVariable Long id, @RequestBody ItemUpdateRequest itemUpdateRequest
            ) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(id, loginMember, itemUpdateRequest));
    }

    // 등록된 상품 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        itemService.deleteItem(id, loginMember);
    }
}
