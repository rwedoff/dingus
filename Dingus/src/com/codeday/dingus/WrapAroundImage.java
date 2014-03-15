package com.codeday.dingus;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class WrapAroundImage extends Image
{
	private Vector2 velocity;

	protected float angle;

	protected boolean destroyed = false;
	protected ArrayList<WrapAroundImage> children = new ArrayList<WrapAroundImage>();
	protected boolean isChild;
	protected World world;
	protected float collisionRadius;

	private ShapeRenderer renderer;

	public WrapAroundImage(TextureRegion texture, World world)
	{
		super(texture);
		setOrigin(getWidth() / 2, getHeight() / 2);

		this.world = world;
		this.isChild = false;
		renderer = new ShapeRenderer();
		collisionRadius = getCollisionRadius();

		for (int i = 0; i < 3; i++)
			addChild(createChild());
	}

	public WrapAroundImage(Drawable drawable, World world)
	{
		super(drawable);
		setOrigin(getWidth() / 2, getHeight() / 2);

		this.world = world;
		this.isChild = true;
		renderer = new ShapeRenderer();
		collisionRadius = getCollisionRadius();
	}

	public void update(float delta)
	{
		// Regardless of whether or not we're an offscreen, update with velocity
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);

		// Only do this for the parent objects
		if (!isChild)
		{
			// Check to see if children are out of sync
			if (children.get(0).getPoint().distance(getPoint()) != world
					.getHeight()
					|| children.get(2).getPoint().distance(getPoint()) != world
							.getHeight()
					|| children.get(1).getPoint().distance(getPoint()) != world
							.getWidth())
			{
				updateChildren();
			}

			// Update all of the children
			for (WrapAroundImage child : children)
				child.update(delta);

			// Check to see if we've gone entirely offscreen
			if (isImageTotallyOffscreen())
			{
				// Find if a child is onscreen, then shift parent and children
				// accordingly.
				float xOnscreen = 0;
				float yOnscreen = 0;
				boolean found = false;

				for (int i = 0; i < children.size() && !found; i++)
				{
					if (children.get(i).isImageTotallyOnscreen())
					{
						xOnscreen = children.get(i).getX();
						yOnscreen = children.get(i).getY();
						found = true; 
					}
				}
				if (found)
					shift(xOnscreen - getX(), yOnscreen - getY());

				// If we are more than two widths or two heighs offscreen,
				// shift back one
				if (getX() + world.getWidth() > world.getWidth())
					setX(getX() - world.getWidth());
				if (getX() + getWidth() - world.getWidth() < 0)
					setX(getX() + world.getWidth());

				if (getY() + world.getHeight() > world.getHeight())
					setY(getY() - world.getHeight());
				if (getY() + getHeight() - world.getHeight() < 0)
					setY(getY() + world.getHeight());

			}

		}
	}

	private void shift(float xDiff, float yDiff)
	{
		setPosition(getX() + xDiff, getY() + yDiff);
		for (WrapAroundImage child : children)
			child.setPosition(child.getX() + xDiff, child.getY() + yDiff);
	}

	public boolean isImageTotallyOffscreen()
	{
		boolean offscreenX = (getX() > world.getWidth() || getX() + getWidth() < 0);
		boolean offscreenY = (getY() > world.getHeight() || getY()
				+ getHeight() < 0);
		return offscreenX || offscreenY;
	}

	public boolean isImageTotallyOnscreen()
	{
		boolean onscreenX = (getX() > 0 && getX() + getWidth() < world
				.getWidth());
		boolean onscreenY = (getY() > 0 && getY() + getHeight() < world
				.getHeight());
		return onscreenX && onscreenY;
	}

	public boolean isImagePartiallyOnscreen()
	{
		boolean onscreenX = (getX() > 0 && getX() + getWidth() < world
				.getWidth());
		boolean onscreenY = (getY() > 0 && getY() + getHeight() < world
				.getHeight());
		return onscreenX && onscreenY;
	}

	public void setVelocity(Vector2 newVelocity)
	{
		this.velocity = newVelocity;

		if (!isChild)
		{
			updateChildren();
		}
	}

	private void updateChildren()
	{
		for (WrapAroundImage child : children)
			child.setVelocity(velocity);

		WrapAroundImage offscreen1 = children.get(0);
		WrapAroundImage offscreen2 = children.get(1);
		WrapAroundImage offscreen3 = children.get(2);

		if (getX() < world.getWidth() / 2)
		{
			if (getY() < world.getHeight() / 2)
			{
				offscreen1.setPosition(getX() + world.getWidth(), getY());
				offscreen2.setPosition(getX(), getY() + world.getHeight());
				offscreen3.setPosition(getX() + world.getWidth(), getY()
						+ world.getHeight());
			}
			if (getY() > world.getHeight() / 2)
			{
				offscreen1.setPosition(getX() + world.getWidth(), getY());
				offscreen2.setPosition(getX(), getY() - world.getHeight());
				offscreen3.setPosition(getX() + world.getWidth(), getY()
						- world.getHeight());
			}
		}
		if (getX() > world.getWidth() / 2)
		{
			if (getY() < world.getHeight() / 2)
			{
				offscreen1.setPosition(getX() - world.getWidth(), getY());
				offscreen2.setPosition(getX(), getY() + world.getHeight());
				offscreen3.setPosition(getX() - world.getWidth(), getY()
						+ world.getHeight());
			}
			if (getY() > world.getHeight() / 2)
			{
				offscreen1.setPosition(getX() - world.getWidth(), getY());
				offscreen2.setPosition(getX(), getY() - world.getHeight());
				offscreen3.setPosition(getX() - world.getWidth(), getY()
						- world.getHeight());
			}
		}

	}

	public void setAngle(float angle)
	{
		this.angle = angle;
		rotate(angle);
	}

	public void rotate(float rotation)
	{
		super.rotate(rotation);
		angle += rotation;

		if (!isChild)
		{
			for (WrapAroundImage child : children)
			{
				child.rotate(rotation);
			}
		}
	}

	public ArrayList<WrapAroundImage> getChildren()
	{
		return children;
	}

	public void draw(SpriteBatch batch, float parentAlpha)
	{
		if (isImageTotallyOffscreen())
			return;

		if (Dingus.DEV_MODE)
		{
			batch.end();
			renderer.setProjectionMatrix(batch.getProjectionMatrix());
			renderer.setTransformMatrix(batch.getTransformMatrix());
			renderer.translate(getX(), getY(), 0);

			renderer.begin(ShapeType.Line);
			renderer.circle(getOriginX(), getOriginY(), getCollisionRadius());
			renderer.end();
			batch.begin();
		}
		super.draw(batch, parentAlpha);
	}

	/**
	 * Create 5 new child objects that will be positioned correctly and then
	 * call addChild for each
	 * 
	 * @return
	 */
	protected abstract WrapAroundImage createChild();

	/**
	 * The radius at which collisions occur on the object
	 * 
	 * @return
	 */
	public abstract float getCollisionRadius();

	protected void addChild(WrapAroundImage child)
	{
		world.addActor(child);
		children.add(child);
		child.setOrigin(getWidth() / 2, getHeight() / 2);
		child.setAngle(angle);
	}

	public boolean containsPoint(Point point)
	{
		return getPoint().distance(point) < getCollisionRadius();
	}

	/**
	 * Assumes it is only called by parent objects
	 * 
	 * @param other
	 * @return Null if no collision or if there was a collision the object
	 *         collided with
	 */
	public WrapAroundImage collidedWith(WrapAroundImage other)
	{
		// Check on one level
		if (getPoint().distance(other.getPoint()) < getCollisionRadius()
				+ other.getCollisionRadius())
			return other;

		// If is a child go no further
		if (isChild)
			return null;

		// Check the other's children
		for (WrapAroundImage child : other.getChildren())
		{
			WrapAroundImage result = collidedWith(child);
			if (result != null)
				return other;
		}

		// We didn't collide with any children. Now check to see if our children
		// collided with them.
		for (WrapAroundImage child : getChildren())
		{
			WrapAroundImage result = child.collidedWith(other);
			if (result != null)
				return other;
		}

		// No collision occurred.
		return null;
	}

	public Point getPoint()
	{
		return new Point(getX() + getOriginX(), getY() + getOriginY());
	}

	public Vector2 getVelocity()
	{
		return velocity;
	}
}
