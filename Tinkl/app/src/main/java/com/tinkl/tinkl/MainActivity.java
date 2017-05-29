package com.tinkl.tinkl;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import com.tinkl.tinkl.TinklGrpc.TinklBlockingStub;
import com.tinkl.tinkl.TinklGrpc.TinklStub;
import io.grpc.stub.StreamObserver;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.tinkl.tinkl.barcode.BarcodeCaptureActivity;
import org.w3c.dom.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;

    private TextView mResultTextView;
    private TextView mResultTemperature;
    private TextView mResultTurbidity;
    private TextView mResultHydration;

    private Button mColorButton;

    private Resources res;

    private List<Sample> samples;
    private List<Sample> currentSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.tinkl_toolbar);
        setSupportActionBar(mainToolbar);

        mResultTextView = (TextView) findViewById(R.id.result_textview);
        mColorButton = (Button) findViewById(R.id.urine_color);
        mResultTemperature = (TextView) findViewById(R.id.temperature_textview);
        mResultTurbidity = (TextView) findViewById(R.id.turbidity_textview);
        mResultHydration = (TextView) findViewById(R.id.hydrated_textview);

        mResultTextView.setText(R.string.no_barcode_captured);
        mColorButton.getBackground().setColorFilter(
                Color.argb(0, 0  , 0, 0), PorterDuff.Mode.SRC);
        mResultTemperature.setText("");
        mResultTurbidity.setText("");
        mResultHydration.setText("");

        samples = new ArrayList<>();
        currentSamples = new ArrayList<>();

//        com.tinkl.tinkl.Color dummyColor = com.tinkl.tinkl.Color.newBuilder().setR(255).setG(255).setB(0).build();
//        com.tinkl.tinkl.Color dummyColor2 = com.tinkl.tinkl.Color.newBuilder().setR(218).setG(165).setB(32).build();
//        com.tinkl.tinkl.Color dummyColor3 = com.tinkl.tinkl.Color.newBuilder().setR(184).setG(134).setB(11).build();
//        Sample dummy = Sample.newBuilder().setTemperature(
//                30).setTurbidity(100).setColor(dummyColor).setSampleId(1).build();
//        samples.add(dummy);
//        Sample dummy2 = Sample.newBuilder().setTemperature(
//                38).setTurbidity(130).setColor(dummyColor2).setSampleId(2).build();
//        Sample dummy3 = Sample.newBuilder().setTemperature(
//                27).setTurbidity(92).setColor(dummyColor3).setSampleId(3).build();
//        samples.add(dummy2);
//        samples.add(dummy3);

        File file = new File(this.getFilesDir(), "data");
        System.out.println(file.exists());
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File inFile : files) {
                try {
                    samples.add(Sample.parseFrom(convertFileToByteArray(inFile)));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            file.mkdir();
        }

        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
    }

    private byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                Intent historyIntent = new Intent(this, HistoryDisplayActivity.class);
                historyIntent.putExtra("size", samples.size());
                for (int i = 0; i < samples.size(); i++) {
                    historyIntent.putExtra("sample"+i, samples.get(i).toByteArray());
                }
                startActivity(historyIntent);
                return true;
            case R.id.details:
                if (currentSamples.size() == 0) {
                    Toast.makeText(this, R.string.no_readings, Toast.LENGTH_SHORT).show();
                    return true;
                }

                Intent detailsIntent = new Intent(this, DetailsDisplayActivity.class);
                detailsIntent.putExtra("size", currentSamples.size());
                for (int i = 0; i < currentSamples.size(); i++) {
                    detailsIntent.putExtra("sample" + i, currentSamples.get(i).toByteArray());
                }
                startActivity(detailsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ManagedChannel mChannel;
        TinklBlockingStub blockingStub;
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    String ids = barcode.displayValue;
                    String[] requestIds = ids.split(",");
                    String address = "olympus.wv.cc.cmu.edu:5000";
                    if (requestIds.length == 3) address = requestIds[2] + ":5000";
                    mChannel = ManagedChannelBuilder.forTarget(address).usePlaintext(true).build();
                    blockingStub = TinklGrpc.newBlockingStub(mChannel);
                    PuckId request = PuckId.newBuilder().setHubId(
                            Integer.parseInt(requestIds[0])).setSensorNodeId(Integer.parseInt(requestIds[1])).build();
                    try {
                        Sample sample = blockingStub.withDeadlineAfter(
                                3, TimeUnit.SECONDS).getUrination(request);
                        Iterator<Sample> details = blockingStub.withDeadlineAfter(
                                3, TimeUnit.SECONDS).getAllUrination(request);
                        currentSamples.clear();
                        while(details.hasNext()) {
                            currentSamples.add(details.next());
                        }

                        if (!sample.hasColor()) {
                            mResultTextView.setText(R.string.grpc_error);
                            mColorButton.getBackground().setColorFilter(
                                    Color.argb(0, 0  , 0, 0), PorterDuff.Mode.SRC);
                            mResultTemperature.setText("");
                            mResultTurbidity.setText("");
                            mResultHydration.setText("");
                            return;
                        }
                        mResultTextView.setText("");
                        float r = sample.getColor().getR();
                        float g = sample.getColor().getG();
                        float b = sample.getColor().getB();
                        float max = Math.max(Math.max(r, g), b);
                        r = r/max*255;
                        g = g/max*255;
                        b = b/max*255;
                        System.out.println(r + ", " + g + ", " + b);
                        mColorButton.getBackground().setColorFilter(
                                Color.argb(255, (int)r, (int)g, (int)b), PorterDuff.Mode.SRC);
                        mResultTemperature.setText(res.getString(R.string.temperature, sample.getTemperature()));
                        Integer turbidity = sample.getTurbidity();
                        String cloudiness = "";
                        if (turbidity > 290) {
                            cloudiness = "Clear";
                        } else if (turbidity > 260) {
                            cloudiness = "Slightly Cloudy";
                        } else {
                            cloudiness = "Very Cloudy";
                        }
                        mResultTurbidity.setText(res.getString(R.string.turbidity, turbidity, cloudiness));
                        int hydration = 0;
                        if ((r + g)/2 - b > 70) {
                            hydration = R.string.not_hydrated;
                            mResultHydration.setTextColor(Color.RED);
                        } else {
                            hydration = R.string.hydrated;
                            mResultHydration.setTextColor(Color.GREEN);
                        }
                        mResultHydration.setText(res.getString(hydration));
                        samples.add(sample);
                        long time= System.currentTimeMillis();
                        File folder = new File(this.getFilesDir(), "data");
                        File file = new File(folder, String.valueOf(time));
                        file.createNewFile();
                        FileOutputStream outputStream = new FileOutputStream(file);
                        outputStream.write(sample.toByteArray());
                        outputStream.close();

                    } catch (Exception e) {
                        mResultTextView.setText(R.string.grpc_error);
                        mColorButton.getBackground().setColorFilter(
                                Color.argb(0, 0  , 0, 0), PorterDuff.Mode.SRC);
                        mResultTemperature.setText("");
                        mResultTurbidity.setText("");
                        e.printStackTrace();
                        return;
                    }

                    mChannel.shutdown();

                } else {
                    mColorButton.getBackground().setColorFilter(
                            Color.argb(0, 0  , 0, 0), PorterDuff.Mode.SRC);
                    mResultTemperature.setText("");
                    mResultTurbidity.setText("");
                    mResultTextView.setText(R.string.no_barcode_captured);
                }
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }
}
