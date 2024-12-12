import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ExampleSLF4J {
    private static Logger logger = LoggerFactory.getLogger(ExampleSLF4J.class);

    @Test
    public void sendTrace() {
        logger.trace("Пробрасываем тросы для мостов");
    }
    @Test
    public void sendInfo() {
        logger.info("Тросы проброшены");
    }
    @Test
    public void sendDebug() {
        logger.debug("Пробросили тросы в с помощью каната)");
    }
    @Test
    public void sendWarning() {
        logger.warn("Тросы могут упасть");
    }
    @Test
    public void sendError(){
        logger.error("Тросы падают вверх!!!");
    }
}
