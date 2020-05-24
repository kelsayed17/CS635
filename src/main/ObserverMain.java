package main;

import observer.Parser;

import java.io.IOException;

public class ObserverMain {
    @SuppressWarnings({"InfiniteLoopStatement", "StatementWithEmptyBody"})
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        parser.parseFile("data/Sample.txt");

        while (true) ;
    }
}
