public class Bishop extends Piece{
    
    public Bishop(boolean trueForWhite){
        super(trueForWhite);
        this.setType("Bishop");
    }

    public boolean isLegalMove(Square from, Square to, Square[][] b){

        if(from.getPieceOnSquare() == null){
            return false;
        }

        if(to.getPieceOnSquare() != null && getColor() == to.getPieceOnSquare().getColor()){
            return false;
        }

        if(from.getXPos() != to.getXPos() && Math.abs(from.getXPos() - to.getXPos()) == Math.abs(from.getYPos() - to.getYPos())){

            if(from.getXPos() > to.getXPos()){
                if(from.getYPos() > to.getYPos()){
                    int j = from.getXPos() - 1;
                    for(int i = from.getYPos() - 1; i > to.getYPos(); i--){
                        if(b[i][j].getPieceOnSquare() != null){
                            return false;
                        }
                        j--;
                    }
                }
                else{
                    int j = from.getXPos() - 1;
                    for(int i = from.getYPos() + 1; i < to.getYPos(); i++){
                        if(b[i][j].getPieceOnSquare() != null){
                            return false;
                        }
                        j--;
                    }
                }
            }else{
                if(from.getYPos() > to.getYPos()){
                    int j = from.getXPos() + 1;
                    for(int i = from.getYPos() - 1; i > to.getYPos(); i--){
                        if(b[i][j].getPieceOnSquare() != null){
                            return false;
                        }
                        j++;
                    }
                }
                else{
                    int j = from.getXPos() + 1;
                    for(int i = from.getYPos() + 1; i < to.getYPos(); i++){
                        if(b[i][j].getPieceOnSquare() != null){
                            return false;
                        }
                        j++;
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


    public Bishop clone(){
        Bishop p = new Bishop(getColor());
        p.setHasMoved(hasMoved());
        return p;
    }

}
