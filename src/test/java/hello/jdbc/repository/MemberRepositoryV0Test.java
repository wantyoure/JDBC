package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();


    @Test
    void crud() throws SQLException {
        // 저장
        Member member = new Member("memberV5",10000);
        repository.save(member);
        // 조회
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}",findMember);
        //update
        repository.update(member.getMemberId(),20000);
        Member updateMember = repository.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);
        // 삭제
        repository.delete(member.getMemberId());


    }


}