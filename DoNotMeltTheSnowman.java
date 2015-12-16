public class DoNotMeltTheSnowman {

  public static void main(String[] args) {
    assert(args.length > 0):
      "Usage: DoNotMeltTheSnowman <level>";

    int levelNumber = Integer.parseInt(args[0]);
    Level level = Levels.getLevels()[levelNumber];

    Board board = new Board(PieceUtils.charsToPieces(
      level.getCharArray(),
      level.getWidth(),
      level.getHeight()));

    System.out.println("Welcome!");

    while(true) {
      Result result = board.fireLaser();
      board.renderBoard();

      if(result == Result.MELT_SNOWMAN) {
        System.out.println("You lose!");
        break;
      } else if(result == Result.HIT_TARGET) {
          System.out.println("You won!");
          break;
      }

      System.out.println("Please enter a row and a column number:");
      int row = IOUtil.readInt();
      int column = IOUtil.readInt();

      board.rotatePiece(new Coordinate(column, row));

      board.clearLasers();
    }
  }

}
