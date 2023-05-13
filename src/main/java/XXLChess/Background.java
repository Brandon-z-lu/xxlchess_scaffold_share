package XXLChess;

/*
 * Background.java begins
 */

class Background {

    public static void drawSideBar(App app) {
        int x1 = App.CELLSIZE * App.BOARD_WIDTH;
        int y1 = App.HEIGHT;
        int x2 = App.WIDTH;
        int y2 = 0;
        app.fill(189, 186, 190);
        app.rect(x1, y1, x2, y2);
    }

    public static void refreshFrame(App app) {
        // Refresh the frame
        app.fill(189, 186, 190);
        app.rect(-1, -1, App.WIDTH + 2, App.HEIGHT + 2);
        drawSideBar(app);
    }

        
}