package ir.mab.booksreviews;


import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.List;
import java.util.Objects;

import ir.mab.booksreviews.ScannerUtils.Preview;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100 ;
    private Camera camera;
    private Preview preview;
    private FrameLayout livePreview;

    FirebaseVisionBarcodeDetectorOptions options =
            new FirebaseVisionBarcodeDetectorOptions.Builder()
                    .setBarcodeFormats(
                            FirebaseVisionBarcode.FORMAT_EAN_13,
                            FirebaseVisionBarcode.FORMAT_EAN_8)
                    .build();

    FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
            .getVisionBarcodeDetector(options);

    FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
            .setWidth(1920)   // 480x360 is typically sufficient for
            .setHeight(1080)  // image recognition
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .build();

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanner,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        livePreview = view.findViewById(R.id.livePreview);

        if (hasPermissions()){
            camera = getCameraInstance();
            preview = new Preview(getContext(),camera);
            livePreview.addView(preview);

            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    FirebaseVisionImage image = FirebaseVisionImage.fromByteArray(bytes, metadata);

                    new Ditector().execute(image);
                    //Log.d("BARCODE",String.valueOf(image));

                }
            });
        }

        else {
            requestPermissions();
        }

    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private boolean hasPermissions(){
        return ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        requestPermissions(
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CAMERA:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    camera = getCameraInstance();
                    preview = new Preview(getContext(),camera);
                    livePreview.addView(preview);
                }

                else {

                }
            }
        }
    }

    class Ditector extends AsyncTask<FirebaseVisionImage,Boolean,Boolean> {

        @Override
        protected Boolean doInBackground(FirebaseVisionImage... firebaseVisionImages) {
            Log.d("BARCODE","do back");
            detector.detectInImage(firebaseVisionImages[0])
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                            // Task completed successfully
                            // ...
                            Log.d("BARCODE: ",String.valueOf(barcodes.size()));
                            for (FirebaseVisionBarcode barcode : barcodes){
                                Log.d("BARCODE: ",barcode.getRawValue());
                            }
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                            Toast.makeText(getContext(), "Sorry, something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
            return null;
        }
    }

}


