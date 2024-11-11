package com.example.t4;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;


@SessionScoped
@Named
public class TestBean implements Serializable {

    public String getData(){
        return  "Hellossss";
    }
}
