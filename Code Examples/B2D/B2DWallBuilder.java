package com.mygdx.game.B2D.Starter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class B2DWallBuilder {
    public static B2DPongBody[] buildWalls(World world, Vector2 worldSize) {
        B2DPongBody[] walls = new B2DPongBody[4];
        Vector2 yBoundarySize = new Vector2(worldSize.x, 1);
        Vector2 xBoundarySize = new Vector2(1, worldSize.y);
        Vector2 ceilingPosition = new Vector2(0, worldSize.y / 2);
        Vector2 floorPosition = new Vector2(0, -worldSize.y / 2);
        Vector2 leftWallPosition = new Vector2(-worldSize.x / 2, 0);
        Vector2 rightWallPosition = new Vector2(worldSize.x / 2, 0);
        Color wallColor = Color.DARK_GRAY;

        B2DPongBody left = new B2DPongBody(world, "left wall", xBoundarySize, wallColor);
        left.setBody(B2DBuilder.createPhysicsBox(world, left.size, 0, false,
                true, false, left));
        left.setPosition(leftWallPosition);
        walls[0] = left;

        B2DPongBody right = new B2DPongBody(world, "right wall", xBoundarySize, wallColor);
        right.setBody(B2DBuilder.createPhysicsBox(world, right.size, 0, false,
                true, false, right));
        right.setPosition(rightWallPosition);
        walls[1] = right;

        B2DPongBody ceiling = new B2DPongBody(world, "ceiling", yBoundarySize, wallColor);
        ceiling.setBody(B2DBuilder.createPhysicsBox(world, ceiling.size, 0, false,
                true, false, ceiling));
        ceiling.setPosition(ceilingPosition);
        walls[2] = ceiling;

        B2DPongBody floor = new B2DPongBody(world, "floor", yBoundarySize, wallColor);
        floor.setBody(B2DBuilder.createPhysicsBox(world, floor.size, 0, false,
                true, false, floor));
        floor.setPosition(floorPosition);
        walls[3] = floor;

        return walls;
    }
}
