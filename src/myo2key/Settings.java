package myo2key;

//import java.io.ObjectOutputStream;
//import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.io.IOException;
import java.util.HashMap;
import java.io.PrintWriter;

import org.json.JSONTokener;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Creates and maintains config files. The files use the JSON format.
 *
 * @author Adam Cutler
 * @version 
 */
public class Settings {
  /**
   * Stores whether or not the system follows POSIX. Is used to make the config folder hidden.
   */
  private static final boolean isPosix =
    FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
  protected static String CONFIG_NAME = "myo2key.json";

  /**
   * The one instance of Settings allowed in the application.
   */
  protected static Settings SETTINGS;

  public static Settings getInstance() {
    if(SETTINGS == null) {
      SETTINGS = new Settings();
    }
    return SETTINGS;
  }

  protected Path file;
  protected JSONObject root;

  protected Settings() {
    // If this is POSIX, add a dot to make the file hidden
    if(isPosix) {
      CONFIG_NAME = "." + CONFIG_NAME;
    }
    file = Paths.get(System.getProperty("user.home"), CONFIG_NAME);

    if(Files.exists(file)) {
      try (
        InputStream is = Files.newInputStream(file);
      ) {
        root = new JSONObject(new JSONTokener(is));
      } catch(IOException ioe) {
        System.err.println("Error while reading from preferences");
        ioe.printStackTrace();
        root = new JSONObject();
      } catch(JSONException je) {
        System.err.println("Invalid JSON in settings file");
        je.printStackTrace();
        root = new JSONObject();
      }
    } else {
      root = new JSONObject();
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        save();
      }
    });
  }


  protected void save() {
    try (
      OutputStream os = Files.newOutputStream(file);
    ){
      PrintWriter pw = new PrintWriter(os);
      pw.print(root.toString(2));
      pw.flush();
      System.out.println("Wrote to settings file");
      // If we aren't Posix (ie Windows), set the hidden attribute to true
      if(! isPosix) {
        Files.setAttribute(file, "dos:hidden", true);
      }
    } catch(IOException ioe) {
      System.err.println("Error while saving settings file.");
      ioe.printStackTrace();
    }
  }

}
