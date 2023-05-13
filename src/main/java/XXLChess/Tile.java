package XXLChess;

/*
 * Tile.java begins
 */



class Tile {
	public int x; // Pixel Value
	public int y; // Pixel Value

	public int x_idx; // From left to right 1~16
	public int y_idx; // From up to down 1~16

	protected boolean isWhite;
	protected boolean isChessPiece;

	/*
	 * Six attributes in which isAbleCaptureLRed and isAbleMoveBlue are related
	 */
	protected boolean isLightBrown; // Is light or dark brown tile: this is set for all attributes
									// later
	protected boolean isDarkBrown; // Invert

	protected boolean isCheckedDRed; // Is being checked and showing `dark red`
	protected boolean isPastMoveYellow; // Is indicating a past move
	protected boolean isAbleCaptureLRed; // Is able to be captured and showing the color `light red`
	protected boolean isAbleMoveBlue; // Is highlighting possible moves
	protected boolean isSelectedGreen; // Is being selected and showing `green`

	/*
	 * Constructor for Tile: only evoked when reading the `level1.txt` file
	 */
	public Tile(int x_idx, int y_idx) {
		// this.pieceID = pieceID;
		this.x_idx = x_idx;
		this.y_idx = y_idx;
		this.x = App.CELLSIZE * this.x_idx;
		this.y = App.CELLSIZE * this.y_idx;

		/*
		 * Automatically select the `isLightBrown` value
		 */
		// If the parity of the x_idx and y_idx are the same:
		// then light brown
		if (((this.x_idx % 2 == 0) && (this.y_idx % 2 == 0))
				|| ((this.x_idx % 2 == 1) && (this.y_idx % 2 == 1))) {
			this.isLightBrown = true;
		} else {
			this.isLightBrown = false;
		}
		this.isDarkBrown = !isLightBrown;

		/*
		 * Initialize other values to `false`
		 */
		this.isCheckedDRed = false;
		this.isPastMoveYellow = false;
		this.isAbleCaptureLRed = false;
		this.isAbleMoveBlue = false;
		this.isSelectedGreen = false;
	}

	/*
	 * We update attributes for every single frame This method is call in the `draw()` method
	 */
	public void tickATile() {

	}

	/*
	 * For each individual `tile`, we call this function to draw it (x,y) with (app.CELLSIZE,
	 * app.CELLSIZE) Note that this is a non-static method This method is called in the `draw()`
	 * method
	 */
	public void drawATile(App app) {

		/*
		 * Six attributes in which isAbleCaptureLRed and isAbleMoveBlue are related
		 */
		if (isCheckedDRed) {
			app.fill(140, 0, 0); // Dark red
		} else if (isPastMoveYellow) {
			if (isLightBrown) {
				app.fill(255, 255, 153); // Light yellow
			} else {
				app.fill(170, 162, 58); // Dark yellow
			}
		} else if (isAbleCaptureLRed) {
			if (isLightBrown) {
				app.fill(255, 153, 153); // Light red
			} else {
				app.fill(255, 0, 0); // Red
			}
		} else if (isAbleMoveBlue) {
			if (isLightBrown) {
				app.fill(153, 204, 255); // Light blue
			} else {
				app.fill(0, 102, 204); // Dark blue
			}
		} else if (isSelectedGreen) {
			app.fill(0, 102, 0); // Dark green
		} else {
			if (isLightBrown) {
				app.fill(240, 217, 181); // Light brown
			} else {
				app.fill(181, 136, 99); // Dark brown
			}
		}
		app.rect(x, y, App.CELLSIZE, App.CELLSIZE);
	}

	public void drawCoor(App app) {
		// Setting the color of the text to be white
		app.fill(255);
		// Setting the text size
		app.textSize(10);
		// Draw the indices on the tile
		app.text("x:" + Integer.toString(this.x_idx + 1), x + 10, y + 5);
		app.text("y:" + Integer.toString(this.y_idx + 1), x + 10, y + 20);
	}

	@Override
	public String toString() {
		String ans = "Coor: (" + this.x_idx + ", " + this.y_idx + ");";
		return ans;
	}

	protected void setAbleCaptureLRed() {
		this.isAbleCaptureLRed = true;
		this.isAbleMoveBlue = true;
	}

	protected void AIsetAbleCaptureLRed() {
		this.isAbleCaptureLRed = false;
		this.isAbleMoveBlue = false;
	}

	protected void setAbleMoveBlue() {
		this.isAbleMoveBlue = true;
	}

	protected void AIsetAbleMoveBlue() {

		this.isAbleMoveBlue = false;
	}
}
