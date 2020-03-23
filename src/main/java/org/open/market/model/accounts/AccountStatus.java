package org.open.market.model.accounts;

/**
* @author halfdev
* @since 2020-03-23
* USER : 일반사용자,
* BLOCK : 관리자로 인해 차단당한 사용자,
* EXPIRED : 휴면 계정,
* DELETED : 탈퇴 계정
*/
public enum AccountStatus {
    USER,
    BLOCK,
    EXPIRED,
    DELETED
}
