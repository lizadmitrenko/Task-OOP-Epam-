package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import handling.Parser;
import handling.Printer;
import handling.Searcher;
import product.Product;


public class Main {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
            Scanner sc = new Scanner(new File("appliances_db.txt"));
            Parser parser;
            List<Product> products = new ArrayList();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                parser = new Parser();
                products.add(parser.parse(line));
            }
            Printer printer = new Printer();
            Searcher searcher = new Searcher();
            List<Product> searched;
            String line = "Refrigerator:POWER_CONSUMPTION=100";
            searched = searcher.search(line, products);
            printer.printProducts(searched);
            line = "Refrigerator:";
            searched = searcher.search(line, products);
            printer.printProducts(searched);
            line = "WIDTH=70";
            searched = searcher.search(line, products);
            printer.printProducts(searched);
            line = "Oven Laptop:";
            searched = searcher.search(line, products);
            printer.printProducts(searched);
            line = "Oven Refrigerator: Width=70";
            searched = searcher.search(line, products);
            printer.printProducts(searched);
            line = "Oven Refrigerator: Width=50";
            searched = searcher.search(line, products);
            printer.printProducts(searched);
    }
}
