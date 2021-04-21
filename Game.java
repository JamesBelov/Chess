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

    public static String CURRENT_TEXTURES; //annoyingly isnt final or private, just dont edit it anywhere
    final public static String DEFAULT_TEXTURES = "def";
    final public static String TEXT_TEXTURES = "text";

    public Game(){
        this(Game.DEFAULT_TEXTURES);
    }

    public Game(String texturePackName){

        CURRENT_TEXTURES = texturePackName;
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
            s.setPieceOnSquare(new Pawn(Piece.BLACK));
        }
        for(Square s : board[6]){
            s.setPieceOnSquare(new Pawn(Piece.WHITE));
        }
        board[0][0].setPieceOnSquare(new Rook(Piece.BLACK));
        board[0][7].setPieceOnSquare(new Rook(Piece.BLACK));
        board[7][0].setPieceOnSquare(new Rook(Piece.WHITE));
        board[7][7].setPieceOnSquare(new Rook(Piece.WHITE));
        board[0][1].setPieceOnSquare(new Knight(Piece.BLACK));
        board[0][6].setPieceOnSquare(new Knight(Piece.BLACK));
        board[7][1].setPieceOnSquare(new Knight(Piece.WHITE));
        board[7][6].setPieceOnSquare(new Knight(Piece.WHITE));
        board[0][2].setPieceOnSquare(new Bishop(Piece.BLACK));
        board[0][5].setPieceOnSquare(new Bishop(Piece.BLACK));
        board[7][2].setPieceOnSquare(new Bishop(Piece.WHITE));
        board[7][5].setPieceOnSquare(new Bishop(Piece.WHITE));
        board[0][3].setPieceOnSquare(new Queen(Piece.BLACK));
        board[7][3].setPieceOnSquare(new Queen(Piece.WHITE));
        board[0][4].setPieceOnSquare(new King(Piece.BLACK));
        board[7][4].setPieceOnSquare(new King(Piece.WHITE));
        
        
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
    private int[] countPieces(boolean white)
    {
        int[] pieces = new int[6]; //King, Queen, Rook, Bishup, Knight, Pawn
        for(Square[] row: board)
        {
            for(Square s: row)
            {
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType() == "King" && s.getPieceOnSquare().getColor() == white) pieces[0]++;
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType() == "Queen" && s.getPieceOnSquare().getColor() == white) pieces[1]++;
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType() == "Rook" && s.getPieceOnSquare().getColor() == white) pieces[2]++;
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType() == "Bishop" && s.getPieceOnSquare().getColor() == white) pieces[3]++;
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType() == "Knight" && s.getPieceOnSquare().getColor() == white) pieces[4]++;
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType() == "Pawn" && s.getPieceOnSquare().getColor() == white) pieces[5]++;
            }
        }
        return pieces;
    }

    private void move(){
        for(Square[] row : board){
            for(Square s : row){
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getColor() == whiteToMove &&
                   s.getPieceOnSquare().hasLegalMove(s, board)){
                    highlight(s);
                    s.getButton().addActionListener((ActionListener) e -> {
                        unhighlightBoard(board);
                        removeActionListenersFromBoard(s, board);
                        ArrayList<Square> legalMoves = s.getPieceOnSquare().getLegalMoves(s, board);
                        for(int i = 0; i < legalMoves.size(); i++){
                            highlight(legalMoves.get(i));//there is a glitch with highlighting during check
                            final int index = i;
                            legalMoves.get(i).getButton().addActionListener((ActionListener) a -> {
                                unhighlightBoard(board);
                                removeActionListenersFromBoard(s, board);
                                checkSpecialMoves(s, legalMoves.get(index), board); //doesnt check for en passant
                                legalMoves.get(index).setPieceOnSquare(s.getPieceOnSquare());
                                
                                s.getPieceOnSquare().onFirstMove();
                                s.setPieceOnSquare(null);
                                whiteToMove = whiteToMove ? false : true;

                                

                                Square whiteKing = null;
                                Square blackKing = null;
                                for(Square[] row1: board)
                                {
                                    for(Square piece : row1)
                                    {
                                        if(piece.getPieceOnSquare() != null && piece.getPieceOnSquare().getType() == "King")
                                        {
                                            if(piece.getPieceOnSquare().getColor())
                                            {
                                                whiteKing = piece;
                                            }
                                            else
                                            {
                                                blackKing = piece;
                                            }
                                        }
                                    }
                                }
                                int[] whitePieces = countPieces(true);
                                int[] blackPieces = countPieces(false);

                                if(whitePieces[5] == 0 && blackPieces[5] == 0   //check for 0 pawns
                                && whitePieces[2] == 0 && blackPieces[2] == 0   //check for 0 rooks
                                && whitePieces[1] == 0 && blackPieces[1] == 0)  //check for 0 queens
                                {
                                    if(whitePieces[3] == 0 && whitePieces[4] == 0   //checks for 0 knights n bishops
                                    && blackPieces[3] == 0 && blackPieces[4] == 0)
                                    {
                                        //just 2 kings left draw
                                        winner.setText("ITS A DRAW");
                                    }
                                    if((whitePieces[3] + whitePieces[4] + blackPieces[3] + blackPieces[4]) == 1)
                                    {
                                        //checks for 1 knight or 1 bishop versus king draw
                                        winner.setText("ITS A DRAW");
                                    }
                                }
                                if(!gameOver()){ //doesnt check for 3 move repetition
                                    move();
                                }
                                else if(!whiteKing.isAttacked(board, false) && !blackKing.isAttacked(board, true)) //checks stalemates
                                {
                                    winner.setText("ITS A DRAW");
                                    //after the previous if statement we can assume there are no legal moves, so if no king is in check it must be a draw
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

    private void removeActionListenersFromBoard(Square sq, Square[][] b){
        for(Square[] row : board){
            for(Square s : row){
                if(s.getButton().getActionListeners().length > 0){
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
        //promote to bishup
        if(from.getPieceOnSquare().getType().equals("Pawn") &&
          (to.getYPos() == 0 || to.getYPos() == 7)){             
            promotionButtonBishop.addActionListener( (ActionListener) c -> {
                removeActionListenersFromPromotionButtons();
                to.setPieceOnSquare(new Bishop(to.getPieceOnSquare().getColor()));
            });
            //promote to a knight
            promotionButtonKnight.addActionListener( (ActionListener) c -> {
                removeActionListenersFromPromotionButtons();
                to.setPieceOnSquare(new Knight(to.getPieceOnSquare().getColor()));
            });
            //promote to a queen
            promotionButtonQueen.addActionListener( (ActionListener) c -> {
                removeActionListenersFromPromotionButtons();
                to.setPieceOnSquare(new Queen(to.getPieceOnSquare().getColor()));              
            });
            //promote to a rook
            promotionButtonRook.addActionListener( (ActionListener) c -> {
                removeActionListenersFromPromotionButtons();
                to.setPieceOnSquare(new Rook(to.getPieceOnSquare().getColor())); 
            });
        }

    }

    private void removeActionListenersFromPromotionButtons(){
        if(promotionButtonBishop.getActionListeners().length > 0){
            for(ActionListener d : promotionButtonBishop.getActionListeners()){
                promotionButtonBishop.removeActionListener(d);
            }
        }
        if(promotionButtonKnight.getActionListeners().length > 0){
            for(ActionListener d : promotionButtonKnight.getActionListeners()){
                promotionButtonKnight.removeActionListener(d);
            }
        }
        if(promotionButtonQueen.getActionListeners().length > 0){
            for(ActionListener d : promotionButtonQueen.getActionListeners()){
                promotionButtonQueen.removeActionListener(d);
            }
        }
        if(promotionButtonRook.getActionListeners().length > 0){
            for(ActionListener d : promotionButtonRook.getActionListeners()){
                promotionButtonRook.removeActionListener(d);
            }
        }
    }

}
