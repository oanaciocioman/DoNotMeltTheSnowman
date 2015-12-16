 public class PieceUtils {

  public static Piece charToPiece(char c) {
    switch (c) {
      case '^': return Piece.EMITTER_NORTH;
      case '>': return Piece.EMITTER_EAST;
      case 'v': return Piece.EMITTER_SOUTH;
      case '<': return Piece.EMITTER_WEST;
      case '|': return Piece.LASER_VERTICAL;
      case '-': return Piece.LASER_HORIZONTAL;
      case '+': return Piece.LASER_CROSSED;
      case '/': return Piece.MIRROR_SW_NE;
      case '\\': return Piece.MIRROR_NW_SE;
      case '#': return Piece.WALL;
      case 'o': return Piece.TARGET;
      case ' ': return Piece.EMPTY;
      case '@': return Piece.SNOWMAN;
    }
    return null;
  }

  public static Piece[][] charsToPieces(char[] description,
                                        int width, int height) {
    Piece[][] charstoPieces = new Piece[width][height];
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        charstoPieces[j][i] = charToPiece(description[(height-i-1)*width + j]);
      }
    }
    return charstoPieces;
  }

  public static boolean isEmitter(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
      case EMITTER_EAST:
      case EMITTER_SOUTH:
      case EMITTER_WEST:
        return true;
    }

    return false;
  }
 public static Coordinate findEmitter(Piece[][] pieces) {
    int height = pieces.length;
    int width = pieces[0].length;
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        if(isEmitter(pieces[i][j])) {
          Coordinate coord = new Coordinate(i,j);
          return coord;
        }
      }
    }
    return null;
  }

  public static Piece hideLaser(Piece p) {
    switch (p) {
      case LASER_VERTICAL:
      case LASER_HORIZONTAL:
      case LASER_CROSSED:
        return Piece.EMPTY;
    }
    return p;
  }

  public static Piece addLaser(Piece p, boolean isHorizontal) {
    if(isHorizontal) {
      if(p == Piece.EMPTY) {
        return Piece.LASER_HORIZONTAL;
      } else if(p == Piece.LASER_VERTICAL) {
          return Piece.LASER_CROSSED;
      } else {
          return p;
      }
    } else {
        if(p == Piece.EMPTY) {
          return Piece.LASER_VERTICAL;
      } else if(p == Piece.LASER_HORIZONTAL) {
          return Piece.LASER_CROSSED;
      } else {
          return p;
      }
    }
  }

 public static Coordinate move(Piece p, Coordinate c, int xo, int yo) {
    if(isEmitter(p)) {
      switch(p) {
        case EMITTER_NORTH: return (new Coordinate (c.getX(), c.getY() + 1));
        case EMITTER_EAST: return (new Coordinate (c.getX() + 1, c.getY()));
        case EMITTER_SOUTH: return (new Coordinate (c.getX(), c.getY() - 1));
        case EMITTER_WEST: return (new Coordinate (c.getX() - 1, c.getY()));
      }
    } else if(p == Piece.EMPTY || p == Piece.LASER_HORIZONTAL ||
              p == Piece.LASER_VERTICAL || p == Piece.LASER_CROSSED) {
        return (new Coordinate (c.getX() + xo, c.getY() + yo));
    } else if(p == Piece.MIRROR_SW_NE) {
        if(xo == -1 && yo == 0) {
          return (new Coordinate (c.getX(), c.getY() - 1));
        } else if(xo == 1 && yo == 0) {
            return (new Coordinate (c.getX(), c.getY() + 1));
        } else if(xo == 0 && yo == -1) {
            return (new Coordinate (c.getX() - 1, c.getY()));
        } else if(xo == 0 && yo == 1) {
            return (new Coordinate (c.getX() + 1, c.getY()));
        }
    } else if(p == Piece.MIRROR_NW_SE) {
        if(xo == -1 && yo == 0) {
          return (new Coordinate (c.getX(), c.getY() + 1));
        } else if(xo == 1 && yo == 0) {
            return (new Coordinate (c.getX(), c.getY() - 1));
        } else if(xo == 0 && yo == -1) {
            return (new Coordinate (c.getX() + 1, c.getY()));
        } else if(xo == 0 && yo == 1) {
            return (new Coordinate (c.getX() - 1, c.getY()));
        }
    }
    return c;
  }
  
  public static Piece rotate(Piece p) {
    switch (p) {
      case EMITTER_NORTH:
        return Piece.EMITTER_EAST;
      case EMITTER_EAST:
        return Piece.EMITTER_SOUTH;
      case EMITTER_SOUTH:
        return Piece.EMITTER_WEST;
      case EMITTER_WEST:
        return Piece.EMITTER_NORTH;
      case MIRROR_SW_NE:
        return Piece.MIRROR_NW_SE;
      case MIRROR_NW_SE:
        return Piece.MIRROR_SW_NE;
    }
    return p;
  }

}
