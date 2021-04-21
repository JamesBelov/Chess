package src;
import javax.swing.*;
import java.awt.*;

public class Square {
    
    private JButton b;
    private Piece pieceOnSquare;
    private int xPos;
    private int yPos;
    private int color;
    private boolean highlighted;

    public Square(int yPos, int xPos){
        color = (xPos + yPos) % 2;
        if(color == 0){
            b = new JButton(new ImageIcon("src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\null\\0null.png"));
        }
        else{
            b = new JButton(new ImageIcon("src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\null\\1null.png"));
        }
        b.setPreferredSize(new Dimension(64, 64));
        pieceOnSquare = null;
        this.xPos = xPos;
        this.yPos = yPos;

    }

    public Piece getPieceOnSquare(){
        return pieceOnSquare;
    }

    public void setPieceOnSquare(Piece p){
        pieceOnSquare = p;
        if(p != null){
            this.setButtonImage("src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\" + getPieceOnSquare().getType() + "\\" + p.getColor() + color + p.getType() + ".png");
        }
        else{
            if(color == 0){
                this.setButtonImage("src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\null\\0null.png");
            }
            else{
                this.setButtonImage("src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\null\\1null.png");
            }
        }
    }

    public String getCurrentButtonImagePath(){
        String s = "";
        if(this.getPieceOnSquare() != null){
            s = "src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\" + getPieceOnSquare().getType() + "\\" + this.getPieceOnSquare().getColor() + color + this.getPieceOnSquare().getType() + ".png";
        }
        else{
            if(color == 0){
                s = "src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\null\\0null.png";
            }
            else{
                s = "src\\Texture Packs\\" + Game.CURRENT_TEXTURES + "\\null\\1null.png";
            }
        }
        if(highlighted){
            s = s.substring(0, s.length() - 4) + "Highlighted.png";
        }
        return s;
    }

    public JButton getButton(){
        return b;
    }

    public void setButtonImage(String imagePath){
        b.setIcon(new ImageIcon(imagePath));
    }

    public int getXPos(){
        return xPos;
    }

    public int getYPos(){
        return yPos;
    }

    public int getColor(){
        return color;
    }

    public boolean isAttacked(Square[][] b, boolean byWhite){
        Square[][] clonedBoard = new Square[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                clonedBoard[i][j] = b[i][j].clone();
            }
        }
        Piece holder = null;
        for(Square[] row : clonedBoard){
            for(Square s : row){
                if(s.equals(this)){
                    continue;
                }
                holder = this.getPieceOnSquare();
                if(this.getPieceOnSquare()!= null && !this.getPieceOnSquare().getType().equals("King")){
                    this.setPieceOnSquare(null);
                }
                if(s.getPieceOnSquare() != null && s.getPieceOnSquare().getColor() == byWhite &&
                s.getPieceOnSquare().isLegalMove(s, this, clonedBoard)){
                    this.setPieceOnSquare(holder);
                    return true;
                }
                this.setPieceOnSquare(holder);
            }
        }
        return false;
    }

    public void setHighlighted(boolean h){
        highlighted = h;
    }

    public boolean isHighlighted(){
        return highlighted;
    }

    public Square clone(){
        Square s = new Square(getYPos(), getXPos());
        if(getPieceOnSquare() != null){
            s.setPieceOnSquare(getPieceOnSquare().clone());
        }
        s.setButtonImage(getCurrentButtonImagePath());
        s.setHighlighted(highlighted);
        return s;
    }

}
