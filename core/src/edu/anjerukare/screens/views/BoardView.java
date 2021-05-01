package edu.anjerukare.screens.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import edu.anjerukare.screens.enums.Team;
import edu.anjerukare.screens.utils.Point;
import edu.anjerukare.screens.views.pieces.*;

import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.Touchable.childrenOnly;
import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static edu.anjerukare.screens.views.TileView.COLOR_BLACK;
import static edu.anjerukare.screens.views.TileView.COLOR_WHITE;
import static edu.anjerukare.screens.views.TileView.State.*;

public class BoardView extends Group {

    private final TileView[][] tiles = new TileView[8][8];
    private final PieceView[][] pieces = new PieceView[8][8];

    public PieceView selectedPiece = null;

    private final static ShapeRenderer renderer = new ShapeRenderer();
    public boolean overlapped;

    public BoardView() {
        setSize(352, 352);
        setTouchable(childrenOnly);

        addTile(new TileView(COLOR_BLACK, new RookView(Team.WHITE)), 0, 0);
        addTile(new TileView(COLOR_WHITE, new KnightView(Team.WHITE)), 0, 1);
        addTile(new TileView(COLOR_BLACK, new BishopView(Team.WHITE)), 0, 2);
        addTile(new TileView(COLOR_WHITE, new QueenView(Team.WHITE)), 0, 3);
        addTile(new TileView(COLOR_BLACK, new KingView(Team.WHITE)), 0, 4);
        addTile(new TileView(COLOR_WHITE, new BishopView(Team.WHITE)), 0, 5);
        addTile(new TileView(COLOR_BLACK, new KnightView(Team.WHITE)), 0, 6);
        addTile(new TileView(COLOR_WHITE, new RookView(Team.WHITE)), 0, 7);
        for (int j = 0; j < 8; ++j)
            addTile(new TileView((j % 2 == 0 ? COLOR_WHITE : COLOR_BLACK), new PawnView(Team.WHITE)), 1, j);

        for (int i = 2; i < 6; ++i) {
            for (int j = 0; j < 8; ++j)
                addTile(new TileView(((i + j) % 2 == 0? COLOR_BLACK : COLOR_WHITE), null), i, j);
        }

        for (int j = 0; j < 8; ++j)
            addTile(new TileView((j % 2 == 0 ? COLOR_BLACK : COLOR_WHITE), new PawnView(Team.BLACK)), 6, j);
        addTile(new TileView(COLOR_WHITE, new RookView(Team.BLACK)), 7, 0);
        addTile(new TileView(COLOR_BLACK, new KnightView(Team.BLACK)), 7, 1);
        addTile(new TileView(COLOR_WHITE, new BishopView(Team.BLACK)), 7, 2);
        addTile(new TileView(COLOR_BLACK, new QueenView(Team.BLACK)), 7, 3);
        addTile(new TileView(COLOR_WHITE, new KingView(Team.BLACK)), 7, 4);
        addTile(new TileView(COLOR_BLACK, new BishopView(Team.BLACK)), 7, 5);
        addTile(new TileView(COLOR_WHITE, new KnightView(Team.BLACK)), 7, 6);
        addTile(new TileView(COLOR_BLACK, new RookView(Team.BLACK)), 7, 7);
    }

    public void addTile(TileView tile, int i, int j) {
        tiles[i][j] = tile;
        tile.setPosition(j * 44, i * 44);
        addActorAt(0, tile);
        if (tile.piece != null) {
            pieces[i][j] = tile.piece;
            tile.piece.setPosition(j * 44, i * 44);
            tile.piece.setTouchable(disabled);
            addActor(tile.piece);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (overlapped) {
            batch.end();

            renderer.setProjectionMatrix(batch.getProjectionMatrix());
            renderer.setTransformMatrix(batch.getTransformMatrix());
            // renderer.translate(getX(), getY(), 0);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(0, 0, 0, .25f);
            renderer.rect(getX(), getY(), getWidth(), getHeight());
            renderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
        }
    }

    public void resetTileStates() {
        for (TileView[] row : tiles) {
            for (TileView tile : row)
                tile.state = DEFAULT;
        }
    }

    public void markTiles(List<Point> points) {
        for (Point point : points) {
            if (tiles[point.y][point.x].piece != null)
                tiles[point.y][point.x].state = CAPTUREAVAILABLE;
            else
                tiles[point.y][point.x].state = MOVEAVAILABLE;
        }
    }

    public void markTilesAsCastlingAvailable(List<Point> points) {
        for (Point point : points) {
            tiles[point.y][point.x].state = CASTLINGAVAILABLE;
        }
    }

    public void markTileAsEnPassantAvailable(Point enPassantLocation) {
        tiles[enPassantLocation.y][enPassantLocation.x].state = ENPASSANTAVAILABLE;
    }

    public Point getPointForTile(TileView tile) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (tiles[i][j] == tile) return new Point(j, i);
            }
        }
        return null;
    }

    public TileView getTileAt(Point point) {
        return tiles[point.y][point.x];
    }

    public void movePiece(PieceView piece, Point targetPoint) {
        /* update pieces array */
        Point point = getPointForPiece(piece);
        pieces[point.y][point.x] = null;
        pieces[targetPoint.y][targetPoint.x] = piece;
        /* update tiles */
        TileView tile = getTileAt(point);
        tile.piece = null;
        TileView targetTile = getTileAt(targetPoint);
        targetTile.piece = piece;
        /* add action to present the move */
        piece.addAction(moveTo(targetTile.getX(), targetTile.getY(), 1));
    }

    public Point getPointForPiece(PieceView piece) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (pieces[i][j] == piece) return new Point(j, i);
            }
        }
        return null;
    }

    public PieceView getPieceAt(Point point) {
        return pieces[point.y][point.x];
    }

    public void removePiece(PieceView piece) {
        Point point = getPointForPiece(piece);
        /* remove from pieces array */
        pieces[point.y][point.x] = null;
        /* remove from tile */
        TileView tile = getTileAt(point);
        tile.piece = null;
        /* remove from group children */
        piece.addAction(sequence(alpha(0, .5f), run(() -> {
            removeActor(piece);
        })));
    }

    public void setPiece(Point point, PieceView pieceToSet) {
        TileView tile = getTileAt(point);
        removeActor(tile.piece);

        tile.piece = pieceToSet;
        pieces[point.y][point.x] = pieceToSet;
        pieceToSet.setTouchable(disabled);
        pieceToSet.setPosition(point.x * 44, point.y * 44);
        addActor(tile.piece);
    }

    public void castlePieces(KingView king, RookView rook) {
        Point kingPosition = getPointForPiece(king);
        Point rookPosition = getPointForPiece(rook);

        int direction = kingPosition.x < rookPosition.x ? 1 : -1;
        kingPosition.x += 2 * direction;
        movePiece(king, kingPosition);
        rookPosition.x = kingPosition.x - direction;
        movePiece(rook, rookPosition);
    }
}
