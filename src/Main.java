import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java Main <Archivo HTML> <Palabra para buscar>");
            return;
        }

        String ArchivoHTML = args[0];
        String Tecla = args[1];
        String ArchivoLog = "file-" + Tecla + ".log";

        try {

            String htmlContent = new String(Files.readAllBytes(Paths.get(ArchivoHTML)));

            HTMLEditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            kit.read(new StringReader(htmlContent), doc, 0);

            Pattern pat = Pattern.compile(Pattern.quote(Tecla), Pattern.CASE_INSENSITIVE);
            Matcher mat = pat.matcher(htmlContent);

            try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(ArchivoLog))) {
                logWriter.write("Archivo HTML: " + ArchivoHTML + "\n");
                logWriter.write("Palabra de busqueda: " + Tecla + "\n");

                int count = 0;
                while (mat.find()) {
                    int position = mat.start();
                    System.out.println("Palabra en lugar: " + position);
                    logWriter.write("Lugar: " + position + "\n");
                    count++;
                }

                if (count == 0) {
                    System.out.println("No se encontraron ocurrencias de la palabra clave.");
                } else {
                    System.out.println("Ocurrencias finales: " + count);
                }
            }

        } catch (Exception e) {
            System.err.println("Hubo un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}