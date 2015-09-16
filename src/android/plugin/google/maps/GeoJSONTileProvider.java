package plugin.google.maps;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Cuando Google Maps avisa que el usuario está viendo un tile, invoca a
 * jsFunctionName(z,x,y)
 *
 * FIXME : Sacar lo de GeoJSON del nombre, es genérica
 *
 * Created by ramiro on 21/7/15.
 */
public class GeoJSONTileProvider implements TileProvider {

    private static String LOG_TAG = "GeoJSONTileProvider";

    private CordovaWebView webView;
    private CordovaInterface cordova;
    private String jsFunctionName;


    public GeoJSONTileProvider(CordovaWebView webView, CordovaInterface cordova, String jsFunctionName) {
        this.webView = webView;
        this.cordova = cordova;
        this.jsFunctionName = jsFunctionName;
    }

    @Override
    public Tile getTile(final int x, final int y, final int zoom) {

        if (zoom >= 12 ) {
            //Notifico a JS que me pidieron un tile
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                String call = "javascript:" + jsFunctionName + "(" + zoom + "," + x + "," + y + ")";
                webView.loadUrl(call);
                }
            });
        }
        return null;
    }

}
