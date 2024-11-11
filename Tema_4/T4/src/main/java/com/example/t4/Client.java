package com.example.t4;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Client {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Integer x;
    private Integer y;

}
