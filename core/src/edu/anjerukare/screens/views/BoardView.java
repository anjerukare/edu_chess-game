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
    private boolean overlapped;

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

    public void resetPieces() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                removeActor(tiles[i][j].piece);
                tiles[i][j].piece = null;
                pieces[i][j] = null;
            }
        }

        setPiece(0, 0, new RookView(Team.WHITE));
        setPiece(0, 1, new KnightView(Team.WHITE));
        setPiece(0, 2, new BishopView(Team.WHITE));
        setPiece(0, 3, new QueenView(Team.WHITE));
        setPiece(0, 4, new KingView(Team.WHITE));
        setPiece(0, 5, new BishopView(Team.WHITE));
        setPiece(0, 6, new KnightView(Team.WHITE));
        setPiece(0, 7, new RookView(Team.WHITE));
        for (int j = 0; j < 8; ++j)
            setPiece(1, j, new PawnView(Team.WHITE));

        for (int j = 0; j < 8; ++j)
            setPiece(6, j, new PawnView(Team.BLACK));

        setPiece(7, 0, new RookView(Team.BLACK));
        setPiece(7, 1, new KnightView(Team.BLACK));
        setPiece(7, 2, new BishopView(Team.BLACK));
        setPiece(7, 3, new QueenView(Team.BLACK));
        setPiece(7, 4, new KingView(Team.BLACK));
        setPiece(7, 5, new BishopView(Team.BLACK));
        setPiece(7, 6, new KnightView(Team.BLACK));
        setPiece(7, 7, new RookView(Team.BLACK));

        selectedPiece = null;
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
        batch.end();
        drawBorder(batch);
        batch.begin();

        super.draw(batch, parentAlpha);

        if (overlapped) {
            batch.end();
            drawOverlap(batch);
            batch.begin();
        }
    }

    private void drawBorder(Batch batch) {
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(COLOR_WHITE);

        renderer.rect(getX() - 4, getY() - 4, getWidth() + 8, getHeight() + 8);

        renderer.end();
    }

    private void drawOverlap(Batch batch) {
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0, 0, 0, .5f);

        renderer.rect(getX() - 4, getY() - 4, getWidth() + 8, getHeight() + 8);

        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
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
        piece.toFront();
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

    public void setPiece(int i, int j, PieceView pieceToSet) {
        setPiece(new Point(j, i), pieceToSet);
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

    public void setOverlapped(boolean overlapped) {
        if (overlapped) {
            this.overlapped = true;
            setTouchable(disabled);
        } else {
            this.overlapped = false;
            setTouchable(childrenOnly);
        }
    }
}
