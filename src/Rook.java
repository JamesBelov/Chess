package src;
public class Rook extends Piece{
    
    public Rook(boolean trueForWhite){
        super(trueForWhite);
        this.setType("Rook");
    }

    public boolean isLegalMove(Square from, Square to, Square[][] b){

        if(from.getPieceOnSquare() == null){
            return false;
        }

        if(to.getPieceOnSquare() != null && getColor() == to.getPieceOnSquare().getColor()){
            return false;
        }

        if(from.getXPos() == to.getXPos() ? from.getYPos() != to.getYPos() : from.getYPos() == to.getYPos()){       

            for(Square[] row : b){
                for(Square s : row){
                    if(s.getPieceOnSquare() != null && 
                    (from.getXPos() == s.getXPos() ? 
                    from.getYPos() != s.getYPos() : 
                    from.getYPos() == s.getYPos()) &&
                    (from.getXPos() == to.getXPos() ? 
                    (from.getYPos() > to.getYPos() ? 
                    (from.getYPos() > s.getYPos() && s.getYPos() > to.getYPos()) : 
                    (from.getYPos() < s.getYPos() && s.getYPos() < to.getYPos())) :
                    (from.getXPos() > to.getXPos() ? 
                    (from.getXPos() > s.getXPos() && s.getXPos() > to.getXPos()) : 
                    (from.getXPos() < s.getXPos() && s.getXPos() < to.getXPos()))) ){
                        return false;
                    }
                }
            }

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

    public Rook clone(){
        Rook p = new Rook(getColor());
        p.setHasMoved(hasMoved());
        return p;
    }

}
