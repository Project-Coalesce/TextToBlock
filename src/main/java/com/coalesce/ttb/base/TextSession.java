package com.coalesce.ttb.base;

import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Cache of a player's current text operations
 */
public final class TextSession {

	private final Stack<Set<BlockState>> undo = new Stack<>(), redo = new Stack<>();


	/**
	 * Cache all BlockStates before modifying their blocks
	 *
	 * @param blockStates The premodified BlockStates
	 */
	public void cacheUndo(@NotNull Set<BlockState> blockStates) {
		undo.push(blockStates);
	}

	private void cacheRedo(@NotNull Set<BlockState> blockStates) {
		redo.push(blockStates);
	}


	/**
	 * Undoes the last operation done by the user.
	 *
	 * @return True if operation successful, false otherwise.
	 */
	public boolean undo() {
		Set<BlockState> lastOperation = retrieve(undo);
		if (lastOperation == null) return false;

		cacheRedo(pullCurrent(lastOperation));
		lastOperation.forEach(blockState -> blockState.update(true));

		return true;
	}

	/**
	 * Redoes the last operation undone by the user.
	 *
	 * @return True if operation successful, false otherwise.
	 */
	public boolean redo() {
		Set<BlockState> lastOperation = retrieve(redo);
		if (lastOperation == null) return false;

		cacheUndo(pullCurrent(lastOperation));
		lastOperation.forEach(blockState -> blockState.update(true));

		return true;
	}

	public void clear() {
		undo.clear();
		redo.clear();
	}


	private @Nullable Set<BlockState> retrieve(Stack<Set<BlockState>> stack) {
		return stack.isEmpty() ? null : stack.pop();
	}

	private @NotNull Set<BlockState> pullCurrent(@NotNull Set<BlockState> blockStates) {
		return blockStates.stream().map(blockState -> blockState.getBlock().getState()).collect(Collectors.toSet());
	}

}
