/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     Björn Johannessen <johannessen.bjorn@gmail.com>
 *
 *  Redistribution and/or modification of this file is subject to the
 *  terms of the GNU Lesser General Public License, version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Other parts of this source tree adhere to other copying
 *  rights. Please see the file `COPYING' in the root directory of the
 *  source tree for details.
 *
 *  A copy the GNU Lesser General Public License is distributed along
 *  with the source tree of which this file is a part in the file
 *  `doc/LPGL-3'. If it is missing for any reason, please see the Free
 *  Software Foundation's website at <http://www.fsf.org/>, or write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *  Boston, MA 02111-1307 USA
 */

package haven;

import java.util.*;

public class RenderList {
    Slot[] list = new Slot[100];
    int cur = 0;
    private int curp = -1;
    Collection<LSlot> lights = new ArrayList<LSlot>();
    
    class Slot {
	Rendered r;
	Transform t;
	int p;
    }
    
    class LSlot {
	Light l;
	int p;
    }
    
    public void add(Rendered r, Transform t) {
	int i = cur++;
	if(i >= list.length) {
	    Slot[] n = new Slot[i * 2];
	    System.arraycopy(list, 0, n, 0, i);
	    list = n;
	}
	Slot s;
	if((s = list[i]) == null)
	    s = list[i] = new Slot();
	s.r = r;
	s.t = t;
	int pp = s.p = curp;
	try {
	    curp = i;
	    if(!r.setup(this))
		s.r = null;
	} finally {
	    curp = pp;
	}
    }
    
    public void rewind() {
	if(curp != -1)
	    throw(new RuntimeException("Tried to rewind RenderList while adding to it."));
	cur = 0;
	lights.clear();
    }
    
    public void add(Light l) {
	LSlot s = new LSlot();
	s.l = l;
	s.p = curp;
	lights.add(s);
    }
}