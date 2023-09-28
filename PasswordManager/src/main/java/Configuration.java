import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Configuration {

    private String configFile;
    public static class StorageDescription {
        public static final String FILE = "file";
        public static final String DATABASE = "database";
        public String type;
        public String[] parts;
        public EncryptionType encryptionType;
    }
    public LinkedList<StorageDescription> storages = new LinkedList<>();


    public void readStoragesFromFile(String file) {
        File f = new File(file);
        if(f.exists() && !f.isDirectory()) {
            Scanner sc;
            try {
                sc = new Scanner(new FileReader(file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            ObjectMapper mapper = new ObjectMapper();
            while (sc.hasNextLine()) {
                StorageDescription rd = null;
                try {
                    rd = mapper.readValue(sc.nextLine(), StorageDescription.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                storages.add(rd);
            }
            sc.close();
        }
    }
    public void writeStoragesToFile() throws IOException {
        FileWriter fw = new FileWriter(configFile);
        ObjectMapper mapper = new ObjectMapper();
        for (StorageDescription rd: storages) {
            String str = String.format("%s%n", mapper.writeValueAsString(rd));
            fw.write(str);
        }
        fw.close();
    }
    public Configuration(String execName) {
        String fileName = execName + ".config";
        readStoragesFromFile(fileName);
        configFile = fileName;
    }
}
