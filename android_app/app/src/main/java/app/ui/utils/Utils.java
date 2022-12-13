package app.ui.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class Utils {

    public static void showServerConnectionError(MainActivity instance){
        instance.runOnUiThread(() -> Toast.makeText(instance, "Es konnte keine Verbindung zum Server hergestellt werden!", Toast.LENGTH_SHORT));
    }

    public static void showErrorMessage(MainActivity instance, String title, String body){
        instance.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(instance);

            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(body);
            builder.setIcon(R.drawable.ic_baseline_error_24);

            builder.setNeutralButton("Ok", (dialogInterface, i) -> {
                dialogInterface.cancel();
            });

            builder.show();
        });
    }

    public static void showInfoMessage(MainActivity instance, String title, String body){
        instance.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(instance);

            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(body);

            builder.setNeutralButton("Ok", (dialogInterface, i) -> {
                dialogInterface.cancel();
            });

            builder.show();
        });
    }

    public static void writeToFile(Context ctx, String filename, String content) throws IOException {
        File dir = new File(ctx.getFilesDir(), "mydir");

        if (!dir.exists()){
            dir.mkdir();
        }

        File file = new File(dir, filename);
        FileWriter writer = new FileWriter(file);
        writer.append(content);
        writer.flush();
        writer.close();

    }

    public static String readFromFile(Context ctx, String filename) throws IOException {
        File dir = new File(ctx.getFilesDir(), "mydir");

        File file = new File(dir, filename);
        FileReader reader = new FileReader(file);

        StringBuilder content = new StringBuilder();

        int c;

        while ((c = reader.read()) != -1){
            content.append((char) c);
        }

            return content.toString();

    }
}
