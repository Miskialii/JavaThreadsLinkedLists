import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Oblig2Del1 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Oblig2Del1 <folder_path>");
            return;
        }

        String folderPath = args[0];

        SubsekvensRegister subsekvensRegister = new SubsekvensRegister();

        // Leser filer og legger HashMap-er inn i SubsekvensRegister for TestDataLike
        lesOgLeggInnHashMaper(subsekvensRegister, folderPath + "/TestDataLike");

        // Leser filer og legger HashMap-er inn i SubsekvensRegister for TestDataLitenLike
        lesOgLeggInnHashMaper(subsekvensRegister, folderPath + "/TestDataLitenLike");

        // Fletter alle HashMap-er i SubsekvensRegister
        flettHashMaper(subsekvensRegister);

        // Skriver ut subsekvensen med flest forekomster
        skrivUtMaksForekomst(subsekvensRegister);
    }

    public static void lesOgLeggInnHashMaper(SubsekvensRegister register, String folderPath) {
        File folder = new File(folderPath);
        lesOgLeggInnHashMaperFraMappe(register, folder);
    }
    
    private static void lesOgLeggInnHashMaperFraMappe(SubsekvensRegister register, File mappe) {
        File[] files = mappe.listFiles();
    
        if (files == null) {
            System.out.println("Folder is empty or does not exist.");
            return;
        }
    
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".csv")) {
                String filePath = file.getPath();
                HashMap<String, Subsekvens> hashMap = SubsekvensRegister.lesFraFil(filePath);
                register.settInnHashMap(hashMap);
            } else if (file.isDirectory()) {
                lesOgLeggInnHashMaperFraMappe(register, file);
            }
        }
    }
    
    
    public static void flettHashMaper(SubsekvensRegister register) {
        int antallHashMaper = register.antallHashMaper();

        while (antallHashMaper > 1) {
            HashMap<String, Subsekvens> hashMap1 = register.taUtHashMap(0);
            HashMap<String, Subsekvens> hashMap2 = register.taUtHashMap(0);
            HashMap<String, Subsekvens> sammenslattHashMap = SubsekvensRegister.slaaSammenHashMaper(hashMap1, hashMap2);
            register.settInnHashMap(sammenslattHashMap);
            antallHashMaper--;
        }
    }

    public static void skrivUtMaksForekomst(SubsekvensRegister register) {
        HashMap<String, Subsekvens> sammenslattHashMap = register.taUtHashMap(0);
        String maksSubsekvens = "";
        int maksForekomster = 0;

        for (Map.Entry<String, Subsekvens> entry : sammenslattHashMap.entrySet()) {
            String subsekvens = entry.getKey();
            Subsekvens forekomster = entry.getValue();
            int antallForekomster = forekomster.hentAntall();

            if (antallForekomster > maksForekomster) {
                maksSubsekvens = subsekvens;
                maksForekomster = antallForekomster;
            }
        }

        System.out.println("Subsekvensen med flest forekomster er: " + maksSubsekvens + " med " + maksForekomster + " forekomster.");
    }
}
