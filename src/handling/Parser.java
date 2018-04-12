package handling;

import java.lang.reflect.Field;


import product.Laptop;
import product.Oven;
import product.Product;
import product.Refrigerator;

public class Parser {
    private String oven = "Oven";
    private String laptop = "Laptop";
    private String refrigerator = "Refrigerator";

    public Product parse(String line) throws IllegalAccessException{
        String [] arr = line.split(":|;");
        Product product = new Product();
        if(arr[0].equalsIgnoreCase(laptop)) {
            return fillProductFields(arr[1],new Laptop());
        }
        else if(arr[0].equalsIgnoreCase(refrigerator)) {
            return fillProductFields(arr[1],new Refrigerator());
        }
        else if(arr[0].equalsIgnoreCase(oven)) {
            return fillProductFields(arr[1],new Oven());
        }
        return product;
    }

    private static Product fillProductFields(String line,Product product) throws IllegalAccessException{
        Field [] fields = product.getClass().getDeclaredFields();
        String [] arr = line.split(",");
        String [] option;
        for(String item: arr) {
            item = item.trim();
            option = item.split("=| ");
            option[0]=option[0].toLowerCase();
            fillAppropriateField(product,fields,option[0],option[1]);
        }
        return product;
    }

    private static void fillAppropriateField(Product product, Field[]fields,String field, String value) throws IllegalAccessException{
        for(Field f: fields) {
            if(f.getName().equalsIgnoreCase(field)) {
                f.setAccessible(true);
                chooseAppropriateFieldValue(product,f,value);
            }
        }
    }
    private static void chooseAppropriateFieldValue(Product product, Field f, String value) throws IllegalAccessException{
        if(f.getType()==String.class) {
            f.set(product, value);
        }
        else if (f.getType()==Integer.class) {
            f.set(product, Integer.parseInt(value));
        }
        else if (f.getType()==Double.class) {
            f.set(product, Double.parseDouble(value));
        }
    }

}
