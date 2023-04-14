package XXLChess;

// import processing.core.PApplet;

class Background {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public int a = 189;
    public int b = 186;
    public int c = 190;

    public static void drawCheckerboard(App app) {
=======
    private static void drawCheckerboard(App app) {
>>>>>>> parent of 004547d (Two clocks working while just adding on top of each other)
=======
    private static void drawCheckerboard(App app) {
>>>>>>> parent of 004547d (Two clocks working while just adding on top of each other)
=======
    private static void drawCheckerboard(App app) {
>>>>>>> parent of 004547d (Two clocks working while just adding on top of each other)
        for (int row = 0; row < 14; row++) {
            for (int col = 0; col < 14; col++) {
                if ((row + col) % 2 == 0) {
                    app.fill(190, 147, 112); // White
                } else {
                    app.fill(247, 222, 191); // Black
                }
                app.rect(col * 48, row * 48, 48, 48);
            }
        }
    }

    private static void drawSideBar(App app) {
        app.fill(189, 186, 190);
        int x1 = App.CELLSIZE * App.BOARD_WIDTH;
        int y1 = 0;
        int x2 = App.WIDTH;
        int y2 = App.HEIGHT;
        app.rect(x1, y1, x2, y2);
    }
    
    public void drawDynamic(App app) {
        
        // app.fill(189, 186, 190);
        app.fill(a++, b--, c--);
        System.out.println(a + "," + b + "," + c);
        int x1 = App.CELLSIZE * App.BOARD_WIDTH;
        int y1 = 0;
        int x2 = App.WIDTH;
        int y2 = App.HEIGHT;
        app.rect(x1, y1, x2, y2);
    }

    public static void drawBackground(App app) {
        drawCheckerboard(app);
        drawSideBar(app);
    }
}