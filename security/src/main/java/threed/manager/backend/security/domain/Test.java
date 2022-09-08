package threed.manager.backend.security.domain;

import lombok.Data;

@Data
public class Test {
    private String text;
    public Test(){

    }
    public Test(String text){
        this.text=text;
    }
}
