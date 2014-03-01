import java.util.*;

public class HW2 implements Comparable{
	
	public enum Cities {Alexandria, Cairo, Matruh, Siwa, Bawiti, Asyut, Nekhel, Suez, Quseir, Qena, Sohag, Luxor, Kharga, Mut, Qasr}
	
	public Cities city;
	public Map<HW2, Integer> distances;
	public int distanceFromStart;
	
	public HW2(Cities c) {
		city = c;
		distances = new HashMap<HW2, Integer>();
		distanceFromStart = 0;
	}
	
	public void addCity(HW2 other, int distance) {
		distances.put(other, distance);
		other.distances.put(this, distance);
	}
	
	public int getDistance(HW2 other) {
		return distances.get(other);
	}
	
	public boolean equals(HW2 other) {
		return city.equals(other.city);
	}
	
	public String toString() {
		return city.name();
	}
	
	
	
	public int compareTo(Object arg0) {
		if (!(arg0 instanceof HW2))
			return -1;
		if (distanceFromStart == ((HW2)arg0).distanceFromStart)
			return city.toString().compareTo(((HW2)arg0).toString());
		else if (distanceFromStart < ((HW2)arg0).distanceFromStart)
			return -1;
		return 1;
	}
	
	///////////////////////////////////////////////////////////
	
	public static Map<Cities, HW2> cits = new HashMap<Cities, HW2>();
	public static Map<Cities, Integer> h1 = new HashMap<Cities, Integer>();
	public static Map<Cities, Integer> h2 = new HashMap<Cities, Integer>();
	
	public static void addHeuristic(Map<Cities, Integer> h, Cities c, int i) {
		h.put(c, i);
	}
	
	public static void initH1() {
		addHeuristic(h1, Cities.Alexandria, 99999);
		addHeuristic(h1, Cities.Matruh, 174);
		addHeuristic(h1, Cities.Cairo, 126);
		addHeuristic(h1, Cities.Nekhel, 133);
		addHeuristic(h1, Cities.Siwa, 132);
		addHeuristic(h1, Cities.Bawiti, 105);
		addHeuristic(h1, Cities.Asyut, 52);
		addHeuristic(h1, Cities.Suez, 121);
		addHeuristic(h1, Cities.Qasr, 68);
		addHeuristic(h1, Cities.Quseir, 55);
		addHeuristic(h1, Cities.Mut, 51);
		addHeuristic(h1, Cities.Kharga, 24);
		addHeuristic(h1, Cities.Sohag, 27);
		addHeuristic(h1, Cities.Qena, 10);
		addHeuristic(h1, Cities.Luxor, 0);
	}
	
	public static void initH2() {
		addHeuristic(h2, Cities.Alexandria, 99999);
		addHeuristic(h2, Cities.Matruh, 189);
		addHeuristic(h2, Cities.Cairo, 139);
		addHeuristic(h2, Cities.Nekhel, 145);
		addHeuristic(h2, Cities.Siwa, 148);
		addHeuristic(h2, Cities.Bawiti, 118);
		addHeuristic(h2, Cities.Asyut, 67);
		addHeuristic(h2, Cities.Suez, 136);
		addHeuristic(h2, Cities.Qasr, 77);
		addHeuristic(h2, Cities.Quseir, 59);
		addHeuristic(h2, Cities.Mut, 65);
		addHeuristic(h2, Cities.Kharga, 38);
		addHeuristic(h2, Cities.Sohag, 36);
		addHeuristic(h2, Cities.Qena, 19);
		addHeuristic(h2, Cities.Luxor, 0);
	}
	
	public static void addCity(Cities c) {
		cits.put(c, new HW2(c));
	}
	
	
	public static void initCities() {
		addCity(Cities.Alexandria);
		addCity(Cities.Cairo);
		addCity(Cities.Matruh);
		addCity(Cities.Siwa);
		addCity(Cities.Bawiti);
		addCity(Cities.Asyut);
		addCity(Cities.Nekhel);
		addCity(Cities.Suez);
		addCity(Cities.Quseir);
		addCity(Cities.Qena);
		addCity(Cities.Sohag);
		addCity(Cities.Luxor);
		addCity(Cities.Kharga);
		addCity(Cities.Mut);
		addCity(Cities.Qasr);
	}
	
	public static void addDistance(Cities c1, Cities c2, int d) {
		cits.get(c1).addCity(getCity(c2), d);
	}
	
	public static HW2 getCity(Cities c) {
		return cits.get(c);
	}
	
	public static void initDistances() {
		addDistance(Cities.Alexandria, Cities.Matruh, 159);
		addDistance(Cities.Alexandria, Cities.Nekhel, 245);
		addDistance(Cities.Alexandria, Cities.Cairo, 112);
		addDistance(Cities.Siwa, Cities.Matruh, 181);
		addDistance(Cities.Siwa, Cities.Bawiti, 210);
		addDistance(Cities.Cairo, Cities.Bawiti, 186);
		addDistance(Cities.Cairo, Cities.Asyut, 198);
		addDistance(Cities.Nekhel, Cities.Suez, 72);
		addDistance(Cities.Nekhel, Cities.Quseir, 265);
		addDistance(Cities.Sohag, Cities.Quseir, 163);
		addDistance(Cities.Sohag, Cities.Qena, 69);
		addDistance(Cities.Qena, Cities.Luxor, 33);
		addDistance(Cities.Mut, Cities.Sohag, 184);
		addDistance(Cities.Mut, Cities.Kharga, 98);
		addDistance(Cities.Mut, Cities.Qasr, 126);
		addDistance(Cities.Bawiti, Cities.Qasr, 104);
	}
	
	public static void DFQ(ArrayList<HW2> list, HW2 node) { //Depth-first
		PriorityQueue<HW2> queue = new PriorityQueue<HW2>(11, new Comparator<HW2>() {
			public int compare(HW2 arg0, HW2 arg1) {
				return arg1.city.toString().compareTo(arg0.city.toString());
			}
		});
		for (HW2 temp: node.distances.keySet()) {
			temp.distanceFromStart = node.distances.get(temp);
			queue.add(temp);
		}
		while (!queue.isEmpty())
			list.add(0, queue.poll());
	}
	
	public static void BFQ(ArrayList<HW2> list, HW2 node) { //Breadth-First
		PriorityQueue<HW2> queue = new PriorityQueue<HW2>(11, new Comparator<HW2>() {
			public int compare(HW2 arg0, HW2 arg1) {
				return arg0.city.toString().compareTo(arg1.city.toString());
			}
		});
		
		for (HW2 temp: node.distances.keySet()) {
			temp.distanceFromStart = node.distanceFromStart + node.distances.get(temp);
			queue.add(temp);
		}
		while (!queue.isEmpty())
			list.add(queue.poll());
	}
	
	public static void UFQ(ArrayList<HW2> list, HW2 node) { //Uniform Cost
		PriorityQueue<HW2> queue = new PriorityQueue<HW2>();
		for (HW2 temp: node.distances.keySet()) {
			if (list.contains(temp) || visited.containsKey(temp))
				continue;
			temp.distanceFromStart = node.distanceFromStart + node.distances.get(temp);
			queue.add(temp);
		}
		while (!queue.isEmpty()) {
			boolean added = false;
			for (int i = 0; i < list.size(); i++) {
				if (queue.peek().distanceFromStart < list.get(i).distanceFromStart) {
					list.add(i, queue.poll());
					added = true;
					break;
				}
			}
			if (!added)
				list.add(queue.poll());
		}
		
	}
	
	public static void GFQ(ArrayList<HW2> list, HW2 node, final Map<Cities, Integer> h) { //Greedy
		PriorityQueue<HW2> queue = new PriorityQueue<HW2>(11, new Comparator<HW2>() {
			public int compare(HW2 arg0, HW2 arg1) {
				return h.get(arg0.city).compareTo(h.get(arg1.city));
			}
		});
		
		for (HW2 temp: node.distances.keySet()) {
			temp.distanceFromStart = node.distanceFromStart + node.distances.get(temp);
			queue.add(temp);
		}
		while (!list.isEmpty())
			queue.add(list.remove(0));
		while (!queue.isEmpty())
			list.add(queue.poll());
	}
	
	public static void ASQ(ArrayList<HW2> list, HW2 node, final Map<Cities, Integer> h) { //A*
		PriorityQueue<HW2> queue = new PriorityQueue<HW2>(11, new Comparator<HW2>() {
			public int compare(HW2 arg0, HW2 arg1) {
				return ((Integer)(arg0.distanceFromStart+h.get(arg0.city))).compareTo(arg1.distanceFromStart+h.get(arg1.city));
			}
		});
		
		for (HW2 temp: node.distances.keySet()) {
			if (temp.distanceFromStart == 0 || (temp.distanceFromStart > node.distanceFromStart + node.distances.get(temp)))
				temp.distanceFromStart = node.distanceFromStart + node.distances.get(temp);
			queue.add(temp);
		}
		while (!list.isEmpty())
			queue.add(list.remove(0));
		while (!queue.isEmpty())
			list.add(queue.poll());
	}
	
	public static Map<HW2, Boolean> visited = new HashMap<HW2, Boolean>();
	
	public static HW2 search(HW2 start, HW2 target) {
		ArrayList<HW2> queue = new ArrayList<HW2>();
		queue.add(start);
		//dfs alphabetical
		
		while(!queue.isEmpty()) {
			HW2 node = queue.remove(0);
			if (visited.containsKey(node))
				continue;
			else
				visited.put(node, true);
			if (node.equals(target)) {
				return node;
			}
			System.out.print(node+/* ": " + (node.distanceFromStart + h2.get(node.city)) + */", ");
			ASQ(queue, node, h1);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		initCities();
		initDistances();
		initH1();
		initH2();
		
		System.out.println(search(getCity(Cities.Alexandria), getCity(Cities.Luxor)));
	}
	
}