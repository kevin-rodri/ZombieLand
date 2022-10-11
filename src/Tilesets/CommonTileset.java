package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class CommonTileset extends Tileset {

	public CommonTileset() {
		super(ImageLoader.load("NewCommonTileset.png"), 16, 16, 3);
	}

	@Override
	public ArrayList<MapTileBuilder> defineTiles() {
		ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

		// grass
		Frame grassFrame = new FrameBuilder(getSubImage(0, 0))
				.withScale(tileScale)
				.build();
				MapTileBuilder grassTile = new MapTileBuilder(grassFrame);
				mapTiles.add(grassTile);
		// sign
		Frame signFrame = new FrameBuilder(getSubImage(3, 0))
				.withScale(tileScale)
				.build();

		MapTileBuilder signTile = new MapTileBuilder(signFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(signTile);

		// sand
		Frame sandFrame = new FrameBuilder(getSubImage(0, 1))
				.withScale(tileScale)
				.build();

		MapTileBuilder sandTile = new MapTileBuilder(sandFrame);

		mapTiles.add(sandTile);

		// rock
		Frame rockFrame = new FrameBuilder(getSubImage(3, 1))
				.withScale(tileScale)
				.build();

		MapTileBuilder rockTile = new MapTileBuilder(rockFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(rockTile);

		// tree trunk with full hole
		Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(2, 2))
				.withScale(tileScale)
				.build();

		MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(grassFrame)
				.withTopLayer(treeTrunkWithFullHoleFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(treeTrunkWithFullHoleTile);

		// left end branch
		Frame leftEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
				.withScale(tileScale)
				.withBounds(0, 6, 16, 4)
				.build();

		MapTileBuilder leftEndBranchTile = new MapTileBuilder(grassFrame)
				.withTopLayer(leftEndBranchFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(leftEndBranchTile);

		// right end branch
		Frame rightEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
				.withScale(tileScale)
				.withBounds(0, 6, 16, 4)
				.withImageEffect(ImageEffect.FLIP_HORIZONTAL)
				.build();

		MapTileBuilder rightEndBranchTile = new MapTileBuilder(grassFrame)
				.withTopLayer(rightEndBranchFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(rightEndBranchTile);

		// tree trunk
		Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0))
				.withScale(tileScale)
				.build();

		MapTileBuilder treeTrunkTile = new MapTileBuilder(grassFrame)
				.withTopLayer(treeTrunkFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(treeTrunkTile);

		// tree top leaves
		Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(1, 1))
				.withScale(tileScale)
				.build();

		MapTileBuilder treeTopLeavesTile = new MapTileBuilder(grassFrame)
				.withTopLayer(treeTopLeavesFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(treeTopLeavesTile);

		// yellow flower
		Frame[] yellowFlowerFrames = new Frame[] {
				new FrameBuilder(getSubImage(1, 2), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(1, 3), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(1, 2), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(1, 4), 500)
				.withScale(tileScale)
				.build()
		};

		MapTileBuilder yellowFlowerTile = new MapTileBuilder(yellowFlowerFrames);

		mapTiles.add(yellowFlowerTile);

		// purple flower
		Frame[] purpleFlowerFrames = new Frame[] {
				new FrameBuilder(getSubImage(0, 2), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(0, 3), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(0, 2), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(0, 4), 500)
				.withScale(tileScale)
				.build()
		};

		MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

		mapTiles.add(purpleFlowerTile);

		// middle branch
		Frame middleBranchFrame = new FrameBuilder(getSubImage(2, 3))
				.withScale(tileScale)
				.withBounds(0, 6, 16, 4)
				.build();

		MapTileBuilder middleBranchTile = new MapTileBuilder(grassFrame)
				.withTopLayer(middleBranchFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(middleBranchTile);

		// tree trunk bottom
		Frame treeTrunkBottomFrame = new FrameBuilder(getSubImage(2, 0))
				.withScale(tileScale)
				.build();

		MapTileBuilder treeTrunkBottomTile = new MapTileBuilder(treeTrunkBottomFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(treeTrunkBottomTile);

		// mushrooms
		Frame mushroomFrame = new FrameBuilder(getSubImage(2, 1))
				.withScale(tileScale)
				.build();

		MapTileBuilder mushroomTile = new MapTileBuilder(mushroomFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(mushroomTile);


		// grey rock
		Frame greyRockFrame = new FrameBuilder(getSubImage(3, 2))
				.withScale(tileScale)
				.build();

		MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(greyRockTile);

		// bush
		Frame bushFrame = new FrameBuilder(getSubImage(3, 3))
				.withScale(tileScale)
				.build();

		MapTileBuilder bushTile = new MapTileBuilder(bushFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(bushTile);

		// house body
		Frame houseBodyFrame = new FrameBuilder(getSubImage(3, 4))
				.withScale(tileScale)
				.build();

		MapTileBuilder houseBodyTile = new MapTileBuilder(houseBodyFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(houseBodyTile);

		// house roof body
		Frame houseRoofBodyFrame = new FrameBuilder(getSubImage(4, 0))
				.withScale(tileScale)
				.build();

		MapTileBuilder houseRoofBodyTile = new MapTileBuilder(grassFrame)
				.withTopLayer(houseRoofBodyFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(houseRoofBodyTile);

		// left house roof
		Frame leftHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
				.withScale(tileScale)
				.build();

		MapTileBuilder leftHouseRoofTile = new MapTileBuilder(grassFrame)
				.withTopLayer(leftHouseRoofFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(leftHouseRoofTile);

		// right house roof
		Frame rightHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
				.withScale(tileScale)
				.withImageEffect(ImageEffect.FLIP_HORIZONTAL)
				.build();

		MapTileBuilder rightHouseRoofTile = new MapTileBuilder(grassFrame)
				.withTopLayer(rightHouseRoofFrame)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(rightHouseRoofTile);

		// left window
		Frame leftWindowFrame = new FrameBuilder(getSubImage(4, 2))
				.withScale(tileScale)
				.build();

		MapTileBuilder leftWindowTile = new MapTileBuilder(leftWindowFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(leftWindowTile);

		// right window
		Frame rightWindowFrame = new FrameBuilder(getSubImage(4, 2))
				.withScale(tileScale)
				.withImageEffect(ImageEffect.FLIP_HORIZONTAL)
				.build();

		MapTileBuilder rightWindowTile = new MapTileBuilder(rightWindowFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(rightWindowTile);

		// door
		Frame doorFrame = new FrameBuilder(getSubImage(4, 3))
				.withScale(tileScale)
				.build();

		MapTileBuilder doorTile = new MapTileBuilder(doorFrame)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(doorTile);
		// top water
		Frame[] topWaterFrames = new Frame[] {
				new FrameBuilder(getSubImage(5, 0), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(5, 1), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(5, 2), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(5, 1), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(5, 0), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(5, 3), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(5, 4), 500)
				.withScale(tileScale)
				.build(),
				new FrameBuilder(getSubImage(5, 3), 500)
				.withScale(tileScale)
				.build()
		};

		MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrames)
				.withTileType(TileType.NOT_PASSABLE);

		mapTiles.add(topWaterTile);

		// road with vertical yellow dashed line
		Frame verticalRoad = new FrameBuilder(getSubImage(0, 5))
				.withScale(tileScale)
				.build();

		MapTileBuilder roadTile = new MapTileBuilder(verticalRoad)
				.withTileType(TileType.PASSABLE);

		mapTiles.add(roadTile); 
		
		// road with horizontal yellow dashed line
				Frame horizontalRoad = new FrameBuilder(getSubImage(1, 5))
						.withScale(tileScale)
						.build();

				MapTileBuilder horizontalRoadTile = new MapTileBuilder(horizontalRoad)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(horizontalRoadTile);
		// blank road 
				Frame emptyRoad = new FrameBuilder(getSubImage(3, 5))
						.withScale(tileScale)
						.build();

				MapTileBuilder emptyRoadTile = new MapTileBuilder(emptyRoad)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(emptyRoadTile); 	
		// road for a corner piece facing up to the left
				Frame cornerRoad2 = new FrameBuilder(getSubImage(2, 5))
						.withImageEffect(ImageEffect.FLIP_VERTICAL)
						.withScale(tileScale)
						.build();

				MapTileBuilder cornerRoadTile2 = new MapTileBuilder(cornerRoad2)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(cornerRoadTile2); 
		// road for a corner piece facing down to the right
				Frame cornerRoad4 = new FrameBuilder(getSubImage(2, 5))
						.withImageEffect(ImageEffect.FLIP_H_AND_V)
						.withScale(tileScale)
						.build();

				MapTileBuilder cornerRoadTile4 = new MapTileBuilder(cornerRoad4)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(cornerRoadTile4);
		// road for a corner piece facing down from the right
				Frame cornerRoad3 = new FrameBuilder(getSubImage(2, 5))
						.withImageEffect(ImageEffect.FLIP_HORIZONTAL)
						.withScale(tileScale)
						.build();

				MapTileBuilder cornerRoadTile3 = new MapTileBuilder(cornerRoad3)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(cornerRoadTile3);
		// road for a corner piece facing down to the left
				Frame cornerRoad = new FrameBuilder(getSubImage(2, 5))
						.withScale(tileScale)
						.build();

				MapTileBuilder cornerRoadTile = new MapTileBuilder(cornerRoad)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(cornerRoadTile);  			
		// sidewalk with left and top border
				Frame leftSideWalk = new FrameBuilder(getSubImage(2, 6))
						.withScale(tileScale)
						.build();

				MapTileBuilder leftSideWalkTile = new MapTileBuilder(leftSideWalk)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(leftSideWalkTile);
		// sidewalk with right and top border
				Frame rightSideWalk = new FrameBuilder(getSubImage(2, 6))
						.withImageEffect(ImageEffect.FLIP_HORIZONTAL)
						.withScale(tileScale)
						.build();

				MapTileBuilder rightSideWalkTile = new MapTileBuilder(rightSideWalk)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(rightSideWalkTile);
		// sidewalk with right and bototm border
				Frame rightAndBottomSideWalk = new FrameBuilder(getSubImage(2, 6))
						.withImageEffect(ImageEffect.FLIP_H_AND_V)
						.withScale(tileScale)
						.build();

				MapTileBuilder rightAndBottomSideWalkTile = new MapTileBuilder(rightAndBottomSideWalk)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(rightAndBottomSideWalkTile);	
		// sidewalk with right and bototm border
				Frame leftAndBottomSideWalk = new FrameBuilder(getSubImage(2, 6))
						.withImageEffect(ImageEffect.FLIP_VERTICAL)
						.withScale(tileScale)
						.build();

				MapTileBuilder leftAndBottomSideWalkTile = new MapTileBuilder(leftAndBottomSideWalk)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(leftAndBottomSideWalkTile);		
		// sidewalk with Stop Sign
				Frame sideWalkStopSign = new FrameBuilder(getSubImage(5, 5))
						.withScale(tileScale)
						.build();

				MapTileBuilder sideWalkStopSignTile = new MapTileBuilder(sideWalkStopSign)
						.withTileType(TileType.NOT_PASSABLE);

				mapTiles.add(sideWalkStopSignTile);
		// sidewalk with left border 
				Frame leftSideSideWalk = new FrameBuilder(getSubImage(3, 6))
						.withScale(tileScale)
						.build();

				MapTileBuilder leftSideSideWalkTile = new MapTileBuilder(leftSideSideWalk)
						.withTileType(TileType.PASSABLE);		
				mapTiles.add(leftSideSideWalkTile);
		// sidewalk with right border 
				Frame rightSideSideWalk = new FrameBuilder(getSubImage(3, 6))
						.withImageEffect(ImageEffect.FLIP_HORIZONTAL)
						
						.withScale(tileScale)
						.build();

				MapTileBuilder rightSideSideWalkTile = new MapTileBuilder(rightSideSideWalk)
						.withTileType(TileType.PASSABLE);		
				mapTiles.add(rightSideSideWalkTile);		
		// sidewalk with top border 
				Frame topSideWalk = new FrameBuilder(getSubImage(4, 6))
						.withScale(tileScale)
						.build();

				MapTileBuilder topSideWalkTile = new MapTileBuilder(topSideWalk)
						.withTileType(TileType.PASSABLE);		
				mapTiles.add(topSideWalkTile);		
		// sidewalk with bottom border 
				Frame bottomSideWalk = new FrameBuilder(getSubImage(4, 6))
						.withImageEffect(ImageEffect.FLIP_VERTICAL)
						.withScale(tileScale)
						.build();

				MapTileBuilder bottomSideWalkTile = new MapTileBuilder(bottomSideWalk)
						.withTileType(TileType.PASSABLE);		
				mapTiles.add(bottomSideWalkTile);	
		// sidewalk with bottom border 
				Frame SideWalk = new FrameBuilder(getSubImage(5, 6))
						.withImageEffect(ImageEffect.FLIP_VERTICAL)
						.withScale(tileScale)
						.build();

				MapTileBuilder SideWalkTile = new MapTileBuilder(SideWalk)
						.withTileType(TileType.PASSABLE);		
				mapTiles.add(SideWalkTile);
		// animated grass
				Frame[] grassFrames = new Frame[] {
						new FrameBuilder(getSubImage(0, 6), 4000)
						.withScale(tileScale)
						.build(),
						new FrameBuilder(getSubImage(1, 6), 4000)
						.withScale(tileScale)
						.build()
				};

				MapTileBuilder grassFramesTile = new MapTileBuilder(grassFrames)
						.withTileType(TileType.PASSABLE);

				mapTiles.add(grassFramesTile);
				
		return mapTiles;
	}
}
