import java.util.*;

public class HW1 implements Comparable{
	
	public enum Cities {Alexandria, Cairo, Matruh, Siwa, Bawiti, Asyut, Nekhel, Suez, Quseir, Qena, Sohag, Luxor, Kharga, Mut, Qasr}
	
	public Cities city;
	public Map<HW1, Integer> distances;
	public int distanceFromStart;
	
	public HW1(Cities c) {
		city = c;
		distances = new HashMap<HW1, Integer>();
		distanceFromStart = 0;
	}
	
	public void addCity(HW1 other, int distance) {
		distances.put(other, distance);
		other.distances.put(this, distance);
	}
	
	public int getDistance(HW1 other) {
		return distances.get(other);
	}
	
	public boolean equals(HW1 other) {
		return city.equals(other.city);
	}
	
	public String toString() {
		return city.name();
	}
	
	
	
	public int compareTo(Object arg0) {
		if (!(arg0 instanceof HW1))
			return -1;
		if (distanceFromStart == ((HW1)arg0).distanceFromStart)
			return city.toString().compareTo(((HW1)arg0).toString());
		else if (distanceFromStart < ((HW1)arg0).distanceFromStart)
			return -1;
		return 1;
	}
	
	///////////////////////////////////////////////////////////
	
	public static Map<Cities, HW1> cits = new HashMap<Cities, HW1>();
	
	public static void addCity(Cities c) {
		cits.put(c, new HW1(c));
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
	
	public static HW1 getCity(Cities c) {
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
	
	public static void DFQ(ArrayList<HW1> list, HW1 node) {
		PriorityQueue<HW1> queue = new PriorityQueue<HW1>(11, new Comparator<HW1>() {
			public int compare(HW1 arg0, HW1 arg1) {
				return arg1.city.toString().compareTo(arg0.city.toString());
			}
		});
		for (HW1 temp: node.distances.keySet()) {
			temp.distanceFromStart = node.distances.get(temp);
			queue.add(temp);
		}
		while (!queue.isEmpty())
			list.add(0, queue.poll());
	}
	
	public static void BFQ(ArrayList<HW1> list, HW1 node) {
		PriorityQueue<HW1> queue = new PriorityQueue<HW1>(11, new Comparator<HW1>() {
			public int compare(HW1 arg0, HW1 arg1) {
				return arg0.city.toString().compareTo(arg1.city.toString());
			}
		});
		
		for (HW1 temp: node.distances.keySet()) {
			temp.distanceFromStart = node.distanceFromStart + node.distances.get(temp);
			queue.add(temp);
		}
		while (!queue.isEmpty())
			list.add(queue.poll());
	}
	
	public static void UFQ(ArrayList<HW1> list, HW1 node) {
		PriorityQueue<HW1> queue = new PriorityQueue<HW1>();
		for (HW1 temp: node.distances.keySet()) {
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
	
	public static Map<HW1, Boolean> visited = new HashMap<HW1, Boolean>();
	
	public static HW1 search(HW1 start, HW1 target) {
		ArrayList<HW1> queue = new ArrayList<HW1>();
		queue.add(start);
		//dfs alphabetical
		
		while(!queue.isEmpty()) {
			HW1 node = queue.remove(0);
			if (visited.containsKey(node))
				continue;
			else
				visited.put(node, true);
			if (node.equals(target)) {
				return node;
			}
			System.out.print(node+", ");
			DFQ(queue, node);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		initCities();
		initDistances();
		
		System.out.println(search(getCity(Cities.Alexandria), getCity(Cities.Luxor)));
	}
	
}