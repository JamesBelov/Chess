package src;
public class Knight extends Piece{
    
    public Knight(boolean trueForWhite){
        super(trueForWhite);
        this.setType("Knight");
    }

    public boolean isLegalMove(Square from, Square to, Square[][] b){

        if(from.getPieceOnSquare() == null){
            return false;
        }

        if(to.getPieceOnSquare() != null && getColor() == to.getPieceOnSquare().getColor()){
            return false;
        }

        if( (Math.abs(from.getXPos() - to.getXPos()) == 1 && Math.abs(from.getYPos() - to.getYPos()) == 2) || 
        (Math.abs(from.getYPos() - to.getYPos()) == 1 && Math.abs(from.getXPos() - to.getXPos()) == 2)){

            for(Square[] row : b){
                for(Square s : row){
                    if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType().equals("King") &&
                    s.getPieceOnSquare().getColor() == getColor() && s.isAttacked(b, !s.getPieceOnSquare().getColor())){
                        return false;
                    }
                }
            }

            return true;
        }
        else{
            return false;
        }
    }

    public Knight clone(){
        Knight p = new Knight(getColor());
        p.setHasMoved(hasMoved());
        return p;
    }

}
