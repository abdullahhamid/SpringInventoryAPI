package inventoryapi;

import org.junit.Test;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ItemRepository repo;
	
	@Autowired
    private ItemController controller;
	
    
    @Test
    public void shouldReturnItems() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }
    
    @Test
    public void shouldReturnCreatedItemObject() throws Exception {
    	Item item = new Item (3, "bounty", 78);
        this.mockMvc.perform(post("/item")
        		.contentType(TestUtil.APPLICATION_JSON_UTF8)
        		.content(TestUtil.convertObjectToJsonBytes(item)))
        		.andDo(print()).andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", containsString("bounty")))
                .andExpect(jsonPath("$.id").value(item.getId()));
        
        //Delete this item after assertion
        try{
    		repo.delete(3);
    	}catch (Exception e){
    		log.error(e.getMessage());
    	}
    }
    
    @Test
    public void shouldReturnItemById() throws Exception {
    	int id = 1;
        this.mockMvc.perform(get("/item/"+id)
        		.contentType(TestUtil.APPLICATION_JSON_UTF8))
        		.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", containsString("kitkat")))
                .andExpect(jsonPath("$.id").value(id));
    }
    
    @Test
    public void shouldReturnNotFound() throws Exception {
    	int id = 3;
        this.mockMvc.perform(get("/item/"+id)
        		.contentType(TestUtil.APPLICATION_JSON_UTF8))
        		.andDo(print()).andExpect(status().isNotFound());
    }
    
    @Test
    public void shouldReturnItemUpdatedById() throws Exception {
    	int id = 1;
    	Item item = new Item (1, "kitkatOrange", 25);
        this.mockMvc.perform(put("/item/"+id)
        		.contentType(TestUtil.APPLICATION_JSON_UTF8)
        		.content(TestUtil.convertObjectToJsonBytes(item)))
        		.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", containsString(item.getName())))
                .andExpect(jsonPath("$.quantity").value(item.getQuantity()))
                .andExpect(jsonPath("$.id").value(id));
    }
    
    @Test
    public void shouldDeleteItemById() throws Exception {
    	Item item = new Item (4, "dairymilk", 78);
    	
    	//Insert new item in db for this test
    	try{
    		repo.save(item);
    	}catch (Exception e){
    		log.error(e.getMessage());
    	}
    	
        this.mockMvc.perform(delete("/item/"+item.getId())
        		.contentType(TestUtil.APPLICATION_JSON_UTF8))
        		.andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Item id: "+ item.getId() +" deleted!")));
    }
    
    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		//populate database
		Item item1 = new Item(1, "kitkat", 50);
		Item item2 = new Item(2, "mars", 65);
		repo.save(item1);
		repo.save(item2);
	}
    

}
