/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter Güttinger and contributors
 */
package ch.njol.skript.effects;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

@Name("Break Block Naturally")
@Description({"Breaks the block and spawns items as if a player had mined it",
		"\nYou can add a tool, which will spawn items based on how that tool would break the block ",
		"(ie: Using a diamond pickaxe of fortune 3 would drop more coal items when breaking a coal ore block)"})
@Examples({"on right click:", "\tbreak clicked block naturally",
		"loop blocks in radius 10 around player:", "\tbreak loop-block naturally using player's tool",
		"loop blocks in radius 10 around player:", "\tbreak loop-block naturally using diamond pickaxe of fortune 3"})
@Since("{INSERT VERSION}")
public class EffBreakNaturally extends Effect {
	
	static {
		Skript.registerEffect(EffBreakNaturally.class, "break %blocks% naturally [using %-itemtype%]");
	}
	
	@SuppressWarnings("null")
	private Expression<Block> block;
	@Nullable
	private Expression<ItemType> tool;
	
	@SuppressWarnings({"unchecked", "null"})
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
		block = (Expression<Block>) exprs[0];
		tool = (Expression<ItemType>) exprs[1];
		return true;
	}
	
	@Override
	protected void execute(final Event e) {
		ItemType tool = this.tool != null ? this.tool.getSingle(e) : null;
		for (Block block : this.block.getArray(e)){
			if (tool != null) block.breakNaturally(tool.getRandom());
			else block.breakNaturally();
		}
	}
	
	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "break " + block.toString(e, debug) + " naturally" + (tool != null ? " using " + tool : "");
	}
}

