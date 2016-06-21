package fr.canm.cyrilstern1.cnamtp14f;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button buttonSend;
    private EditText editTexttoSend;
    private ListView listView;
    private FrameLayout frameLayout;
    private CustomBroadcast customBroadcast;
    private CustomArrayAdaptor customArrayAdaptor;
    private ArrayList<RowItem> arraylistItem;
    private ArrayAdapter<String> aalist;
    private ArrayList<String> aaalist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.buttonSend = (Button) findViewById(R.id.buttonSend);
        this.buttonSend.setOnClickListener(this);
        this.editTexttoSend = (EditText) findViewById(R.id.editTextSend);
        this.listView = (ListView) findViewById(R.id.listView);
        //this.frameLayout.setBackground(getDrawable(R.drawable.radialgradientback));
        this.listRegIds = new GCMListRegIds(this, GCMListRegIds.LIST_STERN);
        this.aaalist = new ArrayList<>();
        this.aalist =  new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,aaalist);
        this.arraylistItem = new ArrayList<>();
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.arraylistItem = new ArrayList<>();
        Intent intent = new Intent();
        //intent.setAction(A)

    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    private final String textSend = "taper votre message";

    private void init(){

        this.buttonSend.setOnClickListener(this);
        this.listView = (ListView) findViewById(R.id.listView);
        //this.frameLayout.setBackground(getDrawable(R.drawable.radialgradientback));
        this.listRegIds = new GCMListRegIds(this, GCMListRegIds.LIST_STERN);
        //this.aalist =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,);
        arraylistItem = new ArrayList<>();



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSend :
                GetListRegIdsTask task = new GetListRegIdsTask();
                String message = editTexttoSend.getText().toString();
                Intent intent = new Intent(this, GoogleCloudTokenGet.class);
                startService(intent);
                SendMessageToCloudTask sendMessageToCloudTask = new SendMessageToCloudTask(message);
                sendMessageToCloudTask.execute();
                task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                addToList(new RowItem(editTexttoSend.getText().toString()));

        }

    }
    private void addToList(RowItem rowitel){
        Toast.makeText(getApplicationContext(),rowitel.getCourseRow(),Toast.LENGTH_LONG).show();
        Log.i("ghjgjh", String.valueOf(arraylistItem.size()));
        arraylistItem.add(rowitel);
        customArrayAdaptor = new CustomArrayAdaptor(this,arraylistItem);
        listView.setAdapter(customArrayAdaptor);



    }
    private class SendMessageToCloudTask extends AsyncTask<String, Void, Exception> {
        private String message;

        public SendMessageToCloudTask(String message) {
            this.message = message;
        }

        protected Exception doInBackground(String... params) {
           /* final com.google.android.gcm.server.Message msg = new com.google.android.gcm.server.Message.Builder()
                    //.collapseKey("1") // avec la meme cl , le nouveau remplace l'ancien pour le meme utilisateur
                    //.timeToLive(30)  // le message est conserve  30 secondes, ne rien mettre, il est conserve  4 semaines
                    .timeToLive(60 * 60 * 24) // 24 heures
                    //.timeToLive(120)
                    //.timeToLive(0)  // maintenant ou jamais
                    .delayWhileIdle(true)
                    .addData("message", message)
                    .build();
            final List<String> abonnes = listRegIds.regIds();
            Exception cause = null;
            try {
                if(internet() && abonnes.size()>=0) {
                    MulticastResult result = sender.send(msg, abonnes, 15);
                }
            } catch (Exception e) {
                e.printStackTrace();
                cause = e;
            }*/
            return null;//cause;
        }
        @Override
        protected void onPostExecute(Exception e){
            // if(e!=null)tv.setText("Exception: " + e.getMessage());
        }
    }
    public  boolean internet(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !(networkInfo.isConnected())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Internet");
            alertDialog.setMessage("Vérifiez votre connexion !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MainActivity.this.finish();
                        }
                    });
            alertDialog.show();
            return false;
        }else
            return true;
    }
    private final String TAG = this.getClass().getSimpleName();
    private final static boolean I = true;

    // une sortie console minimaliste
    private TextView tv;
    // le message à envoyer
    private EditText et;
    // La liste des abonnés
    private GCMListRegIds listRegIds;
    // Envoi vers le cloud
    // private Sender sender;

    private class GetListRegIdsTask extends AsyncTask<Void, String, Void> {
        private String message;
        protected Void doInBackground(Void... params) {
            int number = listRegIds.size();
            String str = number + " subscriber" + (number>1?"s":"") + ", " + listRegIds.getName()+ "\n";

            for (String regId : listRegIds.regIds()) {  // tous les regids
                if (I) Log.i(TAG, regId);
                if(regId.startsWith("APA91")&&regId.length()>20)
                    str = str + regId.substring(0, 20) + "..." + regId.substring(regId.length()-5, regId.length()) +"\n";
                else
                    publishProgress("Regid non conforme ?!: " + regId);
            }
            publishProgress(str);
            return null;
        }
        @Override
        public void onProgressUpdate(String... values) {

//            tv.setText(values[0]);
        }

    }

}
