import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game {

    private Square[][] board;
    private boolean whiteToMove;

    private JButton promotionButtonQueen;
    private JButton promotionButtonRook;
    private JButton promotionButtonBishop;
    private JButton promotionButtonKnight;
    private JLabel winner;

    public Game(){

        JFrame f = new JFrame("Chess");
        f.setVisible(true);
        f.setSize(640, 640);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //start board initialization 
        board = new Square[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = new Square(i, j);
                c.gridy = i;
                c.gridx = j;
                p.add(board[i][j].getButton(), c);
            }
        }
        for(Square s : board[1]){
            s.setPieceOnSquare(new Pawn(false));
        }
        for(Square s : board[6]){
            s.setPieceOnSquare(new Pawn(true));
        }
        board[0][0].setPieceOnSquare(new Rook(false));
        board[0][7].setPieceOnSquare(new Rook(false));
        board[7][0].setPieceOnSquare(new Rook(true));
        board[7][7].setPieceOnSquare(new Rook(true));
        board[0][1].setPieceOnSquare(new Knight(false));
        board[0][6].setPieceOnSquare(new Knight(false));
        board[7][1].setPieceOnSquare(new Knight(true));
        board[7][6].setPieceOnSquare(new Knight(true));
        board[0][2].setPieceOnSquare(new Bishop(false));
        board[0][5].setPieceOnSquare(new Bishop(false));
        board[7][2].setPieceOnSquare(new Bishop(true));
        board[7][5].setPieceOnSquare(new Bishop(true));
        board[0][3].setPieceOnSquare(new Queen(false));
        board[7][3].setPieceOnSquare(new Queen(true));
        board[0][4].setPieceOnSquare(new King(false));
        board[7][4].setPieceOnSquare(new King(true));
        //end board initialization

        JPanel bot = new JPanel(new FlowLayout());
        winner = new JLabel();
        bot.add(winner);

        c.gridy = 0;
        c.gridx = 0;
        JPanel t = new JPanel(new GridBagLayout());
        promotionButtonQueen = new JButton("Queen");
        promotionButtonQueen.setPreferredSize(new Dimension(128, 16));
        t.add(promotionButtonQueen, c);
        c.gridx = 1;
        promotionButtonRook = new JButton("Rook");
        promotionButtonRook.setPreferredSize(new Dimension(128, 16));
        t.add(promotionButtonRook, c);
        c.gridx = 2;
        promotionButtonBishop = new JButton("Bishop");
        promotionButtonBishop.setPreferredSize(new Dimension(128, 16));
        t.add(promotionButtonBishop, c);
        c.gridx = 3;
        promotionButtonKnight = new JButton("Knight");
        promotionButtonKnight.setPreferredSize(new Dimension(128, 16));
        t.add(promotionButtonKnight, c);

        t.setLocation(0, 0);
        t.setSize(640, 32);
        f.add(t);
        p.setLocation(0, 32);
        p.setSize(640, 512);
        f.add(p);
        bot.setLocation(0, 544);
        bot.setSize(640, 64);
        f.add(bot);
        f.validate();

        whiteToMove = true;
        move();
    }

    private void move(){
        for(Square[] row : board){
            for(Square s : row){
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getColor() == whiteToMove &&
                   s.getPieceOnSquare().hasLegalMove(s, board)){
                    highlight(s);
                    s.getButton().addActionListener((ActionListener) e -> {
                        unhighlightBoard(board);
                        removeActionListenersFromBoard(s, board, true);
                        ArrayList<Square> legalMoves = s.getPieceOnSquare().getLegalMoves(s, board);
                        for(int i = 0; i < legalMoves.size(); i++){
                            highlight(legalMoves.get(i));//there is a glitch with highlighting during check
                            final int index = i;
                            legalMoves.get(i).getButton().addActionListener((ActionListener) a -> {
                                unhighlightBoard(board);
                                removeActionListenersFromBoard(s, board, true);
                                checkSpecialMoves(s, legalMoves.get(index), board); //doesnt check for en passant or promotion
                                legalMoves.get(index).setPieceOnSquare(s.getPieceOnSquare());
                                s.getPieceOnSquare().onFirstMove();
                                s.setPieceOnSquare(null);
                                removeActionListenersFromBoard(s, board, false);
                                whiteToMove = whiteToMove ? false : true;
                                if(!gameOver()){ //doesnt check for 3 move repetition
                                    move();
                                }
                                else{
                                    //doesnt check for draws
                                    winner.setText(whiteToMove ? "Black Wins!" : "White Wins!");
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    private void highlight(Square s){
        if(!s.isHighlighted()){
            s.setButtonImage(s.getCurrentButtonImagePath().substring(0, s.getCurrentButtonImagePath().length() - 4) + "Highlighted.png");
            s.setHighlighted(true);
        }
        
    }

    private void unhighlightBoard(Square[][] b){
        for(Square[] row : b){
            for(Square s : row){
                if(s.isHighlighted()){
                    s.setButtonImage(s.getCurrentButtonImagePath().substring(0, s.getCurrentButtonImagePath().indexOf("Highlighted")) + ".png");
                    s.setHighlighted(false);
                }
            }
        }
    }

    private void removeActionListenersFromBoard(Square sq, Square[][] b, boolean exceptCurrentSquare){
        for(Square[] row : board){
            for(Square s : row){
                if(s.getButton().getActionListeners().length > 0 && (exceptCurrentSquare ? !s.equals(sq) : true)){
                    for(ActionListener a : s.getButton().getActionListeners()){
                        s.getButton().removeActionListener(a);
                    }
                }
            }
        }
    }

    public boolean gameOver(){
        for(Square[] row : board){
            for(Square s : row){
                if(s.getPieceOnSquare() != null &&
                   s.getPieceOnSquare().getColor() == whiteToMove &&
                   s.getPieceOnSquare().hasLegalMove(s, board)){
                    return false;
                }
            }
        }
        return true;
    }

    public void checkSpecialMoves(Square from, Square to, Square[][] b){
        //check for en passant and remove appropriate pawn
        //check for promotions and switch to appropriate piece

        if(from.getPieceOnSquare().getType().equals("King") &&
        from.getYPos() == 7 && from.getXPos() == 4){
            if(to.getYPos() == 7 && to.getXPos() == 6){
                b[7][5].setPieceOnSquare(b[7][7].getPieceOnSquare());
                b[7][7].getPieceOnSquare().onFirstMove();
                b[7][7].setPieceOnSquare(null);
            }
            if(to.getYPos() == 7 && to.getXPos() == 2){
                b[7][3].setPieceOnSquare(b[7][0].getPieceOnSquare());
                b[7][0].getPieceOnSquare().onFirstMove();
                b[7][0].setPieceOnSquare(null);
            }
        }
        if(from.getPieceOnSquare().getType().equals("King") &&
        from.getYPos() == 0 && from.getXPos() == 4){
            if(to.getYPos() == 0 && to.getXPos() == 6){
                b[0][5].setPieceOnSquare(b[0][7].getPieceOnSquare());
                b[0][7].getPieceOnSquare().onFirstMove();
                b[0][7].setPieceOnSquare(null);
            }
            if(to.getYPos() == 0 && to.getXPos() == 2){
                b[0][3].setPieceOnSquare(b[0][0].getPieceOnSquare());
                b[0][0].getPieceOnSquare().onFirstMove();
                b[0][0].setPieceOnSquare(null);
            }
        }


    }

}
