package com.coalesce.ttb.session;

import org.bukkit.block.BlockState;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Cache of a player's current text operations
 */
public final class TextSession {
	
	private final Operations undo, redo;

	public TextSession(int maxSize) {
		undo = new Operations(maxSize);
		redo = new Operations(maxSize);
	}

	/**
	 * Cache all BlockStates before modifying their blocks
	 *
	 * @param blockStates The premodified BlockStates
	 */
	public void cacheUndo(Set<BlockState> blockStates) {
		undo.push(blockStates);
	}

	private void cacheRedo(Set<BlockState> blockStates) {
		redo.push(blockStates);
	}


	/**
	 * Undoes the last operation done by the user.
	 *
	 * @return True if operation successful, false otherwise.
	 */
	public boolean undo() {
		Set<BlockState> lastOperation = undo.pull();
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
		Set<BlockState> lastOperation = redo.pull();
		if (lastOperation == null) return false;

		cacheUndo(pullCurrent(lastOperation));
		lastOperation.forEach(blockState -> blockState.update(true));
		
		return true;
	}
	
	/**
	 * Clears all the operations done by this user from the cache.
	 * @return True if there was something to clear, false otherwise.
	 */
	public boolean clear() {
		if (undo.isEmpty() && redo.isEmpty()) return false;
		undo.clear();
		redo.clear();
		return true;
	}

	private Set<BlockState> pullCurrent(Set<BlockState> blockStates) {
		return blockStates.stream().map(blockState -> blockState.getBlock().getState()).collect(Collectors.toSet());
	}


	private final class Operations {

		private final int maxSize;
		private final Deque<Set<BlockState>> stack = new ArrayDeque<>();

		Operations(int maxSize) {
			this.maxSize = maxSize;
		}

		private void push(Set<BlockState> operation) {
			if (stack.size() == maxSize) stack.removeLast();
			stack.addFirst(operation);
		}

		private Set<BlockState> pull() {
			return stack.pollFirst();
		}

		private boolean isEmpty() {
			return stack.isEmpty();
		}

		private void clear() {
			stack.clear();
		}

	}

}
