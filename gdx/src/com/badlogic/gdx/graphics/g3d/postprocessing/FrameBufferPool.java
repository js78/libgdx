
package com.badlogic.gdx.graphics.g3d.postprocessing;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FrameBufferPool {
	/** The maximum number of objects that will be pooled. */
	public final int max;
	/** The highest number of free objects. Can be reset any time. */
	public int peak;

	private final Array<FrameBuffer> freeObjects;

	/** Creates a pool with an initial capacity of 16 and no maximum. */
	public FrameBufferPool () {
		this(16, Integer.MAX_VALUE);
	}

	/** Creates a pool with the specified initial capacity and no maximum. */
	public FrameBufferPool (int initialCapacity) {
		this(initialCapacity, Integer.MAX_VALUE);
	}

	/** @param max The maximum number of free objects to store in this pool. */
	public FrameBufferPool (int initialCapacity, int max) {
		freeObjects = new Array(false, initialCapacity);
		this.max = max;
	}

	protected FrameBuffer newObject (Pixmap.Format format, int width, int height, boolean hasDepth, boolean hasStencil) {
		return new FrameBuffer(format, width, height, hasDepth, hasStencil);
	}

	/** Returns an object from this pool. The object may be new (from {@link #newObject()}) or reused (previously
	 * {@link #free(Object) freed}). */
	public FrameBuffer obtain (Pixmap.Format format, int width, int height, boolean hasDepth, boolean hasStencil) {
		return freeObjects.size == 0 ? newObject(format, width, height, hasDepth, hasStencil) : freeObjects.pop();
	}

	/** Puts the specified object in the pool, making it eligible to be returned by {@link #obtain()}. If the pool already contains
	 * {@link #max} free objects, the specified object is reset but not added to the pool. */
	public void free (FrameBuffer object) {
		if (object == null) throw new IllegalArgumentException("object cannot be null.");
		if (freeObjects.size < max) {
			freeObjects.add(object);
			peak = Math.max(peak, freeObjects.size);
		}
		if (object instanceof Poolable) ((Poolable)object).reset();
	}

	/** Puts the specified objects in the pool. Null objects within the array are silently ignored.
	 * @see #free(Object) */
	public void freeAll (Array<FrameBuffer> objects) {
		if (objects == null) throw new IllegalArgumentException("object cannot be null.");
		Array<FrameBuffer> freeObjects = this.freeObjects;
		int max = this.max;
		for (int i = 0; i < objects.size; i++) {
			FrameBuffer object = objects.get(i);
			if (object == null) continue;
			if (freeObjects.size < max) freeObjects.add(object);
			if (object instanceof Poolable) ((Poolable)object).reset();
		}
		peak = Math.max(peak, freeObjects.size);
	}

	/** Removes all free objects from this pool. */
	public void clear () {
		freeObjects.clear();
	}

	/** The number of objects available to be obtained. */
	public int getFree () {
		return freeObjects.size;
	}
}
