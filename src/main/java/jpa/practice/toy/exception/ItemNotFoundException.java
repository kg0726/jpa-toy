package jpa.practice.toy.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("해당 상품을 찾을 수 없습니다.");
    }
}
