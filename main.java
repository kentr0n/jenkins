import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket; import java.net.Socket;

//hiohhl
//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
///Users/kendalllu/2020 Concurrent Programming/HTTPReq/foo.txt
public class Http
{
    public static void main( String[] args ) throws Exception
    {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080...");
        while(true){
            Socket socket = server.accept();
            InputStream input = socket.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(input));
            String line = read.readLine();
           
            String[] reqParam = line.split(" ");
            String path = reqParam[1];
            path= path.replace("%20", " ");
            path="/Users/kendalllu/2020 Concurrent Programming/HTTPReq/foo.txt";

            File file = new File(path);
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String         line2 = null;
            StringBuilder  stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            System.out.println(line);

            OutputStream out = socket.getOutputStream();
            
            String t = "HTTP/1.1 200 OK\r\n";
            byte[] bb = t.getBytes("UTF-8");
            out.write(bb);

            int len = stringBuilder.length();
            t = "Content-Length: 124\r\n";
            bb = t.getBytes("UTF-8");
            out.write(bb);
           
            t = "Content-Type: text/html\r\n\r\n";
            bb = t.getBytes("UTF-8");
            out.write(bb);

            String response = "<html><head><title>HTML content via java socket</title></head><body><h2>Here is what the file stored:</h2></body></html>";
            out.write(response.getBytes("UTF-8"));

            while((line2 = fileReader.readLine()) != null) {
                t = line2+"\n";
                bb = t.getBytes("UTF-8");
                out.write(bb);
            }
            fileReader.close();

            out.flush();

            socket.close();
        }

    }
}