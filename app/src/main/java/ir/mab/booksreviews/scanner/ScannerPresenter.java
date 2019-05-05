package ir.mab.booksreviews.scanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.List;

public class ScannerPresenter implements ScannerContract.Presenter {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100 ;
    private ScannerContract.View mScannerView;

    public ScannerPresenter(ScannerContract.View scannerView) {
        this.mScannerView = scannerView;
        scannerView.setPresenter(this);
    }

    @Override
    public FirebaseVisionBarcodeDetector getFirebaseVisionBarcodeDetector() {

        FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_EAN_13,
                        FirebaseVisionBarcode.FORMAT_EAN_8)
                .build();
        return FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);
    }

    @Override
    public FirebaseVisionImageMetadata getFirebaseVisionImageMetadata(int width, int height, int imageFormat) {
        return new FirebaseVisionImageMetadata.Builder()
                .setWidth(width)   // 480x360 is typically sufficient for
                .setHeight(height)  // image recognition
                .setFormat(imageFormat)
                .build();
    }

    @Override
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            //mScannerView.loadPreview();
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            mScannerView.loadNoCameraAvailable();
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void detectBarcode(FirebaseVisionImage image) {
        new Ditector(mScannerView,this).execute(image);
    }

    @Override
    public void start() {
        if (mScannerView.hasPermissions()){
            mScannerView.loadPreview();
        }

        else {
            mScannerView.showPermissionDialog();
        }
    }


    static class Ditector extends AsyncTask<FirebaseVisionImage,Boolean,Integer> {

        private ScannerContract.View view;
        private ScannerContract.Presenter presenter;

        Ditector(ScannerContract.View view, ScannerContract.Presenter presenter) {
            this.view = view;
            this.presenter = presenter;
        }

        @Override
        protected Integer doInBackground(FirebaseVisionImage... firebaseVisionImages) {
            Log.d("BARCODE","do back");
            presenter.getFirebaseVisionBarcodeDetector().detectInImage(firebaseVisionImages[0])
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                            // Task completed successfully
                            // ...
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
                        }
                    });
            return null;
        }
    }
}