import java.io.FileInputStream;
import java.io.EOFException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class FileInputHelper implements Closeable {
    private FileInputStream fileInputStream;
    private BufferedReader fileBufferedReader;

    public FileInputHelper(File file) throws IOException {
        fileInputStream = new FileInputStream(file);
        fileBufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
    }


    public byte readByte() throws IOException {
        int cur = fileInputStream.read();
        if (cur == -1)//если закончился файл
            throw new EOFException();
        return (byte)cur;
    }

    public String readLine() throws IOException {
        return fileBufferedReader.readLine();
    }

    @Override
    public void close() throws IOException{
        fileInputStream.close();
    }
}