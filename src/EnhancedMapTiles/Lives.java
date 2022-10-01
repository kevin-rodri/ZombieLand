package EnhancedMapTiles;

import Builders.FrameBuilder;


import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.PlayerState;
import Level.TileType;
import Utils.Direction;
import Utils.Point;

public class Lives extends EnhancedMapTile {
	public Lives(Point location) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Heart.png"), 13, 13), TileType.NOT_PASSABLE);
	}

	@Override
	public void update(Player player) {
		super.update(player);
		if (player.getCurrentWalkingXDirection() == Direction.LEFT) 
		{
			moveXHandleCollision(player.getBoundsX2() - getBoundsX1());
		} 
		else if (player.getCurrentWalkingXDirection() == Direction.RIGHT) 
		{
			moveXHandleCollision(player.getBoundsX1() - getBoundsX2());
		}
		if (player.getCurrentWalkingYDirection() == Direction.UP) 
		{
			moveYHandleCollision(player.getBoundsY1() - getBoundsY2());
		} 
		else if (player.getCurrentWalkingYDirection() == Direction.DOWN) 
		{
			moveYHandleCollision(player.getBoundsY2() - getBoundsY1());
		}
	}


	private boolean canMoveLeft(Player player) {
		return player.getBoundsX1() <= getBoundsX2() && player.getBoundsX2() > getBoundsX2() && canMoveX(player);
	}

	private boolean canMoveRight(Player player) {
		return player.getBoundsX2() >= getBoundsX1() && player.getBoundsX1() < getBoundsX1() && canMoveX(player);
	}

	private boolean canMoveX(Player player) {
		return (player.getBoundsY1() < getBoundsY2() && player.getBoundsY2() >= getBoundsY2())
				|| (player.getBoundsY2() > getBoundsY1() && player.getBoundsY1() <= getBoundsY1())
				|| (player.getBoundsY2() < getBoundsY2() && player.getBoundsY1() > getBoundsY1());
	}

	private boolean canMoveUp(Player player) {
		return player.getBoundsY1() <= getBoundsY2() && player.getBoundsY2() > getBoundsY2() && canMoveY(player);
	}

	private boolean canMoveDown(Player player) {
		return player.getBoundsY2() >= getBoundsY1() && player.getBoundsY1() < getBoundsY1() && canMoveY(player);
	}

	private boolean canMoveY(Player player) {
		return (player.getBoundsX1() < getBoundsX2() && player.getBoundsX2() >= getBoundsX2())
				|| (player.getBoundsX2() > getBoundsX1() && player.getBoundsX1() <= getBoundsX1())
				|| (player.getBoundsX2() < getBoundsX2() && player.getBoundsX1() > getBoundsX1());
	}

	@Override
	protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
		Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0)).withScale(3).build();
		return new GameObject(x, y, frame);
	}

}
