package paragon;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import haven.Coord;
import haven.FlowerMenu;
import haven.Gob;
import haven.Loading;
import haven.Moving;
import haven.Resource;
import haven.UI;

public class Utils {
	
	UI ui;
	
	public Utils(UI ui) {
		this.ui = ui;
	}
	
	public Gob player() {
		return player();
	}
	
	public Coord getCenterScreenCoord() {
		Coord sc, sz;
		sz =  ui.sess.glob.gui.map.sz;
		sc = new Coord((int) Math.round(Math.random() * 200 + sz.x / 2
				- 100), (int) Math.round(Math.random() * 200 + sz.y / 2
				- 100));
		return sc;
	}
	
	public boolean isMoving() {
		Gob me = player();
		if (me == null) return false;
		Moving m = me.getattr(Moving.class);
		if (m == null)
			return false;
		else
			return true;
	}

	public Gob getNearestObject(String... string) {
		return findMapObject(50, 0, 0, string);
	}
	
	 public Gob findMapObject(int radius, int x, int y, String... names) {
        Coord my = player().rc;
        Coord offset = new Coord(x, y).mul(11);
        my = my.add(offset);
        double min = radius;
        Gob nearest = null;
        synchronized (ui.sess.glob.oc) {
            for (Gob gob : ui.sess.glob.oc) {
                double dist = gob.rc.dist(my);
                if (dist < min) {
                    boolean matches = false;
                    for (String name : names) {
                        if (isObjectName(gob, name)) {
                            matches = true;
                            break;
                        }
                    }
                    if (matches) {
                        min = dist;
                        nearest = gob;
                    }
                }
            }
        }
        return nearest;
    }
	 
	 public Set<Gob> findMapObjects(int radius, int x, int y, String... names) {
		 Coord my = player().rc;
        Coord offset = new Coord(x, y).mul(11);
        my = my.add(offset);
        double min = radius;
        Set<Gob> gobs = new HashSet<Gob>();
        synchronized (ui.sess.glob.oc) {
            for (Gob gob : ui.sess.glob.oc) {
            	double dist = gob.rc.dist(my);
            	if (dist < min) {
            		for (String name : names) {
	                    if (isObjectName(gob, name)) {
	                        gobs.add(gob);
	                        break;
	                    }
	                }
            	}
            }
        }
        return gobs;
	 }
	 
	 //gets map objects sorted by distance from player
	 public Set<Gob> findMapObjectsSorted(int radius, int x, int y, String... names) {
		 Coord my = player().rc;
        Coord offset = new Coord(x, y).mul(11);
        my = my.add(offset);
        double min = radius;
        new TreeSet<Gob>(new Comparator<Gob>(){
			@Override
			public int compare(Gob a, Gob b) {
				return (int) (player().coord().dist(a.coord()) - player().coord().dist(b.coord()));
			}
        }); 
        SortedSet<Gob> gobs = new TreeSet<Gob>();
        synchronized (ui.sess.glob.oc) {
            for (Gob gob : ui.sess.glob.oc) {
            	double dist = gob.rc.dist(my);
            	if (dist < min) {
            		for (String name : names) {
	                    if (isObjectName(gob, name)) {
	                        gobs.add(gob);
	                        break;
	                    }
	                }
            	}
            }
        }
        return gobs;
	 }
	 
    public boolean isObjectName(Gob gob, String name) {
        try {
            Resource res = gob.getres();
            return (res != null) && res.name.contains(name);
        } catch (Loading e) {
            return false;
        }   
    }

	public void moveToObject(Gob gob) {
    	double dist = gob.rc.dist(player().rc);
    	while (dist > 0) {
    		leftClickObj(gob);
    		dist = gob.rc.dist(player().rc);
    		if (dist > 0) clickInRandomDirection();
    	}
	}
	
	//@button 1 - left mouse button, 2 - middle mouse button, 3 - right mouse button
	//@mod don't remember mod values... need to doc
	public void doClickObj(Gob gob, int button, int mod) {
		ui.sess.glob.gui.map.wdgmsg("click", Coord.z, gob.rc, button, 0, mod, (int)gob.id, gob.rc, 0, -1);
	}
	
	public void leftClickObj(Gob gob) {
		ui.sess.glob.gui.map.wdgmsg("click", Coord.z, gob.rc, 1, 0, 0, (int)gob.id, gob.rc, 0, -1);
		//wait to start movement
		sleep(200); 
		while (isMoving()) {
			sleep(100);
		}
	}
	
	public void leftClickObjOffset(Gob gob, int xOffset, int yOffset) {
		ui.sess.glob.gui.map.wdgmsg("click", Coord.z, new Coord(gob.rc.x+xOffset, gob.rc.y+yOffset), 1, 0, 0, (int)gob.id, gob.rc, 0, -1);
		sleep(200);
		while (isMoving()) {
			sleep(100);
		}
	}
	
	//1/6th  chance of walking North, South, West, East
	//1/12th chance of going NW, NE, SW, SE
    public void clickInRandomDirection() {
    	Gob me = player();
    	Random random = new Random();
    	int xOffset = 0;
    	int yOffset = 0;
    	int offsetSize = 15;
    	switch (random.nextInt(3)) {
	    	case 0: xOffset = offsetSize; break;
	    	case 1: yOffset = offsetSize; break;
	    	case 2: xOffset = offsetSize; yOffset = offsetSize; break;
    	}
    	switch (random.nextInt(4)) {
    		case 0: leftClickObjOffset(me,  xOffset,  yOffset); break;
    		case 1: leftClickObjOffset(me, -xOffset,  yOffset); break;
    		case 2: leftClickObjOffset(me,  xOffset, -yOffset); break;
    		case 3: leftClickObjOffset(me, -xOffset, -yOffset); break;
    	}
    }

	private boolean sleep(int timeout) {
		try {
			Thread.sleep(timeout);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
	//waits for the options menu to appear, returns false if times out
    public boolean waitForFlowerMenu(int timeout) {
    	FlowerMenu menu = null;
    	while (menu == null && timeout > 0) {
    		menu = ui.root.findchild(FlowerMenu.class);
    		sleep(50);
    		timeout -= 50;
    	}
		return menu == null;
    }
    
    public boolean isObject(Gob gob) {
    	return gob != null && getNearestObject(gob.getres().name) != null;
    }
    
	public boolean farm(Gob crop) {
		doClickObj(crop, 3, 0);
		if (!waitForFlowerMenu(750)) return false;
		FlowerMenu menu = ui.root.findchild(FlowerMenu.class);
        for (FlowerMenu.Petal opt : menu.opts) {
            if (opt.name.equals("Harvest")) {
                menu.choose(opt);
                menu.destroy();
                while (isObject(crop)) {
                	sleep(100);
                };
                return true;
            }
        }
		return false;
	}	
}
