package main;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.Board;
import main.SquareType;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class UI
{
    /*
    Updates the UI with content from squares b.
     */
    public Parent createContent(Board b)
    {
        Pane root = new Pane();
        root.setPrefSize(750, 750);

        List<Tile> tiles = new ArrayList<>();

        //TEMP NOTE - same as toString just replaced strings with Tile objects
        for (int i = 0; i < Board.ROWS; i++)
        {
            for (int j = 0; j < Board.COLS; j++)
            {
                if(b.squares[i][j].getTile() != null)
                {
                    tiles.add(new Tile(b.squares[i][j].getTile().toString(), Color.LIGHTGOLDENRODYELLOW, Font.font(20)));
                }
                else if (b.squares[i][j].getType() == SquareType.CENTRE)
                {
                    tiles.add(new Tile("✸", Color.SALMON, Font.font(38)));
                }
                else if (b.squares[i][j].getType() == SquareType.DL)
                {
                    tiles.add(new Tile ("Double\nletter", Color.LIGHTBLUE, Font.font(10)));
                }
                else if (b.squares[i][j].getType() == SquareType.TL)
                {
                    tiles.add(new Tile("Triple\nletter", Color.DODGERBLUE, Font.font(10)));
                }
                else if (b.squares[i][j].getType() == SquareType.DW)
                {
                    tiles.add(new Tile("Double\nword", Color.SALMON, Font.font(10)));
                }
                else if (b.squares[i][j].getType() == SquareType.TW)
                {
                    tiles.add(new Tile("Triple\nword", Color.RED, Font.font(10)));
                }
                else
                {
                    tiles.add(new Tile("  ", Color.MEDIUMSEAGREEN, Font.font(10)));
                }

            }
        }

        for(int k=0; k<tiles.size(); k++)
        {
            Tile tile = tiles.get(k);
            tile.setTranslateX(50 * (k % Board.ROWS));
            tile.setTranslateY(50 * (k / Board.ROWS));
            root.getChildren().add(tile);

        }
        return root;
    }

    /*
    Pane that represents one of the 15*15 squares on the board.
     */
    private class Tile extends StackPane
    {
        public Tile(String value, Color colour, Font fontsize)
        {
            /*JavaFX Construction*/
            Rectangle border = new Rectangle(50, 50);
            border.setFill(colour);
            border.setStroke(Color.WHITE);
            border.setStrokeWidth(3);

            Text text = new Text(String.valueOf(value));
            text.setFont(fontsize);
            text.setTextAlignment(TextAlignment.CENTER);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
    }

}