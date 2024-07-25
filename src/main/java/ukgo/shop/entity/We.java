package ukgo.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class We{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    private Integer age;
    private String name;


    public void 나이설정(Integer age){
        if(age > 100 || age<0 ){
            System.out.println("나이똑바로입력해");
        }
        this.age = age;
    }
    public void 한살더하기(){
        if(age > 100 || age<0 ){
            System.out.println("나이똑바로입력해");
        }
        this.age = age+1;
    }
}
