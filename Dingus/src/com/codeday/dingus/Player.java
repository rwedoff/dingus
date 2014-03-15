package com.codeday.dingus;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.codeday.dingus.SoundManager.SoundEffect;

public class Player extends WrapAroundImage
{
	private Drawable left;
	private Drawable middle;
	private Drawable right;

	public static float MAX_VELOCITY = 200;
	private long lastTime;
	private long lastBackTime;
	private long lastTriTime;
	private long shootDelay = 200;
	private Laser laser;
	private float playerAngle;
	private ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();
	private Image shield;
	private TextureRegionDrawable damaged;

	public static boolean backShoot = false;
	public static boolean rapidShoot = false;
	public static boolean sideShot = false;
	public static int health = 3;

	public Player(TextureRegion left, TextureRegion middle,
			TextureRegion right, TextureRegion damaged, World world)
	{
		super(middle, world);
		createShield();

		backShoot = false;
		sideShot = false;
		rapidShoot = false;

		this.left = new TextureRegionDrawable(left);
		this.middle = new TextureRegionDrawable(middle);
		this.right = new TextureRegionDrawable(right);
		this.damaged = new TextureRegionDrawable(damaged);

		playerAngle = 90;
		setVelocity(new Vector2(0, 0));

		for (WrapAroundImage child : getChildren())
		{
			((Player) child).setFrames(this.left, this.middle, this.right, this.damaged);
		}
	}

	private Player(Drawable drawable, World world)
	{
		super(drawable, world);

		playerAngle = 90;
		setVelocity(new Vector2(0, 0));

		createShield();
	}

	private void createShield()
	{
		shield = new Image(world.getAtlas().findRegion("shield"));
		shield.setSize(getWidth(), getHeight() * 3.0f / 2.0f);
		shield.setOrigin(shield.getWidth() / 2, shield.getHeight() / 2);
		world.addActor(shield);
	}

	public void update(float delta, World world, float rotate, float thrust,
			ArrayList<Asteroid> asteroids, ArrayList<PowerUp> powerups,
			ArrayList<Laser> enemyLasers)
	{
		super.update(delta);

		rotate(rotate * delta);
		update(delta, rotate);

		for (WrapAroundImage child : getChildren())
		{
			((Player) child).update(delta, rotate);
		}

		if (isChild)
			return;

		float dxA = (float) (Math.cos(Math.toRadians(playerAngle)) * thrust);
		float dyA = (float) (Math.sin(Math.toRadians(playerAngle)) * thrust);

		Vector2 veloCpy = getVelocity().cpy();
		veloCpy.x += dxA;
		veloCpy.y += dyA;
		veloCpy.x = Math.min(veloCpy.x, MAX_VELOCITY);
		veloCpy.y = Math.min(veloCpy.y, MAX_VELOCITY);
		setVelocity(veloCpy);

		if (checkCollisionAsteroids(asteroids))
			world.playerDeath(getX() + getWidth() / 2, getY() + getHeight() / 2);
		if (checkCollisionPowerUps(powerups))
		{
			// TODO: Add powerup noise
		}
		if (checkCollisionLasers(enemyLasers))
		{
			health--;

			if (health <= 0)
			{
				world.playerDeath(getX(), getY());
			}
		}

		for (int i = 0; i < powerUpList.size();)
		{
			if (powerUpList.get(i).isFinished())
			{
				powerUpList.remove(i).deactivate();
			}
			else
				i++;
		}

	}

	private void update(float delta, float rotate)
	{
		if (rotate == 0)
			setDrawable(middle);
		else if (rotate < 0)
			setDrawable(right);
		else
			setDrawable(left);

		playerAngle += rotate * delta;
	}

	private boolean checkCollisionLasers(ArrayList<Laser> enemyLasers)
	{
		boolean collision = false;

		if (checkLaserCollision(enemyLasers))
		{
			collision = true;
		}
		for (WrapAroundImage a : children)
		{
			((Player) a).checkLaserCollision(enemyLasers);
		}
		return collision;
	}

	public boolean checkLaserCollision(ArrayList<Laser> lasers)
	{
		for (int i = 0; i < lasers.size();)
		{
			Laser laser = lasers.get(i);

			Point laserPoint = new Point(laser.getX() + laser.getOriginX(),
					laser.getY() + laser.getOriginX());
			if (containsPoint(laserPoint))
			{
				laser.remove();
				lasers.remove(i);

				return true;
			}
			else
				i++;
		}
		return false;
	}

	private boolean checkCollisionAsteroids(ArrayList<Asteroid> asteroids)
	{
		for (WrapAroundImage asteroid : asteroids)
		{
			WrapAroundImage result = collidedWith(asteroid);
			if (result != null)
			{
				return true;
			}
		}
		return false;
	}

	private boolean checkCollisionPowerUps(ArrayList<PowerUp> images)
	{
		boolean collided = false;

		for (WrapAroundImage powerup : images)
		{
			PowerUp result = (PowerUp) collidedWith(powerup);
			if (result != null)
			{
				result.setCollected();
				result.activate(this);
				powerUpList.add(result);

			}
		}

		return collided;
	}

	public float getplayerAngle()
	{
		return playerAngle;
	}

	public void shoot(ArrayList<Laser> lasers)
	{
		if (this.isImageTotallyOffscreen())
			return;

//		testShot(lasers);

		if (canShoot())
		{
			laser = new Laser(world.getAtlas().findRegion("laserRed"),
					playerAngle);

			laser.setPosition(getX() + getOriginX() - laser.getWidth() / 2
					+ (float) Math.cos(Math.toRadians(playerAngle))
					* getHeight() / 2,
					getY() + getOriginY() - laser.getHeight() / 2
							+ (float) Math.sin(Math.toRadians(playerAngle))
							* getHeight() / 2);
			world.addActor(laser);
			lasers.add(laser);

			if (!isChild)
				world.getGame().getSoundManager().play(SoundEffect.LASER);
		}
		if (backShoot)
		{
			if (canBackShoot())
			{
				laser = new Laser(world.getAtlas().findRegion("laserRed"),
						playerAngle - 180);

				laser.setPosition(getX() + getOriginX() - laser.getWidth() / 2
						- (float) Math.cos(Math.toRadians(playerAngle))
						* getHeight() / 2,
						getY() + getOriginY() - laser.getHeight() / 2
								- (float) Math.sin(Math.toRadians(playerAngle))
								* getHeight() / 2);
				world.addActor(laser);
				lasers.add(laser);
			}
		}
		if (rapidShoot)
		{
			laser = new Laser(world.getAtlas().findRegion("laserRed"),
					playerAngle);

			laser.setPosition(getX() + getOriginX() - laser.getWidth() / 2
					+ (float) Math.cos(Math.toRadians(playerAngle))
					* getHeight() / 2,
					getY() + getOriginY() - laser.getHeight() / 2
							+ (float) Math.sin(Math.toRadians(playerAngle))
							* getHeight() / 2);
			world.addActor(laser);
			lasers.add(laser);
		}
		if (sideShot)
		{
			if (canSideShot())
			{
				Laser laser1 = new Laser(world.getAtlas()
						.findRegion("laserRed"), playerAngle + 90);
				Laser laser2 = new Laser(world.getAtlas()
						.findRegion("laserRed"), playerAngle - 90);

				laser1.setPosition(
						getX()
								+ getOriginX()
								- laser.getWidth()
								/ 2
								+ (float) Math.cos(Math
										.toRadians(playerAngle + 90))
								* getHeight() / 2,
						getY()
								+ getOriginY()
								- laser.getHeight()
								/ 2
								+ (float) Math.sin(Math
										.toRadians(playerAngle + 90))
								* getHeight() / 2);
				world.addActor(laser1);
				lasers.add(laser1);

				laser2.setPosition(
						getX()
								+ getOriginX()
								- laser.getWidth()
								/ 2
								+ (float) Math.cos(Math
										.toRadians(playerAngle - 90))
								* getHeight() / 2,
						getY()
								+ getOriginY()
								- laser.getHeight()
								/ 2
								+ (float) Math.sin(Math
										.toRadians(playerAngle - 90))
								* getHeight() / 2);
				lasers.add(laser2);
				world.addActor(laser2);
			}
		}

		for (WrapAroundImage child : getChildren())
			((Player) child).shoot(lasers);
	}

	private void testShot(ArrayList<Laser> lasers)
	{
		Laser laser1 = new Laser(world.getAtlas().findRegion("laserRed"),
				playerAngle - 15);

		laser1.setPosition(getX() + getOriginX() - laser1.getWidth() / 2
				+ (float) Math.cos(Math.toRadians(playerAngle)) * getHeight()
				/ 2, getY() + getOriginY() - laser1.getHeight() / 2
				+ (float) Math.sin(Math.toRadians(playerAngle)) * getHeight()
				/ 2);
		world.addActor(laser1);
		lasers.add(laser1);
		
		Laser laser2 = new Laser(world.getAtlas().findRegion("laserRed"),
				playerAngle + 15);

		laser2.setPosition(getX() + getOriginX() - laser1.getWidth() / 2
				+ (float) Math.cos(Math.toRadians(playerAngle)) * getHeight()
				/ 2, getY() + getOriginY() - laser1.getHeight() / 2
				+ (float) Math.sin(Math.toRadians(playerAngle)) * getHeight()
				/ 2);
		world.addActor(laser2);
		lasers.add(laser2);
	}

	protected WrapAroundImage createChild()
	{
		Player p = new Player(getDrawable(), world);
		return p;
	}

	private void setFrames(Drawable left2, Drawable middle2, Drawable right2, TextureRegionDrawable damaged)
	{
		this.left = left2;
		this.middle = middle2;
		this.right = right2;
		this.damaged = damaged;
	}

	public boolean remove()
	{
		shield.remove();
		return super.remove();
	}

	private boolean canBackShoot()
	{
		if (System.currentTimeMillis() - lastBackTime > shootDelay)
		{
			lastBackTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	private boolean canSideShot()
	{
		if (System.currentTimeMillis() - lastTriTime > shootDelay)
		{
			lastTriTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	private boolean canShoot()
	{
		if (System.currentTimeMillis() - lastTime > shootDelay)
		{
			lastTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	public void addPowerUp(PowerUp powerUp)
	{
		powerUp.activate(this);
		powerUpList.add(powerUp);

	}

	public void addHealth()
	{
		health++;
	}

	public void draw(SpriteBatch batch, float parentAlpha)
	{
		if (health == 1)
			setDrawable(damaged);
		
		super.draw(batch, parentAlpha);

		if (health >= 3)
		{
			shield.setPosition(getX() + getWidth() / 2 - shield.getWidth() / 2,
					getY() + getHeight() / 2 - shield.getHeight() / 2);
			shield.setRotation(angle);
			shield.setVisible(true);
			shield.draw(batch, parentAlpha);
		}
		else
			shield.setVisible(false);
	}

	@Override
	public float getCollisionRadius()
	{
		return getWidth() / 2;
	}

	public void damagePlayer()
	{
		health--;
	}
}
