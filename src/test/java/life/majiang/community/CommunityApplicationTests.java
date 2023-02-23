package life.majiang.community;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootTest
public class CommunityApplicationTests {

    @Autowired(required = false)
    private DataSource dataSource;

    @Test
    public void aaa() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
