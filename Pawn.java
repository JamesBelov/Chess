public class Pawn extends Piece {

    public Pawn(boolean trueForWhite){
        super(trueForWhite);
        this.setType("Pawn");
    }

    public boolean isLegalMove(Square from, Square to, Square[][] b){
        
        //if there is no pawn to move illegal
        if(from.getPieceOnSquare() == null){
            return false;
        }
        //can't put pawn on top of a piece of its own color
        if(to.getPieceOnSquare() != null && getColor() == to.getPieceOnSquare().getColor()){
            return false;
        }

        if(to.getPieceOnSquare() == null){
            //check if u can empassant 2 the square
            if((Math.abs(from.getXPos() - to.getXPos()) == 1) && (getColor() ? (from.getYPos() - to.getYPos() == 1) : (from.getYPos() - to.getYPos() == -1)) &&
            (getColor() ? (b[to.getYPos()+1][to.getXPos()].getPieceOnSquare() != null && b[to.getYPos()+1][to.getXPos()].getPieceOnSquare().getenpAble()) : 
            b[to.getYPos()-1][to.getXPos()].getPieceOnSquare() != null && b[to.getYPos()-1][to.getXPos()].getPieceOnSquare().getenpAble()))
            {
                
                    
                
                Square[][] iB = new Square[8][8];
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        iB[i][j] = (Square) b[i][j].clone();
                    }
                }
                iB[to.getYPos()][to.getXPos()].setPieceOnSquare(iB[from.getYPos()][from.getXPos()].getPieceOnSquare());
                iB[from.getYPos()][from.getXPos()].setPieceOnSquare(null);
                for(Square[] row : iB){
                    for(Square s : row){
                        if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType().equals("King") &&
                            s.getPieceOnSquare().getColor() == getColor() && s.isAttacked(iB, !s.getPieceOnSquare().getColor())){
                            return false;
                        }
                    }
                }
                return true;
            }

            if(from.getXPos() != to.getXPos()){ //if we aren't taking a piece we don't let it move diagonal 
                return false;
            }

            if(getColor() ? (from.getYPos() - to.getYPos() == 1) || ((!hasMoved() && to.getYPos() != 7 && b[to.getYPos() + 1][to.getXPos()].getPieceOnSquare() == null) ? from.getYPos() - to.getYPos() == 2 : false) : 
                            (from.getYPos() - to.getYPos() == -1) || ((!hasMoved() && to.getYPos() != 0 && b[to.getYPos() - 1][to.getXPos()].getPieceOnSquare() == null) ? from.getYPos() - to.getYPos() == -2 : false)){

                Square[][] iB = new Square[8][8];
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        iB[i][j] = (Square) b[i][j].clone();
                    }
                }
                iB[to.getYPos()][to.getXPos()].setPieceOnSquare(iB[from.getYPos()][from.getXPos()].getPieceOnSquare());
                iB[from.getYPos()][from.getXPos()].setPieceOnSquare(null);
                for(Square[] row : iB){
                    for(Square s : row){
                        if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getType().equals("King") &&
                            s.getPieceOnSquare().getColor() == getColor() && s.isAttacked(iB, !s.getPieceOnSquare().getColor())){
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
        else{
            if(Math.abs(from.getXPos() - to.getXPos()) != 1){
                return false;
            }

            if(getColor() ? (from.getYPos() - to.getYPos() == 1) : (from.getYPos() - to.getYPos() == -1)){

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

    }

    public Pawn clone(){
        Pawn p = new Pawn(getColor());
        p.setHasMoved(hasMoved());
        return p;
    }

    
}
