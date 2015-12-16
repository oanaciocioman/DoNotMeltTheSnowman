public class Board {

  private Piece[][] board;
  private Coordinate emitter;

  public Board(Piece[][] board) {
    this.board    = board;
    this.emitter  = PieceUtils.findEmitter(board);
  }

  public boolean laserEnds(Coordinate c) {
    int height = board.length;
    int width = board[0].length;
    int x = c.getX();
    int y = c.getY();
    if(x < 0 || x >= width || y < 0 || y >= height) {
      return true;
    } else if(PieceUtils.isEmitter(board[x][y]) || board[x][y] == Piece.WALL ||
              board[x][y] == Piece.TARGET || board[x][y] == Piece.SNOWMAN) {
        return true;
    }
    return false;
  }

  public Result calculateResult(Coordinate c) {
    int x = c.getX();
    int y = c.getY();
    int height = board.length;
    int width = board[0].length;
    if(x >= 0 && x < width && y >= 0 && y < height) {
      if(board[x][y] == Piece.SNOWMAN) {
        return Result.MELT_SNOWMAN;
      } else if(board[x][y] == Piece.TARGET) {
          return Result.HIT_TARGET;
      }
    }
    return Result.MISS;
  }
  public Result fireLaser() {
    Coordinate current = emitter;
    int x = 0;
    int y = 0;
    boolean isHorizontal;
    Piece currentPiece = board[current.getX()][current.getY()];
    if(currentPiece == Piece.EMITTER_NORTH || currentPiece == Piece.EMITTER_SOUTH) {
      isHorizontal = false;
    } else {
      isHorizontal = true;
    }
    do {
      currentPiece = board[current.getX()][current.getY()];
      PieceUtils.addLaser(currentPiece,isHorizontal);
      Coordinate next = PieceUtils.move(currentPiece, current, x, y);
      int xnext = next.getX();
      int ynext = next.getY();
      x = xnext - current.getX();
      y = ynext - current.getY();
      if(x == 0) {
        isHorizontal = false;
      } else {
          isHorizontal = true;
      }
      current = next;
    } while(!laserEnds(current));
    return calculateResult(current);
  }

  public void rotatePiece(Coordinate c) {
    assert c.getX() >= 0 && c.getX() < board.length
        && c.getY() >= 0 && c.getY() < board[0].length;

    board[c.getX()][c.getY()]
      = PieceUtils.rotate(board[c.getX()][c.getY()]);
  }

  public void clearLasers() {
    for (int i = 0; i < board.length ; i++) {
      for (int j = 0; j < board[i].length ; j++) {
        board[i][j] = PieceUtils.hideLaser(board[i][j]);
      }
    }
  }
private static final char ESC = 27;

  public void renderBoard() {

    System.out.print(ESC + "[30;47m  ");
    for (int i = 0 ; i < board.length ; i++) {
      System.out.print(i);
    }
    System.out.println(" ");

    System.out.print(" ┏");
    for (int i = 0 ; i < board.length ; i++) {
      System.out.print("━");
    }
    System.out.println("┓");

    for (int j = board[0].length - 1 ; j >= 0 ; j--) {
      System.out.print(ESC + "[30m" + j +"┃");
      for (int i = 0 ; i < board.length ; i++ ) {
        System.out.print(renderPiece(board[i][j]));
      }
      System.out.println(ESC + "[30m┃");
    }
    System.out.print(ESC + "[30m ┗");
    for (int i = 0 ; i < board.length ; i++) {
      System.out.print("━");
    }
    System.out.println("┛");

  }

  private static String renderPiece(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
        return ESC + "[32m↑";
      case EMITTER_EAST:
        return ESC + "[32m→";
      case EMITTER_SOUTH:
        return ESC + "[32m↓";
      case EMITTER_WEST:
        return ESC + "[32m←";

      case LASER_VERTICAL:
        return ESC + "[31m│";
      case LASER_HORIZONTAL:
        return ESC + "[31m─";
      case LASER_CROSSED:
        return ESC + "[31m┼";

      case MIRROR_SW_NE:
        return ESC + "[34m╱";
      case MIRROR_NW_SE:
        return ESC + "[34m╲";

      case WALL:
        return ESC + "[36m█";

      case TARGET:
        return ESC + "[35m☼";

      case EMPTY:
        return " ";

      case SNOWMAN:
        return ESC + "[30m☃";
    }
    return "!";
  }

}
