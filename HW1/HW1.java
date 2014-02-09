import java.util.*

public class HW1 {
	
	public enum Cities {Alexandria, Cairo, Matruh, Siwa, Bawiti, Asyut, Nekhel, Suez, Quseir, Qena, Sohag, Luxor, Kharga, Mut, Qasr}
	
	public Cities city;
	public Map>Cities, int> distances;
	
	public HW1(Cities c) {
		city = c;
		distances = new HashMap<Cities, int>();
	}
	
	public void addCity(HW1 other, int distance) {
		distances.put(other, distance);
		other.distances.put(this, distance);
	}
	
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
		cits.get(c1).addCity(c2, d);
	}
	
	public static void initDistances() {
		addDistance(Cities.Alexandria, Cities.Matruh, 159);
		addDistance(Cities.Alexandria, Cities.Cairo, 112);
		addDistance(Cities.Alexandria, Cities.Nekhel, 245);
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
	
	public static void main(String[] args) {
		initCities();
		initDistances();
	}
	
}