package XXLChess;

import processing.core.PApplet;

// public String playerColour;
// public double pieceMovementSpeed;
// public int maxMovementTime;
// public int playerSeconds;
// public int playerIncrement;
// public int cpuSeconds;
// public int cpuIncrement;

class Sidebar {

    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public static void drawTimers(App app, int player1Time, int player2Time) {
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.textSize(24);

        // Draw the timer for Player 1 in the upper-right corner
        String player1TimeString = formatTime(player1Time);
        int x1 = App.WIDTH - App.SIDEBAR / 2;
        int y1 = App.HEIGHT / 4;
        app.fill(250, 250, 250);
        app.text(player1TimeString, x1, y1);
        y1 += 3;

        // Draw the timer for Player 2 in the lower-right corner
        String player2TimeString = formatTime(player2Time);
        int x2 = App.WIDTH - App.SIDEBAR / 2;
        int y2 = 3 * App.HEIGHT / 4;
        app.fill(250, 250, 250);
        app.text(player2TimeString, x2, y2);
    }
}
