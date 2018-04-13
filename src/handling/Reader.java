package handling;

import product.Product;
import java.util.List;
import java.util.Scanner;

public class Reader {
    public static List<Product> readFile(Scanner sc,Parser parser, List<Product>products)throws IllegalAccessException{
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            parser = new Parser();
            products.add(parser.parse(line));
        }
        return products;
    }
}
