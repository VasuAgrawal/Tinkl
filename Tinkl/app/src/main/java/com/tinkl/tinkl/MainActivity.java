package com.tinkl.tinkl;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;

    private TextView mResultTextView;
    private TextView mResultTemperature;
    private TextView mResultTurbidity;

    private Button mColorButton;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        mResultTextView = (TextView) findViewById(R.id.result_textview);
        mColorButton = (Button) findViewById(R.id.urine_color);
        mResultTemperature = (TextView) findViewById(R.id.temperature_textview);
        mResultTurbidity = (TextView) findViewById(R.id.turbidity_textview);

        mResultTextView.setText(R.string.no_barcode_captured);
        mColorButton.getBackground().setColorFilter(
                Color.argb(0, 0  , 0, 0), PorterDuff.Mode.SRC);
        mResultTemperature.setText("");
        mResultTurbidity.setText("");

        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
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

                    mChannel = ManagedChannelBuilder.forTarget("128.237.165.27:5000").usePlaintext(true).build();
                    blockingStub = TinklGrpc.newBlockingStub(mChannel);
                    PuckId request = PuckId.newBuilder().setHubId(
                            Integer.parseInt(requestIds[0])).setSensorNodeId(Integer.parseInt(requestIds[1])).build();
                    try {
                        Sample sample = blockingStub.getUrination(request);
                        if (!sample.hasColor()) {
                            mResultTextView.setText(R.string.grpc_error);
                            mColorButton.getBackground().setColorFilter(
                                    Color.argb(0, 0  , 0, 0), PorterDuff.Mode.SRC);
                            mResultTemperature.setText("");
                            mResultTurbidity.setText("");
                            return;
                        }
                        mResultTextView.setText("");
                        mColorButton.getBackground().setColorFilter(
                                Color.argb(255, sample.getColor().getR(),
                                        sample.getColor().getG(), sample.getColor().getB()), PorterDuff.Mode.SRC);
                        mResultTemperature.setText(res.getString(R.string.temperature, sample.getTemperature()));
                        mResultTurbidity.setText(res.getString(R.string.turbidity, sample.getTurbidity()));
                    } catch (StatusRuntimeException e) {
                        mResultTextView.setText(R.string.grpc_error);
                        mColorButton.getBackground().setColorFilter(
                                Color.argb(0, 0  , 0, 0), PorterDuff.Mode.SRC);
                        mResultTemperature.setText("");
                        mResultTurbidity.setText("");
                        e.printStackTrace();
                        return;
                    }

                    mChannel.shutdown();

                } else mResultTextView.setText(R.string.no_barcode_captured);
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }
}
