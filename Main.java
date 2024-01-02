public class Main {
    public static void main(String[] args) {
        Board b = new Board(4, 5);
        Game g = new Game(b);
        g.run();
    }
}
