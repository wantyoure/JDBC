package hello.jdbc.exception.basic;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow()).isInstanceOf(MyCheckedException.class);
    }


    /*
    * Exception을 상속 받은 예외는 체크 예외가 된다.
    * */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }




    static class Service {
        Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외 처리, message={}",e.getMessage(), e);
            }

        }
        //체크 예외를 밖으로 던지는 코드
        //체크 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야한다.
        void callThrow()throws MyCheckedException  {
            repository.call();
        }
    }



    static class Repository {
        public void call() throws MyCheckedException { //던져야한다. 체크예외는 무조건 밖으로 던져야한다.
            throw new MyCheckedException("ex");
        }
    }
}


