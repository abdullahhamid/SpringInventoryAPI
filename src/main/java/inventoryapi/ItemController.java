package inventoryapi;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    @Autowired
    private ItemRepository repo;

    
    //GET All Items
    @RequestMapping(value = "/", method=RequestMethod.GET)
    public ArrayList<Item> getAllItems() {
    	ArrayList<Item> items = (ArrayList<Item>) repo.findAll();
        return items;
    }
    
    //CREATE Item
    @RequestMapping(value = "/item", method=RequestMethod.POST, headers="Accept=application/json")
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        try {
            repo.save(item);
        	log.info("Item saved: "+item.toString());
        	return new ResponseEntity<Item>(item, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //GET Item by Id
    @RequestMapping(value = "/item/{id}", method=RequestMethod.GET, headers="Accept=application/json")
    public ResponseEntity<?> getItem(@PathVariable int id) {
        try {
            Item item = repo.findOne(id);
            if (item == null)
            	return new ResponseEntity<String>("Item Not Found!", HttpStatus.NOT_FOUND);
            else
            	return new ResponseEntity<Item>(item, HttpStatus.OK);
            
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //UPDATE Item
    @RequestMapping(value = "/item/{id}", method=RequestMethod.PUT, headers="Accept=application/json")
    public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody Item item) {
        try {
            Item itemSearch = repo.findOne(id);
            if (itemSearch == null){
            	return new ResponseEntity<String>("Item Not Found!", HttpStatus.NOT_FOUND);
            }
            itemSearch.setName(item.getName());
            itemSearch.setQuantity(item.getQuantity());
            repo.save(itemSearch);
            log.info("Item updated - "+itemSearch.toString());
            return new ResponseEntity<>(itemSearch, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //DELETE Item
    @RequestMapping(value = "/item/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteItem(@PathVariable int id) {
        try {
        	Item item = repo.findOne(id);
            if (item == null)
            	return new ResponseEntity<String>("Item Not Found!", HttpStatus.NOT_FOUND);
            repo.delete(id);
            log.info("Item id: "+ id +" deleted!");
            return new ResponseEntity<String>("Item id: "+ id +" deleted!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
