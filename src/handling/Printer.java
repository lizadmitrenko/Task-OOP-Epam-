package handling;
import java.lang.reflect.Field;
import java.util.List;

import product.Product;

public class Printer {

    public void printProducts(List<Product>list) throws NoSuchFieldException,IllegalAccessException{
        if(list.isEmpty()){
            System.out.println("There is no such products!");
        }
        else {
            for (int i = 0; i < list.size(); i++) {
                printProduct(list.get(i));
            }
        }
    }

    private static void printProduct(Product product) throws NoSuchFieldException,IllegalAccessException{
        Field [] fields = product.getClass().getDeclaredFields();
        for(Field f: fields) {
            f.setAccessible(true);
            System.out.print(f.getName()+" = "+f.get(product)+", ");
        }
        System.out.println();
    }

}
