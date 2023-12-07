package hello.jdbc.repository.ex;

public class MyDuplicateKeyException extends MyDbException{ // RuntimeException 을 쓸 수 있지만 카테고리로 묶을 수 있기 때문에 상속 하는 것이다.


    public MyDuplicateKeyException() {
    }

    public MyDuplicateKeyException(String message) {
        super(message);
    }

    public MyDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
