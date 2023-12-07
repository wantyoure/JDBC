package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - @Transaction AOP
 */
@Slf4j
public class MemberServiceV3_3 {

    //private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_3(MemberRepositoryV3 memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional // 이 로직 실작할때 트렌젝션 걸고 시작하겠다. 성공하면 commit 실패 rollback
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
                bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money)
            throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);
        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); //커넥션 풀 고려
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }
}
