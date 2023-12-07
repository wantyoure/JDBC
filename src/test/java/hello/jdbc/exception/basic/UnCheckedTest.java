package hello.jdbc.exception.basic;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }
    @Test
    void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() ->  service.callThrow()).isInstanceOf(MyUnCheckedException.class);
    }


    /*
    *   RunTimeException을 상속 받은 예외는 언체크 예외가 된다.
    * */
    static class MyUnCheckedException extends RuntimeException {
        public MyUnCheckedException(String message) {
            super(message);
        }
    }

    static class Repository {
        public void call() {
            throw new MyUnCheckedException("ex");
        }

    }

    static class Service {
        Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.call();
            }catch (MyUnCheckedException e) {
                log.info("예외 처리 exception={}",e.getMessage(),e);
            }

           // 컴파일러가 체크를 하냐 마나 차이이다.
        }

        /*
        * 체크 예외와 다르게 예외 선언을 하지 않아도 된다.
        * */

        public void callThrow() {
            repository.call();
        }
    }
}
