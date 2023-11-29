package com.ohoon.board.app.repository;

import com.ohoon.board.app.dto.MemberSearchCondition;
import com.ohoon.board.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberQueryRepository {

    Page<Member> list(MemberSearchCondition condition, Pageable pageable);
}
