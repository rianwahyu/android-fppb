package id.co.megadepo.fppb_sidoarjo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by addin on 05/12/17.
 */

public class KCal {

    public String konvertdate (String a, String b, String c) {
        SimpleDateFormat sdf = new SimpleDateFormat(b);
        Date testDate = null;
        try {
            testDate = sdf.parse(a);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(c);
        String newFormat = formatter.format(testDate);
        return newFormat;
    }
}
