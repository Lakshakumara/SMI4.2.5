package sss.yml.smi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;

public class ImageStorage {
    static Context context;
    static String filename;
    static Bitmap bitmap;

    public ImageStorage(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public static String saveInternalStorage(Bitmap bitmap) {

        String stored = null;

        File sdcard = context.getFilesDir();

        File folder = new File(sdcard.getAbsoluteFile(), "/directoryName/");
        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), filename + ".jpg") ;

        if (file.exists()) return stored ;

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            stored = "success";
        } catch (Exception e) {
            //FirebaseCrash.report(e);
            e.printStackTrace();
        }
        return stored;
    }

    public static File getImage() {
        File mediaImage = null;
        try {
            String root = context.getFilesDir().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File(myDir.getPath() + "/directoryName/"+filename+".jpg");
        } catch (Exception e) {
            //FirebaseCrash.report(e);
            e.printStackTrace();
        }
        return mediaImage;
    }
    public static boolean checkifImageExists() {
        Bitmap b = null ;
        File file = ImageStorage.getImage();
        String path = file.getAbsolutePath();
        if (path != null) b = BitmapFactory.decodeFile(path);

        if(b == null ||  b.equals("")) {
            return false ;

        }
        return true ;
    }
}
