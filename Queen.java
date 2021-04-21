public class Queen extends Piece{
    
    public Queen(boolean trueForWhite){
        super(trueForWhite);
        this.setType("Queen");
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

            Square[][] iB = new Square[8][8];
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    iB[i][j] = b[i][j].clone();
                }
            }
            iB[to.getYPos()][to.getXPos()].setPieceOnSquare(iB[from.getYPos()][from.getXPos()].getPieceOnSquare());
            iB[from.getYPos()][from.getXPos()].setPieceOnSquare(null);
            for(Square[] row : b){
                for(Square s : row){
                    if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType().equals("King") &&
                    s.getPieceOnSquare().getColor() == getColor() && s.isAttacked(b, !s.getPieceOnSquare().getColor())){
                        return false;
                    }
                }
            }

            return true; //rook
        }
        else if(from.getXPos() != to.getXPos() && Math.abs(from.getXPos() - to.getXPos()) == Math.abs(from.getYPos() - to.getYPos())){
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

            return true; //bishop
        }
        else{
            return false;
        }
    }


    public Queen clone(){
        Queen p = new Queen(getColor());
        p.setHasMoved(hasMoved());
        return p;
    }

}

