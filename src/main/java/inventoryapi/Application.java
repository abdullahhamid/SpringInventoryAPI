package inventoryapi;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class Application implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    ItemRepository repo;
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        log.info("Creating tables");

        //jdbcTemplate.execute("DROP TABLE items IF EXISTS");
        //jdbcTemplate.execute("CREATE TABLE items(" +
        //        "id SERIAL, name VARCHAR(255), quantity int(11))");
    }
        
}
