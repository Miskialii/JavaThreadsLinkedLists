import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SubsekvensRegister {
    private ArrayList<HashMap<String, Subsekvens>> register;

    public SubsekvensRegister() {
        register = new ArrayList<>();
    }

    public void settInnHashMap(HashMap<String, Subsekvens> hashMap) {
        register.add(hashMap);
    }

    public HashMap<String, Subsekvens> taUtHashMap(int indeks) {
        if (indeks < 0 || indeks >= register.size()) {
            throw new IllegalArgumentException("Ugyldig indeks");
        }
        return register.remove(indeks);
    }

    public int antallHashMaper() {
        return register.size();
    }

    public static HashMap<String, Subsekvens> lesFraFil(String filbane) {
        HashMap<String, Subsekvens> subsekvensHashMap = new HashMap<>();

        try {
            File fil = new File(filbane);
            Scanner scanner = new Scanner(fil);

            while (scanner.hasNextLine()) {
                String linje = scanner.nextLine();
                if (linje.length() >= 3) {
                    for (int i = 0; i <= linje.length() - 3; i++) {
                        String subsekvens = linje.substring(i, i + 3);
                        if (!subsekvensHashMap.containsKey(subsekvens)) {
                            subsekvensHashMap.put(subsekvens, new Subsekvens(subsekvens, 1));
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke filen: " + e.getMessage());
        }

        return subsekvensHashMap;
    }

    public static HashMap<String, Subsekvens> slaaSammenHashMaper(HashMap<String, Subsekvens> hashMap1, HashMap<String, Subsekvens> hashMap2) {
        HashMap<String, Subsekvens> sammenslattHashMap = new HashMap<>();

        // Legg til alle subsekvensene fra hashMap1
        for (Map.Entry<String, Subsekvens> entry : hashMap1.entrySet()) {
            String subsekvens = entry.getKey();
            Subsekvens subsekvens1 = entry.getValue();
            sammenslattHashMap.put(subsekvens, subsekvens1);
        }

        // Slå sammen subsekvenser og antall forekomster fra hashMap2
        for (Map.Entry<String, Subsekvens> entry : hashMap2.entrySet()) {
            String subsekvens = entry.getKey();
            Subsekvens subsekvens2 = entry.getValue();
            if (sammenslattHashMap.containsKey(subsekvens)) {
                Subsekvens eksisterendeSubsekvens = sammenslattHashMap.get(subsekvens);
                int nyttAntall = eksisterendeSubsekvens.hentAntall() + subsekvens2.hentAntall();
                eksisterendeSubsekvens.endreAntall(nyttAntall);
            } else {
                sammenslattHashMap.put(subsekvens, subsekvens2);
            }
        }

        return sammenslattHashMap;
    }
}
