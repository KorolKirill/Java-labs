import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkingWithFiles {
    File getFile(String filePath) throws CustomException {

        File file = new File(filePath);
        if (!file.exists()) {
            throw  new CustomException ("File does not exist.");
        }
        if (!file.isFile()) {
            throw  new CustomException ("Path is not directed to the File.");
        }
        return file;
    }

    List<Shape> getShapesSerialized(String filePath) throws IOException, ClassNotFoundException {
        var list = new ArrayList<Shape>();
        ObjectInputStream istream = new ObjectInputStream(
                new FileInputStream(filePath));

        while (true) {
            try {
                var obj = istream.readObject();
                list.add( (Shape) obj );
            } catch (IOException e) {
                return list;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void  saveShapesToFile(Iterable<Shape> shapes,String filePath)  {
        try {
            OutputStream os = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            shapes.forEach(shape -> {
                try {
                    oos.writeObject(shape);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String cypherPhrase(String phrase) {
        var charArray = phrase.toCharArray();
        var stringBuffer = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            stringBuffer.append(++charArray[i]);
        }
        return stringBuffer.toString();
    }
    String deCypherPhrase(String phrase) {
        var charArray = phrase.toCharArray();
        var stringBuffer = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            stringBuffer.append(--charArray[i]);
        }
        return stringBuffer.toString();
    }
}

class MySecretReader extends FilterReader {

    @Override
    public int read() throws IOException {
        int symbolCode = super.read();
        if (symbolCode == -1) {
            return symbolCode;
        }
        else {
            return symbolCode + 1;
        }
    }

    /**
     * Creates a new filtered reader.
     *
     * @param in a Reader object providing the underlying stream.
     * @throws NullPointerException if {@code in} is {@code null}
     */
    protected MySecretReader(Reader in) {
        super(in);
    }
}

class MySecretWriter extends FilterWriter {
    @Override
    public void write(int c) throws IOException {
        super.write(c+1);
    }

    /**
     * Create a new filtered writer.
     *
     * @param out a Writer object to provide the underlying stream.
     * @throws NullPointerException if {@code out} is {@code null}
     */
    protected MySecretWriter(Writer out) {
        super(out);
    }
}

class CustomException extends Exception {
    String message;
    public CustomException(String s) {
        message = s;
    }
}
