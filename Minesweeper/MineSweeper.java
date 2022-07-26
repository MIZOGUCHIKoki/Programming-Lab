
interface MineSweeperGUI {
	public void setTextToTile(int x, int y, String text);

	public void setColorText(int x, int y, int num);

	public void setColor(int x, int y);

	public void win();

	public void lose();
}

public class MineSweeper {

	private final int height;
	private final int width;
	private final int numTile;
	private final int numMine;
	private final int[][] table;
	private final int[][] table_copy; // 初期の盤面状態を保存する配列

	public MineSweeper(int height, int width, int numMine) {
		this.height = height;
		this.width = width;
		this.numTile = height * width;
		this.numMine = numMine;
		this.table = new int[height][width];
		this.table_copy = new int[height][width];
		initTable();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	/*
	 * 爆弾 : -1
	 * 爆弾以外 : 0
	 * フラグ : -2
	 * 
	 * 開かれた : 1
	 */

	void initTable() {// 盤面を初期化する
		/* ----- add implementation here ----- */
		this.setMine();
		for (int x = 0; x < this.height; x++) {
			for (int y = 0; y < this.width; y++) {
				if (this.table[x][y] == -1) { // 爆弾がセットされている場所は避ける
					continue;
				}
				this.table[x][y] = 0; // 爆弾の場所以外は0で初期化
				this.table_copy[x][y] = 0;
			}
		}
	}

	void setMine() {// 爆弾をセット
		/* ----- add implementation here ----- */
		int count = 0;
		while (count != this.numMine) {// numMineの数だけ爆弾をセットできたらループを抜ける
			int x = new java.util.Random().nextInt(getHeight());
			int y = new java.util.Random().nextInt(getWidth());
			if (this.table[x][y] == -1) {
				// [x][y]にすでに爆弾がセットされていたら、もう一度乱数を決め直す
				continue;
			}
			this.table[x][y] = -1; // 爆弾の場所を値-1としてセットする
			this.table_copy[x][y] = -1;
			count++;
		}
	}

	public void openTile(int x, int y, MineSweeperGUI gui) {
		/* ----- add implementation here ----- */
		if (this.table[x][y] == -1) { // パネルに爆弾があった場合
			this.openAllTiles(gui);
			gui.lose();
			for (int i = 0; i < getHeight(); i++) {
				for (int j = 0; j < getWidth(); j++) {
					this.table[i][j] = -2;
				}
			}
		} else if (this.table[x][y] == -2) { // パネルに旗が立っている場合
			return;
		} else { // パネルに爆弾がなかった場合
			int mineCount = this.returnMine(x, y, gui);// 爆弾を調査
			this.table[x][y] = 1; // 開かれたパネルの値を1に設定
			String mc = String.valueOf(mineCount);
			gui.setColorText(x, y, mineCount);// 色を設定
			gui.setTextToTile(x, y, mc); // 爆弾の個数を表示
			int jud = 0;
			for (int i = 0; i < getHeight(); i++) {
				for (int j = 0; j < getWidth(); j++) {
					// 爆弾以外のパネルが全て開いているか確認
					if (this.table[i][j] == 0) {
						// 開いていないパネルがある場合judの値を0にする
						jud = 0;
						break;
					} else {
						// 開いている場合1にする
						jud = 1;
					}
				}
				if (jud == 0) {
					break;
				}
			}
			if (jud == 1) {
				gui.win();
			}
		}
	}

	public int returnMine(int x, int y, MineSweeperGUI gui) {
		int mineCount = 0;
		int j2, i2;
		// 開かれたパネルに隣接する8マス内の爆弾を調査
		for (i2 = x - 1; i2 < x + 2; i2++) {
			if (i2 < 0 || i2 >= getHeight()) { // パネルの範囲外は除く
				continue;
			}
			for (j2 = y - 1; j2 < y + 2; j2++) {
				if (j2 < 0 || j2 >= getWidth()) { // パネルの範囲外は除く
					continue;
				}
				if (this.table_copy[i2][j2] == -1) { // 爆弾の個数をカウント
					mineCount++;
				}
			}
		}
		return mineCount;
	}

	public void setFlag(int x, int y, MineSweeperGUI gui) {
		// /* ----- add implementation here ----- */
		if (this.table[x][y] == 0 || this.table[x][y] == -1) {
			this.table[x][y] = -2; // 旗を立てる場所に-2を入れる
			gui.setTextToTile(x, y, "F");
		} else if (this.table[x][y] == -2) {
			// すでに旗が立っているときは元の盤面の値に戻す
			this.table[x][y] = this.table_copy[x][y];
			gui.setTextToTile(x, y, "");
		}
	}

	private void openAllTiles(MineSweeperGUI gui) {
		/* ----- add implementation here ----- */
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				if (this.table_copy[x][y] == -1) {
					gui.setTextToTile(x, y, "B"); // 爆弾がある場所にBを表示
					gui.setColor(x, y);
				} else {
					Integer i = this.returnMine(x, y, gui);
					gui.setColorText(x, y, i);
					gui.setTextToTile(x, y, i.toString());// 他の全ての盤面を表示
				}
			}
		}
	}
}
