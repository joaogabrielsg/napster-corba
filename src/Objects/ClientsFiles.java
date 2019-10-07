package Objects;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        String outputString = "----------" + name.toString() + "----------" + "\n";
        for (String file : files) {
            outputString += "=> " + file + "\n";
        }

        return outputString;
    }
}
