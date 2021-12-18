import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    public static final int BOARD_SIZE = 8;
    private static final int SQUARE_WIDTH = 100;
    private static final Color LIGHT = new Color(255,230,200);
    private static final Color DARK = new Color(220,180,130);
    private static final Color MOVEMENT = new Color(219, 210, 125);
    private Piece[][] board;
    private int turn;
    private List<BoardState> stateList;
    private List<Move> moveList;
    private static ImageIcon bBishop;
    private static ImageIcon bKing;
    private static ImageIcon bQueen;
    private static ImageIcon bPawn;
    private static ImageIcon bRook;
    private static ImageIcon bKnight;
    private static ImageIcon wBishop;
    private static ImageIcon wKing;
    private static ImageIcon wQueen;
    private static ImageIcon wPawn;
    private static ImageIcon wRook;
    private static ImageIcon wKnight; 
    private Status status;
    
    private Piece whiteKing;
    private Piece blackKing;
    
    private boolean mouseClicked;
    private Position currPos;
    private Piece movingPiece;
    
    public Board() {
        this.reset();
        
        status = new Status(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        bBishop = rescale("BBishop");
        bKing = rescale("BKing");
        bQueen = rescale("BQueen");
        bPawn = rescale("BPawn");
        bRook = rescale("BRook");
        bKnight = rescale("BKnight");
        wBishop = rescale("WBishop");
        wKing = rescale("WKing");
        wQueen = rescale("WQueen");
        wPawn = rescale("WPawn");
        wRook = rescale("WRook");
        wKnight = rescale("WKnight");

        repaint();
    }
    
    public static ImageIcon rescale(String src) {
        ImageIcon temp = new ImageIcon("files\\" + src + ".png");
        return new ImageIcon(temp.getImage().getScaledInstance(SQUARE_WIDTH, 
                                                               SQUARE_WIDTH,
                                                               java.awt.Image.SCALE_SMOOTH));
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        
        super.paintComponent(gc);
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 == 1) {
                    gc.setColor(DARK);
                } else {
                    gc.setColor(LIGHT);
                }
                gc.fillRect(i * SQUARE_WIDTH, j * SQUARE_WIDTH, SQUARE_WIDTH, SQUARE_WIDTH);

            }
        }
        
        try {
            Position i = this.getLastMove().getInit();
            Position d = this.getLastMove().getDest();
            
            gc.setColor(MOVEMENT);
            gc.fillRect(i.getY() * SQUARE_WIDTH, i.getX() * SQUARE_WIDTH,
                        SQUARE_WIDTH, SQUARE_WIDTH);
            gc.fillRect(d.getY() * SQUARE_WIDTH, d.getX() * SQUARE_WIDTH,
                        SQUARE_WIDTH, SQUARE_WIDTH);
        } catch (Exception e) {
        }
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getTeam() == 1) {
                        if (board[i][j].getType() == PieceType.PAWN) {
                            wPawn.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.KNIGHT) {
                            wKnight.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.ROOK) {
                            wRook.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.KING) {
                            wKing.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.QUEEN) {
                            wQueen.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.BISHOP) {
                            wBishop.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        }
                    } else {
                        if (board[i][j].getType() == PieceType.PAWN) {
                            bPawn.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.KNIGHT) {
                            bKnight.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.ROOK) {
                            bRook.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.KING) {
                            bKing.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.QUEEN) {
                            bQueen.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        } else if (board[i][j].getType() == PieceType.BISHOP) {
                            bBishop.paintIcon(this, gc, i * SQUARE_WIDTH, j * SQUARE_WIDTH);
                        }
                        
                    }
                } 
            }
        }
        
        if (mouseClicked) {
            Position pos = getBoardCoordinates(currPos);
            int i = pos.getX();
            int j = pos.getY();
            int x = currPos.getX() - SQUARE_WIDTH / 2;
            int y = currPos.getY() - SQUARE_WIDTH / 2;
            if (movingPiece == null) {
                movingPiece = board[j][i];
                board[j][i] = null;
            } else {
                int team = movingPiece.getTeam();
                PieceType type = movingPiece.getType();
                if (team == 1) {
                    if (type == PieceType.PAWN) {
                        wPawn.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.KNIGHT) {
                        wKnight.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.ROOK) {
                        wRook.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.KING) {
                        wKing.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.QUEEN) {
                        wQueen.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.BISHOP) {
                        wBishop.paintIcon(this, gc, x, y);
                    }
                } else {
                    if (type == PieceType.PAWN) {
                        bPawn.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.KNIGHT) {
                        bKnight.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.ROOK) {
                        bRook.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.KING) {
                        bKing.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.QUEEN) {
                        bQueen.paintIcon(this, gc, x, y);
                    } else if (type == PieceType.BISHOP) {
                        bBishop.paintIcon(this, gc, x, y);
                    }
                }
            }
        }
        

    }
    
    public Board(Piece[][] b, Board template) {
        board = b;
        moveList = template.getMoves();
    }
    
    @Override
    public Dimension preferredSize() {
        return new Dimension(BOARD_SIZE * SQUARE_WIDTH, BOARD_SIZE * SQUARE_WIDTH);
    }

    public Piece[][] getBoard() {
        Piece[][] toReturn = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getType() == PieceType.PAWN) {
                        board[i][j].getPossibleMoves(this);
                    }
                    toReturn[i][j] = board[i][j].copy();
                }
            }
        }
        return toReturn;
    }

    public void setMouseClicked(boolean click) {
        mouseClicked = click;
    }
    
    public int getTurn() {
        return turn;
    }
    
    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Move getLastMove() {
        return moveList.get(moveList.size() - 1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked = true;
        currPos = new Position(e.getX(), e.getY());        
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int currTurn = this.turn;
        mouseClicked = false;
        Move a;
        Position redrawLoc;
        if (movingPiece != null && movingPiece.getTeam() == this.turn) {
            if (movingPiece.getPossibleMoves(this).contains(getBoardCoordinates(currPos))) { 
                a = movingPiece.move(getBoardCoordinates(currPos), this);
                if (a != null && a.getType() == PieceType.PROMOTION) {
                    moveList.add(a);
                    repaint();
                    movingPiece = null;
                    turn *= -1;
                    return;
                } else if (a != null) {
                    Position pos = getBoardCoordinates(currPos);
                    Piece c = board[pos.getY()][pos.getX()];
                    if (c != null && c.getType() != PieceType.KING) {
                        moveList.add(a);
                    } else if (c == null) {
                        moveList.add(a);
                    }
                    turn *= -1;
                }

            }
            redrawLoc = movingPiece.getLocation();
            board[redrawLoc.getY()][redrawLoc.getX()] = movingPiece;
        } else if (movingPiece != null) {
            redrawLoc = movingPiece.getLocation();
            board[redrawLoc.getY()][redrawLoc.getX()] = movingPiece;
        }
        movingPiece = null;

        repaint();

        if (turn == 1 && !hasLegalMoves(1)) {
            if (whiteKing.causesCheck(whiteKing.getLocation(), this)) {
                JOptionPane.showMessageDialog(this, "Black Wins");
            } else {
                JOptionPane.showMessageDialog(this, "Stalemate");
            }
            reset();
        }
        
        if (turn == -1 && !hasLegalMoves(-1)) {
            if (blackKing.causesCheck(blackKing.getLocation(), this)) {
                JOptionPane.showMessageDialog(this, "White wins");
            } else {
                JOptionPane.showMessageDialog(this, "Stalemate");
            }
            reset();
        }
        
        if (!hasSufficientMaterial()) {
            JOptionPane.showMessageDialog(this, "Draw by Insufficient Material");
            reset();
        }
        
        if (this.turn != currTurn) {
            BoardState curr = new BoardState(this.getBoard());
            
            stateList.add(curr);
            int i = 0;
            for (BoardState a1: this.stateList) {
                if (a1.equals(curr)) {
                    i++;
                }
            }
            
            if (i >= 3) {
                JOptionPane.showMessageDialog(this, "Draw by Threefold Repetition");
                reset();
            }
            
        }
        
        status.updateStatus();
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseClicked = false;
        Position redrawLoc;
        if (movingPiece != null && movingPiece.getTeam() == this.turn) {
            redrawLoc = movingPiece.getLocation();
            board[redrawLoc.getY()][redrawLoc.getX()] = movingPiece;
        } else if (movingPiece != null) {
            redrawLoc = movingPiece.getLocation();
            board[redrawLoc.getY()][redrawLoc.getX()] = movingPiece;
        }
        movingPiece = null;
        
        status.updateStatus();
      
        repaint();
    }
    
    public Position getBoardCoordinates(Position x) {
        int i = x.getY() / SQUARE_WIDTH;
        int j = x.getX() / SQUARE_WIDTH;
        return new Position(i,j);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currPos = new Position(e.getX(), e.getY());
        status.updateStatus();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public Piece getKing(int team) {
        if (team == 1) {
            Position pos = whiteKing.getLocation();
            return new King(pos, team);
        } else {
            Position pos = blackKing.getLocation();
            return new King(pos, team);
        }
    }
    
    public List<Move> getMoves() {
        List<Move> toReturn = new LinkedList<Move>();
        for (Move m: moveList) {
            toReturn.add(m);
        }
        return toReturn;
    }
    
    public void addMove(Move x) {
        moveList.add(x);
    }
    
    
    public void editBoard(int i, int j, Piece c) {
        board[j][i] = c;
    }
    
    public void undo() {
        if (stateList.size() > 2) {
            board = stateList.get(stateList.size() - 2).getBoard();
            stateList.remove(stateList.size() - 1);
            moveList.remove(moveList.size() - 1);
            
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] != null && board[i][j].getType() == PieceType.KING) {
                        if (board[i][j].getTeam() == 1) {
                            whiteKing = board[i][j];
                        } else {
                            blackKing = board[i][j];
                        }
                    }
                }
            }
            
            turn *= -1;
        } else {
            reset();
        }
        
        repaint();
        status.updateStatus();
    }
    
    private boolean hasLegalMoves(int team) {
        for (Piece[] v: board) {
            for (Piece p: v) {
                if (p != null && p.getTeam() == team) {
                    for (Position i : p.getPossibleMoves(this)) {
                        if (!p.causesCheck(i, this)) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public void reset() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        
        stateList = new LinkedList<BoardState>();
        
        stateList.add(new BoardState(this.getBoard()));
        
        turn = 1;
        try {
            status.updateStatus();
        } catch (Exception e) {
        }
        mouseClicked = false;
        
        moveList = new LinkedList<Move>();
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (j == 1) {
                    board[i][j] = new Pawn(new Position(j, i), -1);
                } else if (j == 6) {
                    board[i][j] = new Pawn(new Position(j, i), 1);
                } else if ((i == 0 || i == 7) && j == 0) {
                    board[i][j] = new Rook(new Position(j, i), -1);
                } else if ((i == 0 || i == 7) && j == 7) {
                    board[i][j] = new Rook(new Position(j, i), 1);
                } else if ((i == 1 || i == 6) && j == 0) {
                    board[i][j] = new Knight(new Position(j, i), -1);
                } else if ((i == 1 || i == 6) && j == 7) {
                    board[i][j] = new Knight(new Position(j, i), 1);
                } else if ((i == 2 || i == 5) && j == 0) {
                    board[i][j] = new Bishop(new Position(j, i), -1);
                } else if ((i == 2 || i == 5) && j == 7) {
                    board[i][j] = new Bishop(new Position(j, i), 1);
                } else if (i == 4 && j == 0) {
                    blackKing = new King(new Position(j, i), -1);
                    board[i][j] = blackKing;
                } else if (i == 4 && j == 7) {
                    whiteKing = new King(new Position(j, i), 1);
                    board[i][j] = whiteKing;
                } else if (i == 3 && j == 0) {
                    board[i][j] = new Queen(new Position(j, i), -1);
                } else if (i == 3 && j == 7) {
                    board[i][j] = new Queen(new Position(j, i), 1);
                } 
                
            }
        }
        
        repaint();
    }

    public JPanel getStatus() {
        return status;
    }
    
    private boolean hasSufficientMaterial() {
        int whiteBishopCount = 0;
        int whiteKnightCount = 0;
        int blackKnightCount = 0;
        int blackBishopCount = 0;
        Set<PieceType> white = new HashSet<PieceType>();
        Set<PieceType> black = new HashSet<PieceType>();
        
        for (Piece[] v: board) {
            for (Piece p: v) {
                if (p != null) {
                    if (p.getTeam() == 1) {
                        white.add(p.getType());
                        
                        if (p.getType() == PieceType.BISHOP) {
                            whiteBishopCount++;
                        } else if (p.getType() == PieceType.KNIGHT) {
                            whiteKnightCount++;
                        }
                        
                    } else {
                        black.add(p.getType());
                        
                        if (p.getType() == PieceType.BISHOP) {
                            blackBishopCount++;
                        } else if (p.getType() == PieceType.KNIGHT) {
                            blackKnightCount++;
                        }
                        
                    } 

                }
            }
        }
        if (white.size() < 3 && black.size() < 3) {
            if (!white.contains(PieceType.QUEEN) &&
                    !white.contains(PieceType.PAWN) &&
                    !white.contains(PieceType.ROOK) &&
                    !black.contains(PieceType.QUEEN) &&
                    !black.contains(PieceType.ROOK) &&
                    !black.contains(PieceType.PAWN)) {
                if ((whiteBishopCount < 2 && whiteKnightCount < 3) &&
                        (blackBishopCount < 2 && blackKnightCount < 3)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Piece[][] getPieces() {
        Piece[][] toReturn = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null) {
                    toReturn[i][j] = board[i][j].copy();
                }
            }
        }
        return toReturn;
    }
}
