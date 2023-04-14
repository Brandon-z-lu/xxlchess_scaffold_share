package XXLChess;

// import processing.core.PApplet;

class Background {
    public static void drawCheckerboard(App app) {
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

    public static void drawSideBar(App app) {
        int x1 = App.CELLSIZE * App.BOARD_WIDTH;
        int y1 = App.HEIGHT;
        int x2 = App.WIDTH;
        int y2 = 0;
        app.fill(189, 186, 190);
        app.rect(x1, y1, x2, y2);
    }

    public static void drawBackground(App app) {
        drawCheckerboard(app);
        drawSideBar(app);
    }
}