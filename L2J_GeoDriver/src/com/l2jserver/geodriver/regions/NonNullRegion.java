/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.geodriver.regions;

import java.nio.ByteBuffer;

import com.l2jserver.geodriver.IBlock;
import com.l2jserver.geodriver.IRegion;
import com.l2jserver.geodriver.blocks.ComplexBlock;
import com.l2jserver.geodriver.blocks.FlatBlock;
import com.l2jserver.geodriver.blocks.MultilayerBlock;

/**
 * @author HorridoJoho
 */
public final class NonNullRegion implements IRegion
{
	private final IBlock[] _blocks = new IBlock[IRegion.REGION_BLOCKS];
	
	public NonNullRegion(ByteBuffer bb)
	{
		for (int blockOffset = 0; blockOffset < IRegion.REGION_BLOCKS; blockOffset++)
		{
			int blockType = bb.get();
			switch (blockType)
			{
				case IBlock.TYPE_FLAT:
					_blocks[blockOffset] = new FlatBlock(bb);
					break;
				case IBlock.TYPE_COMPLEX:
					_blocks[blockOffset] = new ComplexBlock(bb);
					break;
				case IBlock.TYPE_MULTILAYER:
					_blocks[blockOffset] = new MultilayerBlock(bb);
					break;
				default:
					throw new RuntimeException("Invalid block type " + blockType + "!");
			}
		}
	}
	
	private IBlock getBlock(int geoX, int geoY)
	{
		return _blocks[(((geoX / IBlock.BLOCK_CELLS_X) % IRegion.REGION_BLOCKS_X) * IRegion.REGION_BLOCKS_Y) + ((geoY / IBlock.BLOCK_CELLS_Y) % IRegion.REGION_BLOCKS_Y)];
	}
	
	@Override
	public boolean checkNearestNswe(int geoX, int geoY, int worldZ, int nswe)
	{
		return getBlock(geoX, geoY).checkNearestNswe(geoX, geoY, worldZ, nswe);
	}
	
	@Override
	public boolean checkNearestNswe(int geoX, int geoY, int worldZ, int nswe, int zDeltaLimit)
	{
		return getBlock(geoX, geoY).checkNearestNswe(geoX, geoY, worldZ, nswe, zDeltaLimit);
	}
	
	@Override
	public int getNearestZ(int geoX, int geoY, int worldZ)
	{
		return getBlock(geoX, geoY).getNearestZ(geoX, geoY, worldZ);
	}
	
	@Override
	public int getNearestZ(int geoX, int geoY, int worldZ, int zDeltaLimit)
	{
		return getBlock(geoX, geoY).getNearestZ(geoX, geoY, worldZ, zDeltaLimit);
	}
	
	@Override
	public int getNextLowerZ(int geoX, int geoY, int worldZ)
	{
		return getBlock(geoX, geoY).getNextLowerZ(geoX, geoY, worldZ);
	}
	
	@Override
	public int getNextLowerZ(int geoX, int geoY, int worldZ, int zDeltaLimit)
	{
		return getBlock(geoX, geoY).getNextLowerZ(geoX, geoY, worldZ, zDeltaLimit);
	}
	
	@Override
	public int getNextHigherZ(int geoX, int geoY, int worldZ)
	{
		return getBlock(geoX, geoY).getNextHigherZ(geoX, geoY, worldZ);
	}
	
	@Override
	public int getNextHigherZ(int geoX, int geoY, int worldZ, int zDeltaLimit)
	{
		return getBlock(geoX, geoY).getNextHigherZ(geoX, geoY, worldZ, zDeltaLimit);
	}
	
	@Override
	public boolean hasGeo()
	{
		return true;
	}
}