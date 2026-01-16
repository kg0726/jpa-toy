package jpa.practice.toy.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("해당 사용자를 찾을 수 없습니다.");
    }
}
