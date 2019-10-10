package Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClientsFiles {
    public String name;
    public List<String> files = new ArrayList<String>();

    public ClientsFiles(String name){
        this.name = name;
    }

    public void addFile(String fileName){
        files.add(fileName);
    }

    public void removeFile(String fileName){
        files.remove(fileName);
    }

    public Boolean hasFile(String fileName){
        AtomicReference<Boolean> clientHasFile = new AtomicReference<>(false);
        files.forEach(file -> {
            if (file.equals(fileName)){
                clientHasFile.set(true);
            }
        });
        return clientHasFile.get();
    }

    @Override
    public String toString() {
        String outputString = "----------" + name + "----------" + "\n";
        for (String file : files) {
            outputString += "=> " + file + "\n";
        }

        return outputString;
    }
}
