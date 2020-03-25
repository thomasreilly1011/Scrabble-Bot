package IO;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Game.Board;
import Game.SquareType;

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

        List<UI.Tile> tiles = new ArrayList<>();

        //TEMP NOTE - same as toString just replaced strings with Tile objects
        for (int i = 0; i < Board.ROWS; i++)
        {
            for (int j = 0; j < Board.COLS; j++)
            {
                if(b.squares[i][j].getTile() != null)
                {
                    tiles.add(new Tile(b.squares[i][j].getTile().toString()));
                }
                else if (b.squares[i][j].getType() == SquareType.CENTRE)
                {
                    tiles.add(new Tile("**"));
                }
                else if (b.squares[i][j].getType() == SquareType.DL)
                {
                    tiles.add(new Tile ("DL"));
                }
                else if (b.squares[i][j].getType() == SquareType.TL)
                {
                    tiles.add(new Tile("TL"));
                }
                else if (b.squares[i][j].getType() == SquareType.DW)
                {
                    tiles.add(new Tile("DW"));
                }
                else if (b.squares[i][j].getType() == SquareType.TW)
                {
                    tiles.add(new Tile("TW"));
                }
                else
                {
                    tiles.add(new Tile("  "));
                }

            }
        }

        //TODO: Fix this loop. Tiles aren't generating in the right order.
        for(int k=0; k<tiles.size(); k++)
        {
            UI.Tile tile = tiles.get(k);
            tile.setTranslateX(50 * (k / Board.ROWS));
            tile.setTranslateY(50 * (k % Board.ROWS));
            root.getChildren().add(tile);

        }
        return root;
    }

    /*
    Pane that represents one of the 15*15 squares on the board.
     */
    private class Tile extends StackPane
    {
        public Tile(String value)
        {
            /*JavaFX Construction*/
            Rectangle border = new Rectangle(50, 50);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            Text text = new Text(String.valueOf(value));
            text.setFont(Font.font(20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
    }

}