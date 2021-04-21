import java.util.ArrayList;

public abstract class Piece {
    
    private boolean hasMoved;
    private boolean color;
    private String type;
    private boolean enpAble = false; //en passant able

    final public static boolean WHITE = true;
    final public static boolean BLACK = false;

    public Piece(boolean trueForWhite){
        color = trueForWhite;
        hasMoved = false;
        type = "null";
    }

    protected void setHasMoved(boolean b){
        hasMoved = b;
    }

    public boolean getColor(){
        return color;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public boolean hasLegalMove(Square from, Square[][] b){
        if(this.getLegalMoves(from, b).size() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public ArrayList<Square> getLegalMoves(Square from, Square[][] b){
        ArrayList<Square> legalMoves = new ArrayList<Square>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(from.getPieceOnSquare() != null && from.getPieceOnSquare().isLegalMove(from, b[i][j], b)){
                    legalMoves.add(b[i][j]);
                }
            }
        }
        return legalMoves;
    }

    public void onFirstMove(){
        hasMoved = true;
    }

    public boolean hasMoved(){
        return hasMoved;
    }
    //gets and sets to see if you can en passant 
    public boolean getenpAble()
    {
        return enpAble;
    }
    public void setenpAble(boolean a)
    {
        enpAble = a;
    }

    public abstract boolean isLegalMove(Square from, Square to, Square[][] b); 

    public abstract Piece clone();

}
