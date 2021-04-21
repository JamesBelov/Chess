package src;
public class King extends Piece{

    
    public King(boolean trueForWhite){
        super(trueForWhite);
        this.setType("King");
    }

    public boolean isLegalMove(Square from, Square to, Square[][] b){

        if(from.getPieceOnSquare() == null){
            return false;
        }

        if(to.getPieceOnSquare() != null && getColor() == to.getPieceOnSquare().getColor()){
            return false;
        }

        if(!from.getPieceOnSquare().hasMoved()){
            if(getColor()){

                if(b[7][7].getPieceOnSquare() != null && !b[7][7].getPieceOnSquare().hasMoved() &&
                to.getYPos() == 7 && to.getXPos() == 6 &&
                !b[7][6].isAttacked(b, !getColor()) &&
                b[7][6].getPieceOnSquare() == null &&
                !b[7][5].isAttacked(b, !getColor()) &&
                b[7][5].getPieceOnSquare() == null){
                    return true;
                }

                if(b[7][0].getPieceOnSquare() != null && !b[7][0].getPieceOnSquare().hasMoved() &&
                to.getYPos() == 7 && to.getXPos() == 2 &&
                !b[7][1].isAttacked(b, !getColor()) &&
                b[7][1].getPieceOnSquare() == null &&
                !b[7][2].isAttacked(b, !getColor()) &&
                b[7][2].getPieceOnSquare() == null &&
                !b[7][3].isAttacked(b, !getColor()) &&
                b[7][3].getPieceOnSquare() == null){
                    return true;
                }

            }else{

                if(b[0][7].getPieceOnSquare() != null && !b[0][7].getPieceOnSquare().hasMoved() &&
                to.getYPos() == 0 && to.getXPos() == 6 &&
                !b[0][6].isAttacked(b, !getColor()) &&
                b[0][6].getPieceOnSquare() == null &&
                !b[0][5].isAttacked(b, !getColor()) &&
                b[0][5].getPieceOnSquare() == null){
                    return true;
                }

                if(b[0][0].getPieceOnSquare() != null && !b[0][0].getPieceOnSquare().hasMoved() &&
                to.getYPos() == 0 && to.getXPos() == 2 &&
                !b[0][1].isAttacked(b, !getColor()) &&
                b[0][1].getPieceOnSquare() == null &&
                !b[0][2].isAttacked(b, !getColor()) &&
                b[0][2].getPieceOnSquare() == null &&
                !b[0][3].isAttacked(b, !getColor()) &&
                b[0][3].getPieceOnSquare() == null){
                    return true;
                }

            }
        }
        
        if(Math.abs(to.getXPos() - from.getXPos()) <= 1 && 
        Math.abs(to.getYPos() - from.getYPos()) <= 1 && 
        !(to.getXPos() == from.getXPos() && to.getYPos() ==  from.getYPos())){
            if(to.isAttacked(b, !getColor())){
                return false;
            }
            return true;
        }
        else{
            return false;
        }
        
    }

    public King clone(){
        King p = new King(getColor());
        p.setHasMoved(hasMoved());
        return p;
    }

}
