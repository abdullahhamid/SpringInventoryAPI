package inventoryapi;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    @Autowired
    ItemRepository repo;
    
    @RequestMapping("/")
    public ArrayList<Item> getAllItems() {
    	ArrayList<Item> items = (ArrayList<Item>) repo.findAll();
        return items;
    }

    @RequestMapping(value = "/items", method=RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(value = "/item", method=RequestMethod.POST, headers = {"Content-type=application/json"})
    public String createItem(@RequestBody Item item) {
        try {
            repo.save(item);
        	log.info(item.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
        return "creation successful: " + String.valueOf(item.getId());
    }
    
    //check return type
    @RequestMapping(value = "/item/{id}", method=RequestMethod.GET)
    public String createItem(@PathVariable int id) {
    	Item item;
        try {
            item = repo.findOne(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
        return item.toString();
    }
}
