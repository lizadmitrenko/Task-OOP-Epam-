package handling;

import product.Product;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Searcher {

    public List<Product> search(String line, List<Product> products) throws IllegalAccessException{
        List<Product> searched = new ArrayList<>();
        Searcher searcher = new Searcher();
        if (line.contains(":")) {
            searched = searcher.getSuitableTypes(line, products, searched);
            if(line.split(":").length>1) {
                line = line.split(":")[1];
            }
        } else {
            searched = products;
        }
        if(line.contains("=")) {
            searched = getAppropriateCharactristics(line, searched);
        }
        return searched;
    }

    private List<Product> getSuitableTypes(String line, List<Product> products, List<Product> searched) {
        String[] arr = line.split(":");
        if (arr[0].split(" |,").length > 1) {
            searched = getAppropriateTypes(arr[0], products, searched);
        } else {
            searched = getAppropriateType(arr[0], products, searched);
        }
        return searched;
    }

    private List<Product> getAppropriateType(String type, List<Product> products, List<Product> searched) {
        type = type.trim();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getClass().getSimpleName().equalsIgnoreCase(type)) {
                searched.add(products.get(i));
            }
        }
        return searched;
    }

    private List<Product> getAppropriateTypes(String types, List<Product> products, List<Product> searched) {
        String[] temp = types.split(" |,");
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getClass().getSimpleName().equalsIgnoreCase(temp[i])) {
                    searched.add(products.get(j));
                }
            }
        }
        return searched;
    }

    private List<Product> getAppropriateCharactristics(String line, List<Product> searched) throws IllegalAccessException{
        String[] arr = line.split(",");
        Product product = new Product();
        List<Product> searching = new ArrayList<>();
        for(String item: arr){
            item = item.trim();
            String [] temp = item.split("=");
            temp[0] = temp[0].toLowerCase();
            searchingProducts(searched,searching,product,temp[0],temp[1]);
        }
        return searching;
    }

    private List<Product> searchingProducts(List<Product>searched, List<Product> searching, Product product, String name, String value) throws IllegalAccessException{
        for(Product obj:searched){
            product = obj;
            Field[] fields = product.getClass().getDeclaredFields();
            for(Field f:fields){
                searching = checkFieldName(f,name,value,product,searching);
            }
        }
        return searching;
    }

    private static List<Product> checkFieldName(Field f,String name, String value, Product product, List<Product> searching) throws IllegalAccessException{
        if (f.getName().equals(name)) {
            f.setAccessible(true);
            String str = value;
            if(f.getType()==Double.class){
                Double a = Double.parseDouble(str);
                str = a.toString();
            }
            if(f.get(product).toString().equals(str)){
                searching.add(product);
            }
        }
        return searching;
    }
}
