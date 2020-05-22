package com.example.mockingexample.data;

/**
 * 商品Bean类
 */
public class Goods {

    public String name; // 商品名称
    public int stock;   // 商品归类

    public Goods() {

    }

    public Goods(String name) {

    }

    public Goods(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

}
